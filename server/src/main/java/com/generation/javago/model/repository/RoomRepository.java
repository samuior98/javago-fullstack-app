package com.generation.javago.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.generation.javago.model.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Procedure(value = "refreshDb")

	void refreshTest();
}
