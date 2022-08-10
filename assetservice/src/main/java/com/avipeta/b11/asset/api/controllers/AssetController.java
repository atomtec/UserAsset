package com.avipeta.b11.asset.api.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avipeta.b11.asset.Utility;
import com.avipeta.b11.asset.entity.AssetEntity;
import com.avipeta.b11.asset.service.AssetService;

@RestController
@RequestMapping("/api/v1")
public class AssetController {
	
	@Autowired
	private AssetService assetService;
	
	@GetMapping("/")
    public String index()  {
        return "Hello World!";
    }
	
	
	@GetMapping("/testcreateasset")
	public AssetEntity testCreateAsset() throws Exception  {
		return assetService.createAsset(Utility.getTestAsset());
    }
	
	@GetMapping("/asset")
	public List<AssetEntity> getAllAssets() throws Exception  {
		return assetService.getAllAsset();
    }
	
	
	@GetMapping("/asset/{id}")
    public AssetEntity getAssetById(@PathVariable(value = "id") Long assetId) throws Exception {
        return assetService.getAssetById(assetId);
    }
	
	@GetMapping("/asset/released/{isReleased}")
    public List<AssetEntity> getAllReleasedAssets(@PathVariable(value = "isReleased") boolean isReleased) throws Exception {
        return assetService.getAssetByReleaseStatus(isReleased);
    }
	
	@GetMapping("/asset/inuse/{isInUse}")
    public List<AssetEntity> getAllAssetsByUse(@PathVariable(value = "isInUse") boolean isInUse) throws Exception {
        return assetService.getAssetByUse(isInUse);
    }
	
	
	@PatchMapping("/asset/{id}/release")
    public ResponseEntity<AssetEntity> releaseAssetById(@PathVariable(value = "id") Long assetId , @Valid @RequestBody Date releaseDate) throws Exception {
        return ResponseEntity.ok(assetService.releaseAssetById(assetId, releaseDate));
    }
	
	@PatchMapping("/asset/{id}/")
    public ResponseEntity<AssetEntity> updateAssetById(@PathVariable(value = "id") Long assetId , @Valid @RequestBody AssetEntity assetEntity) throws Exception {
        return ResponseEntity.ok(assetService.updateAssetById(assetId, assetEntity));
    }
	
	@PostMapping("/asset")
	public AssetEntity createAsset(@Valid @RequestBody AssetEntity assetEntity) throws Exception {
		return assetService.createAsset(assetEntity);
	}
	
	
}
