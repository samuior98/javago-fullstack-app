package com.generation.javago.model.mask;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una mappatura dei sovrapprezzi da applicare ai giorni di prenotazione per un dato mese e anno.<br>
 * Contiene una stringa di caratteri 'prices', dove ogni carattere corrisponde ad un giorno del mese, <br>
 * mentre il suo valore sarà una identificativo specifico per una Season. Iutti i giorni di prenotazione <br>
 * avranno il loro prezzo calcolato in base al prezzo base della stanza moltiplicato per il fattore <br>
 * della Season specifica di quel giorno.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price_mask")
public class PriceMask
{
	@Id
	private String ref;
	private String prices; //00000000AAA0000000000000000FFFF
}
