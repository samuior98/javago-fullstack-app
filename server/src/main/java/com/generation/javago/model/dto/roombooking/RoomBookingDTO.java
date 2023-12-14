package com.generation.javago.model.dto.roombooking;

import com.generation.javago.model.entity.Customer;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.entity.RoomBooking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomBookingDTO
{
	private int id;
	private String checkIn,checkOut,notes;
	private Integer guests;
	private boolean confirmed;
	private Integer customerId;
	private Integer roomId;
	private Double  price;

	public RoomBookingDTO(RoomBooking rb)
	{
		this.checkIn= rb.getCheckin().toString();
		this.checkOut= rb.getCheckout().toString();
		this.notes= rb.getNotes();
		this.guests = rb.getGuests();
		this.confirmed = rb.isConfirmed();
		this.customerId= rb.getCustomer().getId();
		this.roomId= rb.getRoom().getId();
		this.id= rb.getId();
		this.price = rb.getPrice();
	}

	public RoomBooking convertToRoomBooking(Customer c, Room r)
	{
		RoomBooking a = new RoomBooking() ;
		a.setCheckin(checkIn);
		a.setCheckout(checkOut);
		a.setNotes(notes);
		a.setConfirmed(confirmed);
		a.setCustomer(c);
		a.setGuests(guests);
		a.setRoom(r);
		a.setPrice(price);
		return a;
	}
}