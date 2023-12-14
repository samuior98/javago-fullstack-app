package com.generation.javago.model.dto.customer;

import com.generation.javago.model.entity.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTOWrite
{

	protected String email;
	private String password;

	public CustomerDTOWrite(Customer c)
	{
		this.email = c.getEmail();
		this.password = c.getPassword();
	}


	public Customer convertToCustomer()
	{
		Customer c = new Customer();
		c.setEmail(email);
		c.setPassword(password);
		return c;
	}

}