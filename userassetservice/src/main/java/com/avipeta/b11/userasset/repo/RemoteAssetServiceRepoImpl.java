package com.avipeta.b11.userasset.repo;

import org.springframework.stereotype.Service;

import com.avipeta.b11.userasset.remote.dto.AssetDTO;

@Service
public class RemoteAssetServiceRepoImpl implements RemoteAssetServiceRepo{

	@Override
	public AssetDTO getAssetById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssetDTO updateIsinUse(long id, boolean isInUse) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetDB() {
		// TODO Auto-generated method stub
		
	}

}
