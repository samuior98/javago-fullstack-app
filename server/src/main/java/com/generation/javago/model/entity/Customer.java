package com.generation.javago.model.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Definisce un Customer che specializza uno User generico.<br>
 * possiede i figli RoomBooking, ovvero le sue prenotazioni.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User
{
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<RoomBooking> bookings;
}