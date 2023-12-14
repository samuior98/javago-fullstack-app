package com.generation.javago.model.repository.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.generation.javago.model.entity.Admin;

@Component
public class AdminMockRepository
{
	List<Admin> content = new ArrayList<>();

	public AdminMockRepository()
	{
		content.add(new Admin(1, "nome1@dominio.com", "12341","propietario", 2000.00));
		content.add(new Admin(2, "nome2@dominio.com", "12342","dipendente1", 900.00));
		content.add(new Admin(3, "nome3@dominio.com", "12343","dipendente2", 800.00));
		content.add(new Admin(4, "nome4@dominio.com", "12344","dipendente3", 700.00));

	}

	public List<Admin> findAll()
	{
		return content;
	}

	public Admin findById(Integer id)
	{
		for(Admin a : content)
			if(a.getId()== id)
			return a;

		return null;
	}

	public Admin save(Admin a)
	{
		content.add(a);
		return a;
	}

	public void delete(Admin a)
	{
		content.remove(a);
	}
}
