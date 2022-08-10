package com.avipeta.b11.userasset.repo;

import org.springframework.stereotype.Service;

import com.avipeta.b11.userasset.remote.dto.UserDTO;

@Service
public class RemoteUserServiceRepoImpl implements RemoteUserServiceRepo{

	@Override
	public UserDTO getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateIsUserTaggedById(long id, boolean hasAssets) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetDB() {
		// TODO Auto-generated method stub
		
	}

}
