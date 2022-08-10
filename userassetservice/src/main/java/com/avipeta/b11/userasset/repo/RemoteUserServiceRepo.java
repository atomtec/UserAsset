package com.avipeta.b11.userasset.repo;

import com.avipeta.b11.userasset.remote.dto.UserDTO;

public interface RemoteUserServiceRepo {
	public UserDTO getUserById(Long id) throws Exception;
    public UserDTO updateIsUserTaggedById(long id , boolean hasAssets) throws Exception;
    
    //For testing 
    public void resetDB();

}
