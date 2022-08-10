package com.avipeta.b11.userasset.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.avipeta.b11.userasset.TestUtils;
import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.entity.TaggedAssetEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserAssetControllerTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@BeforeEach 
	public void setDB() {
		//Hell Spring boot patch request has a problem which can be fixed by this line
		this.restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		//Since I am not mocking and running a full servlet 
		//The tests are now unmanaged so roll back will not take place
		//SO I will have to manually delete before every test
		
		jdbcTemplate.update("insert into activeusers(id,firstname,lastname,remoteuserid,employeeid,phonenumber)\r\n" + 
				" values (1,'Tagged1','Tagged1 Last', 2,'1234567','983164')");
		jdbcTemplate.update("insert into activeusers(id,firstname,lastname,remoteuserid,employeeid,phonenumber)\r\n" + 
				" values (2,'Tagged2','Tagged2 Last', 4,'1234569','983166')");
		jdbcTemplate.update("insert into taggedassets(id,taggeduser_id,bondedid,remoteassetid,title,desrciption)\r\n" + 
				" values (1,1,'WT-02',2,'Tuner 01','Tuner it is ')");
		jdbcTemplate.update("insert into taggedassets(id,taggeduser_id,bondedid,remoteassetid,title,desrciption)\r\n" + 
				" values (2,2,'WT-04',4,'LCD 01','LCD it is ')");
		jdbcTemplate.update(" insert into taggedassets(id,taggeduser_id,bondedid,remoteassetid,title,desrciption)\r\n" + 
				" values (3,2,'WT-05',5,'Harness 01','Harness it is ');");
		
		 
	}
	
	@AfterEach 
	public void resetDB() {
	
		//Since I am not mocking and running a full servlet 
		//The tests are now unmanaged so roll back will not take place
		//SO I will have to manually delete before every test
		
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE activeusers");
		jdbcTemplate.update("TRUNCATE taggedassets");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}
	
	
	@Test
	public void testDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/userasset/",
				String.class)).contains("Hello World!");
	}
	
	@Test
	public void test_transferasset_from_inventory() throws Exception {
		ActiveUserEntity activeUser = this.restTemplate.patchForObject("http://localhost:" + port + "/api/v1/userasset/transferasset",
				TestUtils.getTransferfromInventoryDTO(),ActiveUserEntity.class);
		assertTrue(activeUser.getTaggedAssets().size() == 1);
	}
	
	@Test
	public void test_getactiveuser_by_remote_id() throws Exception {
		ActiveUserEntity activeUser = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/userasset/activeuser/{userid}",
				ActiveUserEntity.class,2L);
		assertTrue(activeUser.getFirstname().equals("Tagged1"));
	}
	
	
	/*will come baxk to this when dealing with angular
	 * @Test public void test_exception_getactiveuser_by_inavlid_remote_id() {
	 * ResponseEntity<ActiveUserEntity> err =
	 * this.restTemplate.getForObject("http://localhost:" + port +
	 * "/api/v1/userasset/activeuser/{userid}", ResponseEntity.class,20L);
	 * assertTrue(err.getStatusCode().equals(HttpStatus.NOT_FOUND)); }
	 */
	
	
	
	
	@Test
	public void test_getall_active_users() throws Exception {
		ActiveUserEntity[] activeUser  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/userasset/activeuser",
				ActiveUserEntity[].class);
		assertTrue(activeUser.length == 2);
	}
	
	@Test
	public void test_getall_tagged_assets() throws Exception {
		TaggedAssetEntity[] taggedAssets  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/userasset/taggedasset",
				TaggedAssetEntity[].class);
		assertTrue(taggedAssets.length == 3);
	}
	
	@Test
	public void test_tagged_user_by_bondedid() throws Exception {
		ActiveUserEntity activeUser  = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/userasset/activeuser/taggedasset/{bondedid}",
				ActiveUserEntity.class,"WT-02");
		assertTrue(activeUser.getFirstname().equals("Tagged1"));
	}

}
