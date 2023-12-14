package com.generation.javago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.mask.PriceMask;
import com.generation.javago.model.repository.PriceMaskRepository;
import com.generation.javago.model.util.MaskHandler;

@RestController
@RequestMapping("/api")
public class PriceMaskController
{
	@Autowired
	PriceMaskRepository maskRepo;
	@Autowired
	MaskHandler mask;

	@GetMapping("/pricemask")
	public List<PriceMask> getAllPriceMasks()
	{
		List<PriceMask> res = maskRepo.findAll();
		return res;
	}

	/**
	 * Restituisce tutte le PriceMask per un dato anno.
	 */
	@GetMapping("/pricemask/{year}")
	public List<PriceMask> getOneYearPriceMask(@PathVariable Integer year)
	{
		List<PriceMask> res = maskRepo.findByRefStartsWith(""+year);
		return res;
	}

	/**
	 * Inizializza tutte le PriceMask per un dato anno.
	 */
	@PostMapping("/pricemask/{year}")
	public void initYearBookingMasks(@PathVariable Integer year)
	{
		mask.initYearPrices(year);
	}
}
