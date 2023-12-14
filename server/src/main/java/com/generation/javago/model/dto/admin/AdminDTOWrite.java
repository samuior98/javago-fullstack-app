package com.generation.javago.model.dto.admin;

import com.generation.javago.model.entity.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTOWrite extends AdminDTO
{
	String password;


	public Admin convertToAdmin()
	{
		Admin o = new Admin();
		o.setEmail(email);
		o.setEmployement(employement);
		o.setPassword(password);
		o.setSalary(salary);
		return o;
	}

}
