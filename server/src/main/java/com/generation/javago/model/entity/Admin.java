package com.generation.javago.model.entity;

import java.util.List;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Definisce un amministratore/staff che specializza uno User generico.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User
{
	private String employement;
	private Double salary;

	@Override
	public List<String> getErrors()
	{
		List<String> errors= super.getErrors();
		if(!hasValue(employement))
			errors.add("-Missing or invalid value for field 'employement'");
		if(salary == null || salary <= 0)
			errors.add("-Missing or invalid value for field 'salary'");

		return errors;
	}

	public Admin(Integer id, String email, String password, String employement, Double salary)
	{
		super(id, email, password);
		this.employement= employement;
		this.salary= salary;
	}

}