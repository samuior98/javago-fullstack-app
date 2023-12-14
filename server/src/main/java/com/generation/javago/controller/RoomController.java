package com.generation.javago.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javago.controller.util.InvalidEntityException;
import com.generation.javago.model.dto.room.RoomDTO;
import com.generation.javago.model.dto.room.RoomDTOFull;
import com.generation.javago.model.entity.Room;
import com.generation.javago.model.repository.RoomRepository;

@RestController
@RequestMapping("/api")
public class RoomController
{
	@Autowired
	RoomRepository roomRepo;

	@GetMapping("/room")
	public List<RoomDTO> getAll()
	{
		return roomRepo.findAll()
							.stream()
							.map(el -> new RoomDTO(el))
							.toList();
	}

	@GetMapping("/room/{id}")
	public RoomDTOFull getOne(@PathVariable Integer id)
	{
		Optional<Room> opt = roomRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find room for id " +id);

		return new RoomDTOFull(opt.get());
	}

	@PostMapping("/room")
	public RoomDTO insert(@RequestBody RoomDTO dto)
	{
		Room toSave = dto.convertToRoom();
		if(!toSave.isValid())
		{
			StringBuilder build = new StringBuilder();
			toSave.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		return new RoomDTO(roomRepo.save(toSave));
	}

	@PutMapping("/room/{id}")
	public RoomDTO update(@RequestBody RoomDTO dto, @PathVariable Integer id)
	{
		Optional<Room> opt = roomRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find room for id " +id);

		Room toUpdate = dto.convertToRoom();
		toUpdate.setId(id);

		if(!toUpdate.isValid())
		{
			StringBuilder build = new StringBuilder();
			toUpdate.getErrors().forEach(build::append);
			throw new InvalidEntityException(build.toString());
		}

		return new RoomDTO(roomRepo.save(toUpdate));
	}

	@DeleteMapping("/room/{id}")
	public void delete(@PathVariable Integer id)
	{
		Optional<Room> opt = roomRepo.findById(id);
		if(opt.isEmpty())
			throw new NoSuchElementException("Could not find room for id " +id);

		roomRepo.delete(opt.get());
	}
}
