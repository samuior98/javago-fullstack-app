package com.generation.javago.model.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.generation.javago.library.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Definisce una prenotazione: possiede i padri Customer e Room, rispettivamente <br>
 * Il cliente che ha effettuato la prenotazione e la stanza prenotata.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room_booking")
public class RoomBooking extends BaseEntity
{
	private final static int MAXSTAY = 21;

	private LocalDateTime checkin, checkout;
	private Integer guests;
	private String notes;
	private boolean confirmed;
	private double price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_room")
	private Room room;

	@Override
	public List<String> getErrors()
	{
		List<String> errors = new ArrayList<>();
		if(checkin == null)
			errors.add("-Missing or invalid value for field 'checkin'");
		if(checkout == null)
			errors.add("-Missing or invalid value for field 'checkout'");
		if(checkin.isAfter(checkout) || checkin.isEqual(checkout))
			errors.add("-Invalid values for fields 'checkout','checkin'");
		if(checkin.until(checkout, ChronoUnit.DAYS)>MAXSTAY)
			errors.add("-Stay must not exceed 21 days");
		if(guests == null || guests<=0 || guests > room.getCapacity())
			errors.add("-Missing or invalid value for field 'guest'");
		if(customer == null)
			errors.add("-Missing or invalid value for field 'customer'");
		if(room == null)
			errors.add("-Missing or invalid value for field 'room'");

		return errors;
	}

	/*
	 * Calcola e restituisce i giorni di pernottamento.
	 */
	public int days()
	{
		return (int)checkin.until(checkout, ChronoUnit.DAYS);
	}

	// 10/09/2023   ->   gg/mm/aaaa/hh/min/sec
	public void setCheckin(String date)
	{
		try
		{
			String[] d= date.split("/");
			LocalDateTime ld= LocalDateTime.parse(date);
			this.checkin= ld;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Data format not valid. Correct format dd/mm/yyyy/hh/min/sec");
		}
	}


	public void setCheckout(String date)
	{
		try
		{
			String[] d= date.split("/");
			LocalDateTime ld= LocalDateTime.parse(date);
			this.checkout= ld;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Data format not valid. Correct format dd/mm/yyyy/hh/min/sec");
		}
	}
}