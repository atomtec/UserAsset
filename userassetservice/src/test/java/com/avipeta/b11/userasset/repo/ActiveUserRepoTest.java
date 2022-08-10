package com.avipeta.b11.userasset.repo;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ActiveUserRepoTest {
	
	@Autowired
	ActiveUserRepo repository;
	
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_findByRemoteuserid_pass() throws Exception {
		Assertions.assertEquals("Tagged1", repository.findByRemoteuserid(2L).getFirstname());
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/asset-data.sql" })
	public void test_findByRemoteuserid_fail() throws Exception {
		Assertions.assertEquals(null, repository.findByRemoteuserid(10L));
	}

}
