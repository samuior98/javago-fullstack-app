package com.generation.javago.model.dto.room;

import com.generation.javago.model.entity.Room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO
{
	protected String name;
	protected Integer capacity;
	protected Double pricePerNight;
	protected String notes;
	protected int id;

	public RoomDTO(Room r)
	{
		this.name=r.getName();
		this.capacity=r.getCapacity();
		this.pricePerNight=r.getPricePerNight();
		this.notes=r.getNotes();
		this.id=r.getId();
	}

	public Room convertToRoom()
	{
		Room r = new Room();
		r.setName(name);
		r.setCapacity(capacity);
		r.setPricePerNight(pricePerNight);
		r.setNotes(notes);
		return r;
	}
}