package com.avipeta.b11.userasset.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.entity.TaggedAssetEntity;
import com.avipeta.b11.userasset.exception.InvalidArgumentException;
import com.avipeta.b11.userasset.exception.ResourceNotFoundException;
import com.avipeta.b11.userasset.repo.RemoteAssetServiceRepo;
import com.avipeta.b11.userasset.repo.RemoteUserServiceRepo;



@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
public class UserAssetServiceTest {
	
	
	@Autowired
	UserAssetService  userassetSerivce;
	
	@Autowired
	RemoteUserServiceRepo remoteUserRepo;
	
	@Autowired
	RemoteAssetServiceRepo remoteAssetRepo;
	
	
	@AfterEach
	public void resetDB() {
		userassetSerivce.resetDB();
	}
	
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_transfer_from_inventory() throws Exception {
		ActiveUserEntity transferEntity = userassetSerivce.transferAsset(5L, 0L, 1L, 1L);
		ActiveUserEntity dbentity = userassetSerivce.getActiveUserByRemoteId(transferEntity.getRemoteuserid());
		Assertions.assertEquals("WT-01", dbentity.getTaggedAssets().get(0).getBondedid());
		Assertions.assertTrue(remoteUserRepo.getUserById(1L).isTagged());
		Assertions.assertTrue(remoteAssetRepo.getAssetById(1L).getIsinuse());
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_releaseduser() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			 userassetSerivce.transferAsset(5L, 3L, 1L, 1L);
	    });
		System.out.println(exception.getMessage());
		Assertions.assertTrue(exception.getMessage().contains("This user is already released cannot be used in asset transfer emp id 1234569"));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_to_releaseduser() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			 userassetSerivce.transferAsset(5L, 0L, 3L, 1L);
	    });
		System.out.println(exception.getMessage());
		Assertions.assertTrue(exception.getMessage().contains("This user is already released cannot be used in asset transfer emp id  1234569"));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_released_asset() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			 userassetSerivce.transferAsset(5L, 0L, 1L, 3L);
	    });
		System.out.println(exception.getMessage());
		Assertions.assertTrue(exception.getMessage().contains("Cannot transfer a released asset  "));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_non_activeuser_asset_transfer_to_inventory() throws Exception {
		ActiveUserEntity transferEntity = userassetSerivce.transferAsset(5L, 2L, 0L, 2L);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			 userassetSerivce.getActiveUserByRemoteId(transferEntity.getRemoteuserid());
	    });
		Assertions.assertTrue(exception.getMessage().contains("ActiveUser not found for this id :: " +transferEntity.getRemoteuserid()));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_transfer_of_free_asset_to_inventory() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			userassetSerivce.transferAsset(5L, 2l, 0L, 1L);
	    });
		Assertions.assertTrue(exception.getMessage().contains("This asset is not in use cannot be transferred back to inventory  "+ "WT-01"));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_transfer_between_activeusers() throws Exception {
		ActiveUserEntity totransferEntity = userassetSerivce.transferAsset(5L, 4L, 2L, 5L);
		Assertions.assertTrue(totransferEntity.getTaggedAssets().size() == 2);
		Assertions.assertTrue(userassetSerivce.getActiveUserByRemoteId(4L).getTaggedAssets().size() == 1);
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_transfer_between_active_and_newusers() throws Exception {
		ActiveUserEntity totransferEntity = userassetSerivce.transferAsset(5L, 4L, 1L, 5L);
		Assertions.assertTrue(totransferEntity.getTaggedAssets().size() == 1);
		Assertions.assertTrue(userassetSerivce.getActiveUserByRemoteId(4L).getTaggedAssets().size() == 1);
		
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_return_to_inventory_activeuser_multiple_assets() throws Exception {
		ActiveUserEntity totransferEntity = userassetSerivce.transferAsset(5L, 4L, 0L, 5L);
		Assertions.assertTrue(userassetSerivce.getActiveUserByRemoteId(4L).getTaggedAssets().size() == 1);
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_return_from_inventory_activeuser_multiple_assets() throws Exception {
		ActiveUserEntity totransferEntity = userassetSerivce.transferAsset(5L, 0L, 4L, 1L);
		Assertions.assertTrue(userassetSerivce.getActiveUserByRemoteId(4L).getTaggedAssets().size() == 3);
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_return_to_inventory() throws Exception {
		ActiveUserEntity totransferEntity = userassetSerivce.transferAsset(5L, 2L, 0L, 2L);
		Assertions.assertFalse(remoteUserRepo.getUserById(2L).isTagged());
		Assertions.assertFalse(remoteAssetRepo.getAssetById(2L).getIsinuse());
		Assertions.assertTrue(totransferEntity.getTaggedAssets().size() == 0);
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_miss_match_asset_transfer_to_inventory() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			userassetSerivce.transferAsset(5L, 4L, 0L, 2L);
	    });
		Assertions.assertTrue(exception.getMessage().contains("This asset is either not tagged to the"));
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_miss_match_asset_transfer_from_inventory() throws Exception {
		Exception exception = assertThrows(InvalidArgumentException.class, () -> {
			userassetSerivce.transferAsset(5L, 0L, 2L, 2L);
	    });
		Assertions.assertTrue(exception.getMessage().contains("This asset is already tagged please do transfer from User"));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_miss_match_asset_transfer_to_from_inventory() throws Exception {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			userassetSerivce.transferAsset(5L, 0L, 0L, 2L);
	    });
		Assertions.assertTrue(exception.getMessage().contains("This id is not present in repository id: " +0L));
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_exception_for_invalid_bonded_id() throws Exception {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			userassetSerivce.getUserByBondedAssetId("WT-100");
	    });
		Assertions.assertTrue(exception.getMessage().contains("Asset not found for this id"));
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_activeuser_from_bonded_id() throws Exception {
		ActiveUserEntity totransferEntity =userassetSerivce.getUserByBondedAssetId("WT-02");
		Assertions.assertTrue(totransferEntity.getFirstname().equals("Tagged1"));		
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_get_all_tagged_assets() throws Exception {
		List<TaggedAssetEntity> allassets = userassetSerivce.getAllTaggedAssets();
		Assertions.assertTrue(allassets.size() == 3);		
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_get_all_activeusers() throws Exception {
		List<ActiveUserEntity> allUsers = userassetSerivce.getAllActiveUsers();
		Assertions.assertTrue(allUsers.size() == 2);		
	}
	
	
	

}
