package com.avipeta.b11.asset.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.asset.entity.AssetEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AssetRepoTest {
	
	@Autowired
    AssetRepo repository;
     
	
	@Test
    @Transactional
    @Sql(scripts = {"/asset-data.sql"})
	public void testFindInUseTrue() {
		List<AssetEntity> entity = repository.findByIsinuse(true);
		Assertions.assertTrue(entity.size() == 1);
	}
	
	@Test
    @Transactional
    @Sql(scripts = {"/asset-data.sql"})
	public void testFindInUseFalse() {
		List<AssetEntity> entity = repository.findByIsinuse(false);
		Assertions.assertTrue(entity.size() == 2);
	}
	
	
	
	@Test
    @Transactional
    @Sql(scripts = {"/asset-data.sql"})
	public void testFindIsReleasedTrue() {
		List<AssetEntity> entity = repository.findByIsreleased(true);
		Assertions.assertTrue(entity.size() == 1);
	}
	
	@Test
    @Transactional
    @Sql(scripts = {"/asset-data.sql"})
	public void testFindIsReleasedFalse() {
		List<AssetEntity> entity = repository.findByIsreleased(false);
		Assertions.assertTrue(entity.size() == 2);
	}
	
	
	@Test
    @Transactional
    @Sql(scripts = {"/asset-data.sql"})
	public void testFindByBondedId() {
		List<AssetEntity> entity = repository.findByBondedid("WT-101");
		Assertions.assertTrue(entity.size() == 1);
	}
	
	@Test
    @Transactional
    @Sql(scripts = {"/asset-data.sql"})
	public void testGetAllAsset() {
		List<AssetEntity> entity = repository.findAll();
		Assertions.assertTrue(entity.size() == 3);
	}
	
	


}
