package com.generation.javago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.model.dto.bookingmask.BookingMaskDTO;
import com.generation.javago.model.repository.BookingMaskRepository;
import com.generation.javago.model.repository.CustomerRepository;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.util.MaskHandler;

@RestController
@RequestMapping("/api")
public class BookingMaskController
{
	@Autowired
	BookingMaskRepository maskRepo;
	@Autowired
	MaskHandler mask;

	@Autowired
	RoomRepository roomRepo;
	@Autowired
	RoomBookingRepository bookingRepo;
	@Autowired
	CustomerRepository customerRepo;

	@GetMapping("/bookingmask")
	public List<BookingMaskDTO> getAllBookingMasks()
	{
		List<BookingMaskDTO> res = maskRepo.findAll()
											.stream()
											.map(el -> new BookingMaskDTO(el))
											.toList();
		return res;
	}

	@GetMapping("/bookingmask/{year}")
	public List<BookingMaskDTO> getYearBookingMask(@PathVariable Integer year)
	{
		List<BookingMaskDTO> res = maskRepo.findByRefStartsWithOrRefStartsWith(""+year, ""+(year+1))
											.stream()
											.map(el -> new BookingMaskDTO(el))
											.toList();
		
		return res;
	}


	/**
	 * Restituisce tutte le BookingMask di una data Room per un dato anno.
	 */
	@GetMapping("/bookingmask/{year}/{roomname}")
	public List<BookingMaskDTO> getOneRoomYearBookingMask(@PathVariable Integer year, @PathVariable String roomname)
	{
		List<BookingMaskDTO> res = maskRepo.findByRefStartsWithAndRefEndsWith(""+year,roomname)
											.stream()
											.map(el -> new BookingMaskDTO(el))
											.toList();
		return res;
	}

	/**
	 * Inizializza tutte le BookingMask per un dato anno.
	 */
	@PostMapping("/bookingmask/{year}")
	public void initYearBookingMasks(@PathVariable Integer year)
	{
		mask.initYearMasks(year);
	}
}
