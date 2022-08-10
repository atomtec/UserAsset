package com.avipeta.b11.userasset.simulate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.avipeta.b11.userasset.remote.dto.AssetDTO;
import com.avipeta.b11.userasset.repo.RemoteAssetServiceRepo;

@Profile("test")
@Service
@Primary
public class FakeRemoteAssetRepo implements RemoteAssetServiceRepo{
	
	public static Map<Long, AssetDTO> assetDTOs;
	static {
		assetDTOs = new HashMap<>();
		assetDTOs.put(1L, createFreeAsset()); //Free asset
		assetDTOs.put(2L, createTaggedAsset()); //In Use asset asset user 
		assetDTOs.put(3L, createReleasedAsset()); //Is released asset 
		assetDTOs.put(4L, createTaggedAsset2()); //Tagged Asset 
		assetDTOs.put(5L, createTaggedAsset3()); //Tagged Asset to user 2
	}

	@Override
	public AssetDTO getAssetById(Long id) throws Exception {
		return assetDTOs.get(id);
	}

	@Override
	public AssetDTO updateIsinUse(long id, boolean isInUse) throws Exception {
		assetDTOs.get(id).setIsinuse(isInUse);
		return assetDTOs.get(id);
	}
	
	

	private static AssetDTO createFreeAsset() {
		AssetDTO free = new AssetDTO();
		free.setAddeddate(new Date());
		free.setId(1L);
		free.setBondedid("WT-01");
		free.setIsinuse(false);
		free.setIsreleased(false);
		free.setDesrciption("Meter it is ");
		free.setTitle("METER 01");
		
		return free;
	}
	
	
	private static AssetDTO createTaggedAsset() {
		AssetDTO tagged = new AssetDTO();
		tagged.setAddeddate(new Date());
		tagged.setId(2L);
		tagged.setBondedid("WT-02");
		tagged.setIsinuse(true);
		tagged.setIsreleased(false);
		tagged.setDesrciption("Tuner it is ");
		tagged.setTitle("Tuner 01");
		
		return tagged;
	}
	
	private static AssetDTO createReleasedAsset() {
		AssetDTO released = new AssetDTO();
		released.setAddeddate(new Date());
		released.setId(3L);
		released.setBondedid("WT-03");
		released.setIsinuse(false);
		released.setIsreleased(true);
		released.setDesrciption("Amp it is ");
		released.setTitle("Amp 01");
		released.setReleasedate(new Date());
		return released;
	}
	
	
	private static AssetDTO createTaggedAsset2() {
		AssetDTO tagged2 = new AssetDTO();
		tagged2.setAddeddate(new Date());
		tagged2.setId(4L);
		tagged2.setBondedid("WT-04");
		tagged2.setIsinuse(true);
		tagged2.setIsreleased(false);
		tagged2.setDesrciption("LCD it is ");
		tagged2.setTitle("LCD 01");
		
		return tagged2;
	}
	
	private static AssetDTO createTaggedAsset3() {
		AssetDTO tagged3 = new AssetDTO();
		tagged3.setAddeddate(new Date());
		tagged3.setId(5L);
		tagged3.setBondedid("WT-05");
		tagged3.setIsinuse(true);
		tagged3.setIsreleased(false);
		tagged3.setDesrciption("Harness it is ");
		tagged3.setTitle("Harness 01");
		
		return tagged3;
	}
	
	@Override
	public void resetDB() {
		assetDTOs.clear();
		assetDTOs.put(1L, createFreeAsset()); //Free asset
		assetDTOs.put(2L, createTaggedAsset()); //In Use asset asset user 
		assetDTOs.put(3L, createReleasedAsset()); //Is released asset 
		assetDTOs.put(4L, createTaggedAsset2()); //Tagged Asset 
		assetDTOs.put(5L, createTaggedAsset3()); //Tagged Asset to user 2
	
	}
	

}
