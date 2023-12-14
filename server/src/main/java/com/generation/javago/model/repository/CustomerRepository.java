package com.generation.javago.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.generation.javago.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

@Procedure(value = "refreshDb")

	void refreshTest();

	Customer findByEmail(String email);

}
