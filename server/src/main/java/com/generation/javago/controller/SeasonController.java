package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.controller.util.InvalidEntityException;
import com.generation.javago.model.entity.Season;
import com.generation.javago.model.repository.SeasonRepository;
import com.generation.javago.model.util.MaskHandler;

@RestController
@RequestMapping("/api")
public class SeasonController
{
	@Autowired
	SeasonRepository seasonRepo;
	@Autowired
	MaskHandler mask;

	@GetMapping("/season")
	public List<Season> getAll()
	{
		return seasonRepo.findAll();
	}

	@GetMapping("/season/id")
	public Season getOneById(@PathVariable Integer id)
	{
		Optional<Season> opt = seasonRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find season for id " +id);

		return opt.get();
	}

	@GetMapping("/season/{tag}")
	public Season getOneByTag(@PathVariable char tag)
	{
		Optional<Season> opt = seasonRepo.findByTag(tag);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find season for id " +tag);

		return opt.get();
	}

	@PostMapping("/season")
	public Season insert(@RequestBody Season toInsert)
	{
		if(!toInsert.isValid())
		{
			StringBuilder build = new StringBuilder();
			toInsert.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		mask.updatePriceMask(toInsert, false);

		return seasonRepo.save(toInsert);
	}

	@PutMapping("/season/{id}")
	public Season insert(@RequestBody Season toInsert, @PathVariable Integer id)
	{
		Optional<Season> opt = seasonRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find season for id " +id);

		if(!toInsert.isValid())
		{
			StringBuilder build = new StringBuilder();
			toInsert.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		mask.updatePriceMask(opt.get(), true);
		mask.updatePriceMask(toInsert, false);

		return seasonRepo.save(toInsert);
	}

	@DeleteMapping("/season/{id}")
	public void delete(@PathVariable Integer id)
	{
		Optional<Season> opt = seasonRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find season for id " +id);

		mask.updatePriceMask(opt.get(), true);

		seasonRepo.deleteById(id);
	}
}
