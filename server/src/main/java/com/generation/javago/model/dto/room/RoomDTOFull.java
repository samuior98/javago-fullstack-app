package com.generation.javago.model.dto.room;

import java.util.ArrayList;
import java.util.List;

import com.generation.javago.model.dto.roombooking.RoomBookingDTO;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTOFull extends RoomDTO
{
	private List<RoomBookingDTO> bookings;

	public RoomDTOFull(Room r)
	{
		super(r);

		this.bookings= new ArrayList<>();

		for(RoomBooking rb: r.getBookings())
		{
			bookings.add(new RoomBookingDTO(rb));
		}
	}
}
