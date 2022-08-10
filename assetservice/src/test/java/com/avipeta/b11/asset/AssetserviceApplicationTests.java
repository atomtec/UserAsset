package com.avipeta.b11.asset;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class AssetserviceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource)
	{
	    return new JdbcTemplate(dataSource);
	}
	
	

}
