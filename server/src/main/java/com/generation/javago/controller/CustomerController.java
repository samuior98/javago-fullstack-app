package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.controller.util.InvalidEntityException;
import com.generation.javago.model.dto.customer.CustomerDTO;
import com.generation.javago.model.dto.customer.CustomerDTOFull;
import com.generation.javago.model.dto.customer.CustomerDTOWrite;
import com.generation.javago.model.entity.Customer;
import com.generation.javago.model.repository.CustomerRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CustomerController
{
	@Autowired
	CustomerRepository customerRepo;

	@GetMapping("/customer")
	public List<CustomerDTOFull> getAll()
	{
		return customerRepo.findAll()
							.stream()
							.map(el -> new CustomerDTOFull(el))
							.toList();
	}

	@GetMapping("/customer/{id}")
	public CustomerDTOFull getOne(@PathVariable Integer id)
	{
		Optional<Customer> opt = customerRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find customer for id " +id);

		return new CustomerDTOFull(opt.get());
	}

	@GetMapping("/customer/{email}/email")
	public Integer getOne(@PathVariable String email)
	{
		Customer a= customerRepo.findByEmail(email);
		if(a == null) throw new NoSuchElementException("No Admin with such username: " + email);
		return a.getId();
	}

	@PostMapping("/customer")
	public CustomerDTO insert(@RequestBody CustomerDTOWrite dto)
	{
		Customer toSave = dto.convertToCustomer();
		if(!toSave.isValid())
		{
			StringBuilder build = new StringBuilder();
			toSave.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		return new CustomerDTO(customerRepo.save(toSave));
	}

	@PutMapping("/customer/{id}")
	public CustomerDTO update(@RequestBody CustomerDTOWrite dto, @PathVariable Integer id)
	{
		Optional<Customer> opt = customerRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find customer for id " +id);

		Customer toUpdate = dto.convertToCustomer();
		toUpdate.setId(id);

		if(!toUpdate.isValid())
		{
			StringBuilder build = new StringBuilder();
			toUpdate.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		return new CustomerDTO(customerRepo.save(toUpdate));
	}

	@DeleteMapping("/customer/{id}")
	public void delete(@PathVariable Integer id)
	{
		Optional<Customer> opt = customerRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find customer for id " +id);

		customerRepo.delete(opt.get());
	}
}
