package com.generation.javago.model.dto.customer;

import java.util.ArrayList;
import java.util.List;

import com.generation.javago.model.dto.roombooking.RoomBookingDTO;
import com.generation.javago.model.entity.Customer;
import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTOFull extends CustomerDTO
{
	private List<RoomBookingDTO> bookings;

	public CustomerDTOFull(Customer c)
	{
		super(c);
		this.bookings= new ArrayList<>();

		for(RoomBooking r: c.getBookings())
			bookings.add(new RoomBookingDTO(r));
	}

}