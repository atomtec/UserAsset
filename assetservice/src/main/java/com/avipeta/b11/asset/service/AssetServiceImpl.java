package com.avipeta.b11.asset.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.avipeta.b11.asset.entity.AssetEntity;
import com.avipeta.b11.asset.exception.InvalidArgumentException;
import com.avipeta.b11.asset.exception.ResourceNotFoundException;
import com.avipeta.b11.asset.repository.AssetRepo;

@Service
public class AssetServiceImpl implements AssetService {
	
	@Autowired
    AssetRepo assetRepo;

	@Override
	public AssetEntity createAsset(AssetEntity asset) throws Exception {
		return save(asset);
	}

	@Override
	public AssetEntity updateAssetById(Long id ,AssetEntity asset) throws Exception {
		AssetEntity dbAssetEntity = getAssetEntityById(id);
		if (dbAssetEntity.getIsreleased())
		{
			throw new InvalidArgumentException("Asset is released and cannot be updated contact DB Admin :: " + id);
		}
		dbAssetEntity.setDesrciption(asset.getDesrciption());
		dbAssetEntity.setAddeddate(asset.getAddeddate());
		dbAssetEntity.setTitle(asset.getTitle());
		dbAssetEntity.setBondedid(asset.getBondedid());
		return save(asset);
	}

	private AssetEntity save(AssetEntity asset) throws Exception {
		try
		{
			assetRepo.save(asset);
		}
		catch (DataAccessException ex)
		{
			throw new Exception(ex);
		}
		
		asset.setId(asset.getId());
		
		return asset;
	}
	
	
	private AssetEntity getAssetEntityById(Long id) throws Exception {
		try {
			Optional<AssetEntity> assetEntity = assetRepo.findById(id);
			if (assetEntity.isEmpty()) {
				throw new ResourceNotFoundException("Asset not found for this id :: " + id);
			} 
			else {
				return assetEntity.get();
			}
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		
	}

	@Override
	public AssetEntity releaseAssetById(Long id, Date releaseDate) throws Exception {
		AssetEntity dbAssetEntity = getAssetEntityById(id);
		if (dbAssetEntity.getIsinuse()) {
			throw new InvalidArgumentException("This asset is in use cannot be release " + id);
		}
		else {
			//Add check for Release Data lesser than current data?
			dbAssetEntity.setIsreleased(true);
			dbAssetEntity.setReleasedate(releaseDate);
			return save(dbAssetEntity);
		}
		
	}
	
	private AssetEntity updateInUseById(Long id, boolean inUse) throws Exception {
		AssetEntity dbAssetEntity = getAssetEntityById(id);
		if (dbAssetEntity.getIsreleased()) {
			throw new InvalidArgumentException("This asset is released cannot update usage " + id);
		}
		else {
			//Add check for Release Data lesser than current data?
			dbAssetEntity.setIsinuse(inUse);
			return save(dbAssetEntity);
		}
		
	}

	@Override
	public AssetEntity getAssetById(Long id) throws Exception {
		return getAssetEntityById(id);
	}

	@Override
	public List<AssetEntity> getAllAsset() throws Exception {
		return assetRepo.findAll();
	}

	@Override
	public List<AssetEntity> getAssetByUse(Boolean isInUse) throws Exception {
		return assetRepo.findByIsinuse(isInUse);
	}

	@Override
	public List<AssetEntity> getAssetByReleaseStatus(Boolean isReleased) throws Exception {
		return assetRepo.findByIsreleased(isReleased);
	}

	@Override
	public AssetEntity updateUseById(Long id, boolean inuse) throws Exception {
		return updateInUseById(id,inuse);
	}

}
