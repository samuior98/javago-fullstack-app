package com.generation.javago.model.dto.customer;

import com.generation.javago.model.entity.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

	protected int id;
	protected String email;

	public CustomerDTO(Customer c) {
		this.id= c.getId();
		this.email= c.getEmail();
	}

}