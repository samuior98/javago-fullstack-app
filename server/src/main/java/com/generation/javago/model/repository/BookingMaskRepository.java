package com.generation.javago.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.generation.javago.model.mask.BookingMask;

public interface BookingMaskRepository extends JpaRepository<BookingMask,String>
{
	List<BookingMask> findByRefStartsWithAndRefEndsWith(String year, String roomName);

	@Procedure(value = "refreshDb")

	void refreshTest();

	List<BookingMask> findByRefStartsWithOrRefStartsWith(String year, String year2);
}
