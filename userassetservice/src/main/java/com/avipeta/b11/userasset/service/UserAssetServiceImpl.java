package com.avipeta.b11.userasset.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.entity.LogTransferEntity;
import com.avipeta.b11.userasset.entity.TaggedAssetEntity;
import com.avipeta.b11.userasset.exception.InvalidArgumentException;
import com.avipeta.b11.userasset.exception.ResourceNotFoundException;
import com.avipeta.b11.userasset.remote.dto.AssetDTO;
import com.avipeta.b11.userasset.remote.dto.UserDTO;
import com.avipeta.b11.userasset.repo.ActiveUserRepo;
import com.avipeta.b11.userasset.repo.LogTransferRepo;
import com.avipeta.b11.userasset.repo.RemoteAssetServiceRepo;
import com.avipeta.b11.userasset.repo.RemoteUserServiceRepo;
import com.avipeta.b11.userasset.repo.TaggedAssetRepo;

@Service
public class UserAssetServiceImpl implements UserAssetService {
	
	@Autowired
	RemoteUserServiceRepo remoteUserRepo;
	
	@Autowired
	RemoteAssetServiceRepo remoteAssetRepo;
	
	@Autowired
	TaggedAssetRepo taggedAssetRepo;
	
	@Autowired
	ActiveUserRepo activeUserRepo;
	
	@Autowired
	LogTransferRepo logtransferRepo;
	
	
	private static String INVENTORY_USER = "INVENTORY";

	

	

	@Override
	public ActiveUserEntity transferAsset(Long adminid, Long fromUserId, Long toUserId, Long assetId) throws Exception {
		ActiveUserEntity transferEntity = null;
		UserDTO adminDTO = remoteUserRepo.getUserById(adminid);// replace by exception throwing methods 
		//if(admin.role != Role.ADMIN)
			//throw new SecurityException("This operation is not valid for this user type");
		AssetDTO assetDTO = remoteAssetRepo.getAssetById(assetId);//
		UserDTO toUser = null;
		if(fromUserId == 0L) {
			toUser = remoteUserRepo.getUserById(toUserId);
			if (toUser.isReleased()) {
				throw new InvalidArgumentException("This user is already released cannot be used in asset transfer emp id  " + toUser.getEmployeeid());
			}
			transferEntity = addFromInventory(adminDTO,toUser, assetDTO);//check to to user id here 
			return transferEntity;
		}
		//Ok not from inventory 
		UserDTO fromUser = remoteUserRepo.getUserById(fromUserId);// replace by exception throwing methods 
		if (fromUser.isReleased()) {
			throw new InvalidArgumentException("This user is already released cannot be used in asset transfer emp id " + fromUser.getEmployeeid());
		}
		
		if(toUserId == 0L) {
			//return to inventory
			transferEntity = returnToInventory(adminDTO,fromUser, assetDTO);//check to to user id here
			return transferEntity;
		}
		//Ok User to user 
		toUser = remoteUserRepo.getUserById(toUserId);
		transferEntity = transferBetweenUsers(adminDTO ,fromUser, toUser ,assetDTO);//check to to user id here 
		
		return transferEntity;
	}

	

	private ActiveUserEntity transferBetweenUsers(UserDTO adminDTO, UserDTO fromUserDTO, UserDTO toUserDTO, AssetDTO assetDTO) throws Exception {
		rejectReleasedAsset(assetDTO);
		boolean isaNewUser = false;
		ActiveUserEntity activeFromUser = getActiveUserByRemoteId(fromUserDTO.getId()); //Exception if not found
		//Now 2 cases either the toUser is present in UserAssetDB or is a fresh user
		ActiveUserEntity activeToUser = null;
		try {
			activeToUser = getActiveUserByRemoteId(toUserDTO.getId());
		} catch (ResourceNotFoundException e) {
			//Ok so user is new 
			isaNewUser = true;
		}
		
		TaggedAssetEntity matchedAsset = getMatchedTaggedAsset(activeFromUser,assetDTO);
		
		
		if(matchedAsset == null) {
			throw new InvalidArgumentException("This asset is either not tagged to the "
					+ "user or not in use  asset id" + assetDTO.getBondedid() + " Asset status is in use " + assetDTO.getIsinuse() + " From user employee id " + fromUserDTO.getEmployeeid() 
					+ " Asset owner employee id " + fromUserDTO.getEmployeeid());
		}
		
		//Now transfer
		 //First check if this is the only asset of this user
		if (activeFromUser.getTaggedAssets().size() == 1) {
			//delete this and all assets 
			activeUserRepo.delete(activeFromUser); //This ensures all gone
			remoteUserRepo.updateIsUserTaggedById(fromUserDTO.getId(), false); //mark the user is free to be removed
			activeFromUser.getTaggedAssets().clear();
		} else {
			activeFromUser.getTaggedAssets().remove(matchedAsset);
			activeUserRepo.save(activeFromUser); //This should work in JPA??
		}
		//Now add the asset 
		
		
		
		//Get the assset ready 
		if(isaNewUser) {
			activeToUser = convertUserDTOtoActiveUserEntity(toUserDTO);
			activeUserRepo.save(activeToUser);
			List<TaggedAssetEntity> taggedAssets = new ArrayList<TaggedAssetEntity>();
			taggedAssets.add(matchedAsset);
			activeToUser.setTaggedAssets(taggedAssets);
		}
		else {
			activeToUser.getTaggedAssets().add(matchedAsset);
		}
		
		activeUserRepo.save(activeToUser);

		
		if(isaNewUser) {
			remoteUserRepo.updateIsUserTaggedById(activeToUser.getId(), true);
		}
		
		
		String transferedByName = adminDTO.getFirstname() + " " + adminDTO.getLastname();
		String fromUserName = activeFromUser.getFirstname() + " " + activeFromUser.getLastname();
		String toUserName = activeToUser.getFirstname() + " " + activeToUser.getLastname();
		
		saveTransferLog(transferedByName, fromUserName, toUserName, assetDTO);
		
		return activeToUser;
		
		
		
	}

	private ActiveUserEntity returnToInventory(UserDTO adminDTO, UserDTO fromUserDTO, AssetDTO assetDTO) throws Exception {
		// We already have the user and assets
		//Check if we have active user 
		
		ActiveUserEntity activeUser = getActiveUserByRemoteId(fromUserDTO.getId());
		
		rejectReleasedAsset(assetDTO);
		
		if(!assetDTO.getIsinuse()) {
			throw new InvalidArgumentException("This asset is not in use cannot be transferred back to inventory  " + assetDTO.getBondedid());
		}
		
		TaggedAssetEntity matchedAsset = getMatchedTaggedAsset(activeUser,assetDTO);
		
		
		if(matchedAsset == null) {
			throw new InvalidArgumentException("This asset is either not tagged to the "
					+ "user or not in use " + assetDTO.getBondedid() + " Asset status " + assetDTO.getIsinuse() + " From user id " + fromUserDTO.getEmployeeid() 
					+ " Asset owner" + activeUser.getEmployeeid());
		}
		
		//All check pass 
	    //First check if this is the only asset of this user
		if (activeUser.getTaggedAssets().size() == 1) {
			//delete this and all assets 
			activeUserRepo.delete(activeUser); //This ensures all gone
			remoteUserRepo.updateIsUserTaggedById(fromUserDTO.getId(), false); //mark the user is free to be removed
			activeUser.getTaggedAssets().clear();//clear it 
		} else {
			activeUser.getTaggedAssets().remove(matchedAsset);
			activeUserRepo.save(activeUser); //This should work in JPA??
		}
		
		//Now mark the item ready for resue 
		remoteAssetRepo.updateIsinUse(assetDTO.getId(), false);
		//save logs
		String transferedByName = adminDTO.getFirstname() + " " + adminDTO.getLastname();
		String fromUserName = activeUser.getFirstname() + " " + activeUser.getLastname();
		
		saveTransferLog(transferedByName, fromUserName, INVENTORY_USER, assetDTO);
		
		
		return activeUser;
		
		 
	}

	private ActiveUserEntity addFromInventory(UserDTO adminDTO, UserDTO toUser, AssetDTO assetDTO) throws Exception {
		boolean isaNewUser = false;
		//check if user is already in the DB
		ActiveUserEntity activeUser = null ;
		TaggedAssetEntity taggedAsset = null;
		try {
			activeUser = getActiveUserByRemoteId(toUser.getId());
		} catch (ResourceNotFoundException  e) {
			//user does not exists we will create a new one later
			isaNewUser = true;
		}
		
		
		rejectReleasedAsset(assetDTO);
		
		//Check in Use asset
		if (assetDTO.getIsinuse()) {
			throw new InvalidArgumentException("This asset is already tagged please do transfer from User  " + assetDTO.getBondedid());
		}
		
		try {
			taggedAsset = getTaggedAssetByBondedId(assetDTO.getBondedid());
		} catch (ResourceNotFoundException  e) {
			//This is expected 
		}
		
		if (taggedAsset != null) //This Should never happen
			throw new InvalidArgumentException("Data violation inventory asset shown as tagged " + taggedAsset.getBondedid());
		
		//Get the assset ready 
		if(isaNewUser) {
			activeUser = convertUserDTOtoActiveUserEntity(toUser);
			activeUserRepo.save(activeUser);
			List<TaggedAssetEntity> taggedAssets = new ArrayList<TaggedAssetEntity>();
			taggedAssets.add(convertAssetDTOtoTaggedAssetEntity(assetDTO , activeUser));
			activeUser.setTaggedAssets(taggedAssets);
		}
		else {
			activeUser.getTaggedAssets().add(convertAssetDTOtoTaggedAssetEntity(assetDTO , activeUser));
		}
		
			
		activeUserRepo.save(activeUser);

		
		//Now mark the asset as in use
		remoteAssetRepo.updateIsinUse(assetDTO.getId(), true);
		
		if(isaNewUser) {
			remoteUserRepo.updateIsUserTaggedById(toUser.getId(), true);
		}
		//Now mark the user has assets tagged 
		
		// save logs
		String transferedByName = adminDTO.getFirstname() + " " + adminDTO.getLastname();
		String toUserName = activeUser.getFirstname() + " " + activeUser.getLastname();

		saveTransferLog(transferedByName, INVENTORY_USER, toUserName, assetDTO);

		return activeUser;
		
	}

	
	
	
	
	
	@Override
	public ActiveUserEntity getActiveUserByRemoteId(Long userid) throws Exception {
		try {
			ActiveUserEntity actUserOp = activeUserRepo.findByRemoteuserid(userid);
			if (actUserOp == null) {
				throw new ResourceNotFoundException("ActiveUser not found for this id :: " + userid);
			} 
			else {
				//Get All Assets for that User
				List<TaggedAssetEntity> assets = taggedAssetRepo.findByTaggeduser(actUserOp);
				if(assets.size() < 0)
				{
					throw new DataIntegrityViolationException("TaggedUser has no asset that Cannot happen emp id " + actUserOp.getEmployeeid());
				}
				
				return actUserOp;
			}
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		
	}
	
	

	private TaggedAssetEntity getTaggedAssetByBondedId(String bondedId) throws Exception {
		try {
			TaggedAssetEntity assetOP = taggedAssetRepo.findByBondedid(bondedId);
			if (assetOP == null) {
				throw new ResourceNotFoundException("Asset not found for this id :: " + bondedId);
			} 
			return assetOP;
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		
	}
	
	
	private static TaggedAssetEntity convertAssetDTOtoTaggedAssetEntity(AssetDTO assetDTO, ActiveUserEntity activeUser) {
		TaggedAssetEntity taggedAssetEntity  = new TaggedAssetEntity();
		taggedAssetEntity.setBondedid(assetDTO.getBondedid());
		taggedAssetEntity.setRemoteassetid(assetDTO.getId());
		taggedAssetEntity.setTitle(assetDTO.getTitle());
		taggedAssetEntity.setDesrciption(assetDTO.getDesrciption());
		taggedAssetEntity.setTaggeduser(activeUser);
		return taggedAssetEntity;
	}
	
	
	private static ActiveUserEntity convertUserDTOtoActiveUserEntity(UserDTO userDTO) {
		ActiveUserEntity activeUserEntity  = new ActiveUserEntity();
		activeUserEntity.setEmployeeid(userDTO.getEmployeeid());
		activeUserEntity.setRemoteuserid(userDTO.getId());
		activeUserEntity.setFirstname(userDTO.getFirstname());
		activeUserEntity.setLastname(userDTO.getLastname());
		activeUserEntity.setPhonenumber(userDTO.getPhonenumber());
		return activeUserEntity;
	}
	
	private void rejectReleasedAsset(AssetDTO assetDTO) throws InvalidArgumentException {
		// Check released asset
		if (assetDTO.getIsreleased()) {
			throw new InvalidArgumentException("Cannot transfer a released asset  " + assetDTO.getBondedid());
		}

	}
	
	private void saveTransferLog(String transferByUserName,String fromUserName , String toUserName, AssetDTO assetDTO) {
		LogTransferEntity logTransfer = new LogTransferEntity();
		logTransfer.setFromusername(fromUserName);
		logTransfer.setTousername(toUserName);
		logTransfer.setBondedid(assetDTO.getBondedid());
		logTransfer.setTitle(assetDTO.getTitle());
		logTransfer.setTransferbyusername(transferByUserName);
		logtransferRepo.save(logTransfer);
		
	}

	private TaggedAssetEntity getMatchedTaggedAsset(ActiveUserEntity activeUser , AssetDTO assetDTO) {
		TaggedAssetEntity matchedAsset = null;
		for (TaggedAssetEntity taggedAssetEntity : activeUser.getTaggedAssets())
		{
			if (taggedAssetEntity.getBondedid().equals(assetDTO.getBondedid())) {
				matchedAsset = taggedAssetEntity;
				break;
			}
		}
		return matchedAsset;
	}



	@Override
	public void resetDB() {
		remoteAssetRepo.resetDB();
		remoteUserRepo.resetDB();
	}



	@Override
	public List<ActiveUserEntity> getAllActiveUsers() throws Exception {
		return activeUserRepo.findAll();
	}



	@Override
	public ActiveUserEntity getUserByBondedAssetId(String bondedid) throws Exception {
		return getTaggedAssetByBondedId(bondedid).getTaggeduser();
	}



	@Override
	public List<TaggedAssetEntity> getAllTaggedAssets() throws Exception {
		return taggedAssetRepo.findAll();
	}

}
