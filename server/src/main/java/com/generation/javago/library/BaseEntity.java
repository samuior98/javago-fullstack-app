package com.generation.javago.library;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
/**
 * Classe base per tutte le altre classi
 * @author samui
 *
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	/**
	 * Metodo che restituisce una lista di errori
	 * (proprietà oggetto non valide)
	 * @return
	 */
	public abstract List<String> getErrors();

	/**
	 * Ritorna true se la lista di errori è vuota,
	 * false altrimenti
	 * @return
	 */
	public boolean isValid() {
		return getErrors().size() == 0;
	}

	/**
	 * Riceve in input una stringa.
	 * Restituisce true se l'input è presente
	 * (non null e non vuota), false altrimenti
	 * @param s
	 * @return
	 */
	public boolean hasValue(String s) {
		return s!=null && !s.isBlank();
	}

}