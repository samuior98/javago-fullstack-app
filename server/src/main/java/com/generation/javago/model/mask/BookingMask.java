package com.generation.javago.model.mask;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.generation.javago.model.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una mappatura dei giorni di disponibilità per una data Room del B&B <br>
 * e per un dato mese e anno. contiene una stringa di n caratteri, dove n è il numero <br>
 * di giorni del mese, mentre ogni carattere può essere 0 o 1 a seconda della disponibilità. <br>
 * (0 = "libero", 1 = "occupato)
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking_mask")
public class BookingMask
{
	@Id
	private String ref; //2023-10-01A
	private String avaliability; //0000000000000000000000000000000 -> tanti zeri quanti i giorni del mese interessato.

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_room")
	private Room room;
}
