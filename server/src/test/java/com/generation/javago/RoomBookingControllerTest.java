package com.generation.javago;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.javago.model.dto.roombooking.RoomBookingDTO;
import com.generation.javago.model.repository.RoomBookingRepository;

@SpringBootTest
(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = JavagoApplication.class
)

@AutoConfigureMockMvc
class RoomBookingControllerTest
{
	@Autowired
	MockMvc mock;
	@Autowired
	RoomBookingRepository rRepo;

	@Test
	void getAllTest() throws Exception
	{
		rRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders.get("/api/booking"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(bookingAllJson));
	}

	@Test
	void getOneTest() throws Exception
	{
		rRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders.get("/api/booking/37"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(booking1Json));

		mock.perform(MockMvcRequestBuilders.get("/api/booking/10"))
		.andExpect(status().is(404));

		mock.perform(MockMvcRequestBuilders.get("/api/booking/paperino"))
		.andExpect(status().is(400));
	}

	@Test
	void insertTest() throws Exception
	{
		rRepo.refreshTest();

		RoomBookingDTO toInsert= new RoomBookingDTO();
		toInsert.setCheckIn("31/05/2023/11/30/00");
		toInsert.setCheckOut("05/06/2023/11/30/00");
		toInsert.setGuests(2);
		toInsert.setNotes("blabla");
		toInsert.setConfirmed(true);
		toInsert.setCustomerId(1);
		toInsert.setRoomId(1);
		toInsert.setPrice(600.00);

		String r= "{\r\n"
				+ "    \"id\": 39,\r\n"
				+ "    \"checkIn\": \"2023-05-31T11:30\",\r\n"
				+ "    \"checkOut\": \"2023-06-05T11:30\",\r\n"
				+ "    \"notes\": \"blabla\",\r\n"
				+ "    \"guests\": 2,\r\n"
				+ "    \"confirmed\": true,\r\n"
				+ "    \"customerId\": 1,\r\n"
				+ "    \"roomId\": 1,\r\n"
				+ "    \"price\": 600.00\r\n"
				+ "}";

		String dto= asJsonString(toInsert);

		mock.perform(MockMvcRequestBuilders
				.post("/api/booking/customer/1/room/1")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(r));

		rRepo.refreshTest();
	}

	@Test
	void updateTest() throws Exception
	{
		rRepo.refreshTest();

		RoomBookingDTO toInsert= new RoomBookingDTO();
		toInsert.setCheckIn("28/11/2023/11/30/00");
		toInsert.setCheckOut("05/12/2023/11/30/00");
		toInsert.setGuests(2);
		toInsert.setNotes("blabla");
		toInsert.setConfirmed(true);
		toInsert.setCustomerId(1);
		toInsert.setRoomId(3);
		toInsert.setPrice(500.00);

		String r= "{\r\n"
				+ "    \"id\": 38,\r\n"
				+ "    \"checkIn\": \"2023-11-28T11:30\",\r\n"
				+ "    \"checkOut\": \"2023-12-05T11:30\",\r\n"
				+ "    \"notes\": \"blabla\",\r\n"
				+ "    \"guests\": 2,\r\n"
				+ "    \"confirmed\": true,\r\n"
				+ "    \"customerId\": 1,\r\n"
				+ "    \"roomId\": 3,\r\n"
				+ "    \"price\": 500.00\r\n"
				+ "}";

		String dto= asJsonString(toInsert);

		mock.perform(MockMvcRequestBuilders
				.put("/api/booking/38")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(r));

		rRepo.refreshTest();
	}

	@Test
	void deleteTest() throws Exception
	{
		rRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders
				.delete("/api/booking/37"))
				.andExpect(status().is(200));
		rRepo.refreshTest();
	}

	private static String asJsonString(Object obj)
	{
		try
	    {
	        return new ObjectMapper().writeValueAsString(obj);
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException(e);
	    }
	}

	String bookingAllJson = "[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 37,\r\n"
			+ "        \"checkIn\": \"2023-11-01T10:00\",\r\n"
			+ "        \"checkOut\": \"2023-11-10T16:00\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"guests\": 1,\r\n"
			+ "        \"confirmed\": true,\r\n"
			+ "        \"customerId\": 1,\r\n"
			+ "        \"roomId\": 1\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"id\": 38,\r\n"
			+ "        \"checkIn\": \"2023-11-28T10:00\",\r\n"
			+ "        \"checkOut\": \"2023-12-10T16:00\",\r\n"
			+ "        \"notes\": \"\",\r\n"
			+ "        \"guests\": 4,\r\n"
			+ "        \"confirmed\": true,\r\n"
			+ "        \"customerId\": 1,\r\n"
			+ "        \"roomId\": 3\r\n"
			+ "    }\r\n"
			+ "]";

	String booking1Json = "{\r\n"
			+ "    \"id\": 37,\r\n"
			+ "    \"checkIn\": \"2023-11-01T10:00\",\r\n"
			+ "    \"checkOut\": \"2023-11-10T16:00\",\r\n"
			+ "    \"notes\": \"\",\r\n"
			+ "    \"guests\": 1,\r\n"
			+ "    \"confirmed\": true,\r\n"
			+ "    \"customerId\": 1,\r\n"
			+ "    \"roomId\": 1\r\n"
			+ "}";
}
