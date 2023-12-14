package com.generation.javago.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.generation.javago.model.entity.RoomBooking;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer>{

	@Procedure(value = "refreshDb")

	void refreshTest();



}
