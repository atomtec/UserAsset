package com.avipeta.b11.userasset.simulate;

import java.util.HashMap;

import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.avipeta.b11.userasset.exception.ResourceNotFoundException;
import com.avipeta.b11.userasset.remote.dto.UserDTO;
import com.avipeta.b11.userasset.repo.RemoteUserServiceRepo;


@Profile("test")
@Service
@Primary
public class FakeRemoteUserRepo implements RemoteUserServiceRepo {
	
	public static Map<Long, UserDTO> userDTOS;
	static {
		userDTOS = new HashMap<>();
		userDTOS.put(1L, createFreeUser()); //Free user
		userDTOS.put(2L, createTaggedUser()); //Has tagged asset user 
		userDTOS.put(3L, createReleasedUser()); //Is released user 
		userDTOS.put(4L, createTaggedUser2()); //Tagged User 
		userDTOS.put(5L, createAdminUser()); //Admin User 
	}

	@Override
	public UserDTO getUserById(Long id) throws Exception {
		if(userDTOS.containsKey(id))
			return userDTOS.get(id);
		else 
			throw new  ResourceNotFoundException("This id is not present in repository id: " +id);
	}

	@Override
	public UserDTO updateIsUserTaggedById(long id, boolean hasAssets) throws Exception {
	    userDTOS.get(id).setTagged(hasAssets);
		return  userDTOS.get(id);
	}
	
	
	private static UserDTO createFreeUser() {
		UserDTO free = new UserDTO();
		free.setEmployeeid("1234567");
		free.setId(1L);
		free.setFirstname("Free");
		free.setLastname("Free Last");
		free.setPhonenumber("983164");
		free.setTagged(false);
		free.setReleased(false);
		
		return free;
	}
	
	private static UserDTO createTaggedUser() {
		UserDTO tagged = new UserDTO();
		tagged.setEmployeeid("1234568");
		tagged.setId(2L);
		tagged.setFirstname("Tagged1");
		tagged.setLastname("Tagged1 Last");
		tagged.setPhonenumber("983165");
		tagged.setTagged(true);
		tagged.setReleased(false);
		
		return tagged;
	}
	
	private static UserDTO createReleasedUser() {
		UserDTO released = new UserDTO();
		released.setEmployeeid("1234569");
		released.setId(3L);
		released.setFirstname("Released");
		released.setLastname("Released Last");
		released.setPhonenumber("983166");
		released.setTagged(false);
		released.setReleased(true);
		
		return released;
	}
	
	private static UserDTO createTaggedUser2() {
		UserDTO tagged2 = new UserDTO();
		tagged2.setEmployeeid("1234569");
		tagged2.setId(4L);
		tagged2.setFirstname("Tagged2");
		tagged2.setLastname("Tagged2 Last");
		tagged2.setPhonenumber("983166");
		tagged2.setTagged(true);
		tagged2.setReleased(false);
		
		return tagged2;
	}
	
	
	private static UserDTO createAdminUser() {
		UserDTO admin = new UserDTO();
		admin.setEmployeeid("1234599");
		admin.setId(5L);
		admin.setFirstname("Admin");
		admin.setLastname("Admin Last");
		admin.setPhonenumber("98316689");
		admin.setTagged(false);
		admin.setReleased(false);
		
		return admin;
	}

	@Override
	public void resetDB() {
	userDTOS.clear();
	userDTOS.put(1L, createFreeUser()); //Free user
	userDTOS.put(2L, createTaggedUser()); //Has tagged asset user 
	userDTOS.put(3L, createReleasedUser()); //Is released user 
	userDTOS.put(4L, createTaggedUser2()); //Tagged User 
	userDTOS.put(5L, createAdminUser()); //Admin User 
	
	}
	
	

}
