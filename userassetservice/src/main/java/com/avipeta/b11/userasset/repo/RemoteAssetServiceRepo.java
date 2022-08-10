package com.avipeta.b11.userasset.repo;

import com.avipeta.b11.userasset.remote.dto.AssetDTO;

public interface RemoteAssetServiceRepo {
	public AssetDTO getAssetById(Long id) throws Exception;
    public AssetDTO updateIsinUse(long id , boolean isInUse) throws Exception;
	void resetDB();//Testing
}
