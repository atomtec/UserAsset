package com.avipeta.b11.asset.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import com.avipeta.b11.asset.Utility;
import com.avipeta.b11.asset.entity.AssetEntity;

//Writing Integration Tests Only

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class AssetControllerTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String deleteSql = "TRUNCATE asset";
	
	@BeforeEach 
	public void resetDB() {
		//Since I am not mocking and running a full servlet 
		//The tests are now unmanaged so roll back will not take place
		//SO I will have to manually delete before every test
		jdbcTemplate.update(deleteSql);
		 
	}
	
	@Test
	public void testDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/",
				String.class)).contains("Hello World!");
	}
	
	
	@Test
	public void testTestCreateAsset() throws Exception {
		AssetEntity assetEntity = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/testcreateasset",
				AssetEntity.class);
		assertThat(assetEntity.getBondedid().equals("WT-01"));
	}
	
	
	@Test
	public void testgetAllAsset() throws Exception {
		create3Users();
		AssetEntity[] assets  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/asset",
				AssetEntity[].class);
		assertThat(assets.length == 3);
	}
	
	
	@Test
	public void createAsset() throws Exception {
		String url = "http://localhost:" + port + "/api/v1/asset";
		AssetEntity asset  = this.restTemplate.postForObject(url,Utility.getTestAsset(),
				AssetEntity.class);
		assertThat(asset.getTitle().equals("HondaAsset"));
	}
	
	
	
	@Test
	public void testgetAssetById() throws Exception {
		String url = "http://localhost:" + port + "/api/v1/asset";
		AssetEntity asset  = this.restTemplate.postForObject(url,Utility.getTestAsset2(),
				AssetEntity.class);
		AssetEntity dbasset  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/asset/{id}",
				AssetEntity.class,asset.getId());
		assertThat(dbasset.getTitle().equals(asset.getTitle()));
	}
	
	
	@Test
	public void testgetAssetByReleaseStatusTrue() throws Exception {
		create3Users();
		AssetEntity[] assets  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/asset/released/true",
				AssetEntity[].class);
		assertTrue(assets.length == 1);
	}
	
	@Test
	public void testgetAssetByInUseTrue() throws Exception {
		create3Users();
		AssetEntity[] assets  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1//asset/inuse/true",
				AssetEntity[].class);
		assertTrue(assets.length == 2);
	}
	
	@Test
	public void testgetAssetByReleaseStatusFalse() throws Exception {
		create3Users();
		AssetEntity[] assets  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/asset/released/false",
				AssetEntity[].class);
		assertTrue(assets.length == 2);
	}
	
	
	@Test
	public void testgetAssetByInUseFalse() throws Exception {
		create3Users();
		AssetEntity[] assets  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1//asset/inuse/false",
				AssetEntity[].class);
		assertTrue(assets.length == 1);
	}
	
	
	@Test
	public void testUpdateAssetById() throws Exception {
		String url = "http://localhost:" + port + "/api/v1/asset";
		AssetEntity asset  = this.restTemplate.postForObject(url,Utility.getTestAsset2(),
				AssetEntity.class);
		asset.setTitle("Siyanat");
		asset.setIsinuse(false); //should not be updated 
		asset.setIsreleased(true);// should not be updated 
		this.restTemplate.put("http://localhost:" + port + "/api/v1/asset/{id}", asset, asset.getId());
		AssetEntity dbasset  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/asset/{id}",
				AssetEntity.class,asset.getId());
		assertTrue(dbasset.getTitle().equals("Siyanat"));
		assertTrue(dbasset.getIsinuse());
		assertFalse(dbasset.getIsreleased());
	}
	
	
	
	private void  create3Users() {
		String url = "http://localhost:" + port + "/api/v1/asset";
		this.restTemplate.postForObject(url,Utility.getTestAsset3(),
				AssetEntity.class);
		this.restTemplate.postForObject(url,Utility.getTestAsset(),
				AssetEntity.class);
		this.restTemplate.postForObject(url,Utility.getTestAsset2(),
				AssetEntity.class);
	}
	

}
