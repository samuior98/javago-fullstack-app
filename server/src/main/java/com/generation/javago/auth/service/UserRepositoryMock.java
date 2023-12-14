package com.generation.javago.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryMock
{

	private Map<String,User> users = new HashMap<>();


	public UserRepositoryMock()
	{

	}

	User getUserByUsername(String username)
	{
		if(users.containsKey(username))
		{
			User old = users.get(username);
			return new User(old.getUsername(),old.getPassword(),old.getAuthorities());
		}

		return null;
	}
}
