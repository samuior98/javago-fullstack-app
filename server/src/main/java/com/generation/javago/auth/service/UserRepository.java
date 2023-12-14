package com.generation.javago.auth.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.auth.model.UserInDb;

public interface UserRepository extends JpaRepository<UserInDb, String>
{

	UserInDb findByUsername(String username);

}
