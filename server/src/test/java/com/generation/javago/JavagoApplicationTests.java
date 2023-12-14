package com.generation.javago;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.generation.javago.model.repository.AdminRepository;

@SpringBootTest
class JavagoApplicationTests {

	@Autowired
	AdminRepository adminRepo;


	@Test
	void contextLoads() {
	}

}
