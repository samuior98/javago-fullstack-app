package com.generation.javago.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.model.mask.PriceMask;

public interface PriceMaskRepository extends JpaRepository<PriceMask,String>
{
	List<PriceMask> findByRefStartsWith(String year);
}
