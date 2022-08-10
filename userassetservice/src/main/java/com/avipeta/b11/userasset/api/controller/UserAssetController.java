package com.avipeta.b11.userasset.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.entity.TaggedAssetEntity;
import com.avipeta.b11.userasset.remote.dto.TransferDTO;
import com.avipeta.b11.userasset.service.UserAssetService;

@RestController
@RequestMapping("/api/v1/userasset")
public class UserAssetController {
	
	@Autowired
	UserAssetService userAssetService;
	
	
	@GetMapping("/")
    public String index()  {
        return "Hello World!";
    }
	
	@PatchMapping("/transferasset")  
	public ActiveUserEntity transferAsset(@Valid @RequestBody TransferDTO transferDTO) throws Exception{
		return userAssetService.transferAsset(transferDTO.getAdminid(),
				transferDTO.getFromuserid(),transferDTO.getTouserid(),
				transferDTO.getAssetid());
	}
	
	@GetMapping("/activeuser/{userid}")
    public ActiveUserEntity getActiveUserByRemoteId(@PathVariable(value = "userid") Long userId) throws Exception {
        return userAssetService.getActiveUserByRemoteId(userId);
    }
	
	@GetMapping("/activeuser")
    public List<ActiveUserEntity> getAllActiveUser() throws Exception {
        return userAssetService.getAllActiveUsers();
    }
	
	
	@GetMapping("/activeuser/taggedasset/{bondedid}")
    public ActiveUserEntity getUserByBondedAssetId(@PathVariable(value = "bondedid") String bondedid) throws Exception {
        return userAssetService.getUserByBondedAssetId(bondedid);
    }
	
	@GetMapping("/taggedasset")
    public List<TaggedAssetEntity> getAllActiveAssets() throws Exception {
        return userAssetService.getAllTaggedAssets();
    }
	
	
	
}
