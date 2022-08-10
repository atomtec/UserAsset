package com.example.demo.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestCOntroller {
	
	  @Autowired
      RestTemplate restTemplate;

	
	@GetMapping({"/test"})
    public String test(Model model) {
    	 System.out.println("Getting School details for ");
    	 
         ResponseEntity<String> response = restTemplate.getForEntity("http://student-service/greeting", String.class);
                                 
  
         System.out.println("Response Received as " + response);
  
         return  "redirect:/http://student-service/greeting";
    }
	

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
