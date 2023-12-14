package com.generation.javago.model.dto.admin;

import com.generation.javago.model.entity.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO
{
	protected String email,employement;
	protected Double salary;
	protected Integer id;

	public AdminDTO(Admin a)
	{
		this.email= a.getEmail();
		this.employement= a.getEmployement();
		this.salary= a.getSalary();
		this.id= a.getId();
	}

}