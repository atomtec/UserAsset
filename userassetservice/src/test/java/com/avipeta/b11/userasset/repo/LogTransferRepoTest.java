package com.avipeta.b11.userasset.repo;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.service.UserAssetService;

@SpringBootTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class LogTransferRepoTest {
	
	@Autowired
	LogTransferRepo repository;
	
	@Autowired
	UserAssetService  userassetSerivce;
	
	@AfterEach
	public void resetDB() {
		userassetSerivce.resetDB();
	}
	
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_to_username_log() throws Exception {
		ActiveUserEntity transferEntity = userassetSerivce.transferAsset(5L, 0L, 1L, 1L);
		ActiveUserEntity dbentity = userassetSerivce.getActiveUserByRemoteId(transferEntity.getRemoteuserid());
		Assertions.assertEquals("WT-01", dbentity.getTaggedAssets().get(0).getBondedid());
		Assertions.assertTrue(repository.findAll().get(0).getTousername().contains("Free"));
		Assertions.assertTrue(repository.findAll().get(0).getFromusername().contains("INVENTORY"));
	}
}
