package com.generation.javago.model.dto.bookingmask;

import com.generation.javago.model.mask.BookingMask;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingMaskDTO
{
	private String  ref;
	private String  availability;
	private Integer idRoom;

	public BookingMaskDTO(BookingMask bm)
	{
		this.ref = bm.getRef();
		this.availability = bm.getAvaliability();
		this.idRoom = bm.getRoom().getId();
	}
}
