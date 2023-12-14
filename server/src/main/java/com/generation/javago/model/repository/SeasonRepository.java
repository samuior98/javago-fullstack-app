package com.generation.javago.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.generation.javago.model.entity.Season;

public interface SeasonRepository extends JpaRepository<Season, Integer>{

	@Procedure(value = "refreshDb")

	void refreshTest();

	Optional<Season> findByTag(char c);
}
