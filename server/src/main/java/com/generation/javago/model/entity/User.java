package com.generation.javago.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;

import com.generation.javago.library.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe astratta da cui derivano tutte le entity del nostro DB.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends BaseEntity
{
	protected String email, password;

	@Override
	public List<String> getErrors()
	{
		List<String> errors= new ArrayList<>();
		if(!hasValue(email))
			errors.add("-Missing or invalid value for field 'email'");
		if(!hasValue(password))
			errors.add("-Missing or invalid value for field 'email'");
		if(!hasValue(password))
			errors.add("-Missing or invalid value for field 'password'");

		return errors;
	}

	public User(Integer id, String email, String password)
	{
		super(id);
		this.email= email;
		this.password= password;
	}
}