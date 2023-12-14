package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;

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
import com.generation.javago.model.dto.admin.AdminDTO;
import com.generation.javago.model.dto.admin.AdminDTOWrite;
import com.generation.javago.model.entity.Admin;
import com.generation.javago.model.repository.AdminRepository;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdminController
{
	@Autowired
	AdminRepository adminRepo;

	@GetMapping("/admin")
	public List<AdminDTO> getAll()
	{
		List<AdminDTO> admin =adminRepo.findAll()
										.stream()
										.map(el-> new AdminDTO(el))
										.toList();
		return admin;
	}

	@GetMapping("/admin/{id}")
	public AdminDTO getOne(@PathVariable Integer id)
	{
		Admin a= adminRepo.findById(id).get();
		if(a == null) throw new NoSuchElementException("No Admin with such id: " + id);
		return new AdminDTO(a);
	}

	@GetMapping("/admin/{email}/email")
	public Integer getOne(@PathVariable String email)
	{
		Admin a= adminRepo.findByEmail(email);
		if(a == null) throw new NoSuchElementException("No Admin with such username: " + email);
		return a.getId();
	}

	@PostMapping("/admin")
	public AdminDTO insert(@RequestBody AdminDTOWrite dto)
	{
		Admin a= dto.convertToAdmin();
		if(!a.isValid()) throw new InvalidEntityException("Invalid admin");
		adminRepo.save(a);
		return new AdminDTO(a);
	}

	@PutMapping("/admin/{id}")
	public AdminDTO update(@RequestBody AdminDTOWrite dto)
	{
		Admin a= adminRepo.findById(dto.getId()).get();
		if(a == null) throw new NoSuchElementException("No Admin with such id: " + dto.getId());
		a= dto.convertToAdmin();
		a.setId(dto.getId());
		if(!a.isValid()) throw new InvalidEntityException("Invalid admin");
		adminRepo.save(a);
		return new AdminDTO(a);
	}

	@DeleteMapping("/admin/{id}")
	public void delete(@PathVariable Integer id)
	{
		Admin a= adminRepo.findById(id).get();
		if(a == null) throw new NoSuchElementException("No Admin with such id: " + id);
		adminRepo.delete(a);
	}
}

