package com.generation.javago.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.generation.javago.library.BaseEntity;
import com.generation.javago.model.mask.BookingMask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Classe/entità Room, che rappresenta una stanza del nostro b&b, estende BaseEntity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity
{
	private String name, notes;
	private Integer capacity;
	private Double pricePerNight;

	@OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
	private Set<RoomBooking> bookings;

	@OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
	private Set<BookingMask> maps;

	@Override
	public List<String> getErrors()
	{
		List<String> errors= new ArrayList<>();
		if(!hasValue(name))
			errors.add("-Missing or invalid value for field 'name'");
		if(!hasValue(notes))
			errors.add("-Missing or invalid value for field 'notes'");
		if(capacity == null || capacity <= 0)
			errors.add("-Missing or invalid value for field 'capacity'");
		if(pricePerNight == null || pricePerNight <= 0)
			errors.add("-Missing or invalid value for field 'pricePerNight'");

		return errors;
	}

}