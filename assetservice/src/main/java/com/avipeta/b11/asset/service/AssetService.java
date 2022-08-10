package com.avipeta.b11.asset.service;

import java.util.Date;
import java.util.List;

import com.avipeta.b11.asset.entity.AssetEntity;

public interface AssetService {
	
	AssetEntity createAsset(AssetEntity asset) throws Exception;
	AssetEntity updateAssetById(Long id,AssetEntity asset) throws Exception;
	AssetEntity releaseAssetById(Long id, Date releaseDate) throws Exception;
	AssetEntity getAssetById(Long id) throws Exception;
	List<AssetEntity> getAllAsset() throws Exception;
	List<AssetEntity> getAssetByUse(Boolean isInUse) throws Exception;
	List<AssetEntity> getAssetByReleaseStatus(Boolean isReleased) throws Exception;
	AssetEntity updateUseById(Long id, boolean inuse) throws Exception;

}
