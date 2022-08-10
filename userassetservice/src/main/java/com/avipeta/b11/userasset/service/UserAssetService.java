package com.avipeta.b11.userasset.service;

import java.util.List;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.entity.TaggedAssetEntity;

public interface UserAssetService {
	ActiveUserEntity transferAsset (Long adminid , Long fromUserId , Long toUserId ,Long assetid) throws Exception;
	ActiveUserEntity getActiveUserByRemoteId (Long id) throws Exception;
	List <ActiveUserEntity> getAllActiveUsers() throws Exception;
	void resetDB();//For testing only 
	ActiveUserEntity getUserByBondedAssetId(String bondedid) throws Exception;
	List <TaggedAssetEntity> getAllTaggedAssets() throws Exception;
}
