package com.generation.javago.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.generation.javago.model.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>
{
	@Procedure(value = "refreshDb")

	void refreshTest();

	Admin findByEmail(String email);

}
