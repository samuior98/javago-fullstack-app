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
import com.generation.javago.model.dto.roombooking.RoomBookingDTO;
import com.generation.javago.model.entity.Customer;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;
import com.generation.javago.model.repository.BookingMaskRepository;
import com.generation.javago.model.repository.CustomerRepository;
import com.generation.javago.model.repository.RoomBookingRepository;
import com.generation.javago.model.repository.RoomRepository;
import com.generation.javago.model.util.MaskHandler;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RoomBookingController
{
	@Autowired
	RoomBookingRepository bookingRepo;
	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	RoomRepository roomRepo;
	@Autowired
	BookingMaskRepository bMaskRepo;
	@Autowired
	MaskHandler mask;

	@GetMapping("/booking")
	public List<RoomBookingDTO> getAll()
	{
		return bookingRepo.findAll()
							.stream()
							.map(el -> new RoomBookingDTO(el))
							.toList();
	}

	@GetMapping("/booking/{id}")
	public RoomBookingDTO getOne(@PathVariable Integer id)
	{
		Optional<RoomBooking> opt = bookingRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find booking for id " +id);

		return new RoomBookingDTO(opt.get());
	}

	@GetMapping("/booking/{email}/bookings")
	public List<RoomBookingDTO> getAllByUser(@PathVariable String email)
	{
		Customer cus= customerRepo.findByEmail(email);
		if(cus == null)
			throw new NoSuchElementException("Could not find customer for emal " + email);

		List<RoomBookingDTO> opt = cus.getBookings().stream().map(el -> new RoomBookingDTO(el)).toList();
		return opt;
	}


	@PostMapping("/booking/customer/{idc}/room/{idr}")
	public RoomBookingDTO insert(@RequestBody RoomBookingDTO dto, @PathVariable Integer idc, @PathVariable Integer idr)
	{
		Optional<Customer> optCustomer = customerRepo.findById(idc);
		if(optCustomer.isEmpty())
			throw new NoSuchElementException("Could not find customer for id " +idc);

		Optional<Room> optRoom = roomRepo.findById(idr);
		if(optRoom.isEmpty())
			throw new NoSuchElementException("Could not find room for id " +idr);

		RoomBooking toSave = dto.convertToRoomBooking(optCustomer.get(), optRoom.get());
		toSave.setPrice(mask.getBookingPrice(toSave));
		if(!toSave.isValid())
		{
			StringBuilder build = new StringBuilder();
			toSave.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		mask.updateBookingMask(toSave, false);

		return new RoomBookingDTO(bookingRepo.save(toSave));
	}

	@PutMapping("/booking/{id}")
	public RoomBookingDTO update(@RequestBody RoomBookingDTO dto, @PathVariable Integer id)
	{
		Optional<RoomBooking> optBooking = bookingRepo.findById(id);
		if(optBooking.isEmpty())
			throw new NoSuchElementException("Could not find booking for id " +id);

		RoomBooking toUpdate = optBooking.get();

		RoomBooking updated = dto.convertToRoomBooking(toUpdate.getCustomer(), toUpdate.getRoom());
		updated.setId(id);

		if(!updated.isValid())
		{
			StringBuilder build = new StringBuilder();
			updated.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}
		//Ripulisco le mask dei mesi interessati dalla prenotazione vecchia
		mask.updateBookingMask(toUpdate, true);
		//Aggiorno le mask dei mesi interessati dalla prenotazione aggiornata.
		mask.updateBookingMask(updated, false);

		return new RoomBookingDTO(bookingRepo.save(updated));
	}

	@DeleteMapping("/booking/{id}")
	public void delete(@PathVariable Integer id)
	{
		Optional<RoomBooking> optBooking = bookingRepo.findById(id);
		if(optBooking.isEmpty())
			throw new NoSuchElementException("Could not find booking for id " +id);

		//Ripulisco le mask dei mesi interessati dalla prenotazione da cancellare
		mask.updateBookingMask(optBooking.get(), true);

		bookingRepo.deleteById(id);
	}
}
