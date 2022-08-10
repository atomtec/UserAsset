package com.avipeta.b11.asset.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.asset.entity.AssetEntity;
import com.avipeta.b11.asset.exception.InvalidArgumentException;
import com.avipeta.b11.asset.exception.ResourceNotFoundException;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
public class AssetServiceTest {
	
	@Autowired
    AssetService assetService;
	
	@Test
    @Transactional
	public void testCreate() throws Exception {
		Assertions.assertNotNull(assetService.createAsset(getTestAsset()).getId());
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void testUpdate() throws Exception {
		AssetEntity fetchedAsset = assetService.getAssetById(1L);
	    fetchedAsset.setIsinuse(true);
	    assetService.updateAssetById(1L,fetchedAsset);
	    Assertions.assertEquals(true, fetchedAsset.getIsinuse());
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void testRelease_throwsException() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			assetService.releaseAssetById(2L, Calendar.getInstance().getTime());
	    });
		Assertions.assertTrue(exception.getMessage().contains("This asset is in use cannot be release"));
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void testUpdate_throwsException() throws Exception {
		AssetEntity fetchedAsset = assetService.getAssetById(3L);
	    fetchedAsset.setTitle("Hello");
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			assetService.updateAssetById(3L ,fetchedAsset);
	    });
		Assertions.assertTrue(exception.getMessage().contains("Asset is released and cannot be updated contact DB Admin "));
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void testGetAsset_throwsException() throws Exception {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			assetService.getAssetById(10L);
	    });
		Assertions.assertTrue(exception.getMessage().contains("Asset not found for this id :: "));
		
	}
	

	private AssetEntity getTestAsset() {
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setAddeddate(Calendar.getInstance().getTime());
		assetEntity.setDesrciption("This is a demo asset");
		assetEntity.setIsinuse(true);
		assetEntity.setBondedid("WT-01");
		assetEntity.setTitle("HondaAsset");
		return assetEntity;
	}

}
