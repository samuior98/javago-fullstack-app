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
import com.generation.javago.model.dto.room.RoomDTO;
import com.generation.javago.model.repository.RoomRepository;

@SpringBootTest
(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = JavagoApplication.class
)

@AutoConfigureMockMvc
class RoomControllerTest {

	@Autowired
	MockMvc mock;

	@Autowired
	RoomRepository rRepo;


	@Test
	void getAllTest() throws Exception {
		rRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders.get("/api/room"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(roomJson));
	}


	@Test
	void getOneTest() throws Exception {
		rRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders.get("/api/room/1"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(jsonRoom1));

		mock.perform(MockMvcRequestBuilders.get("/api/room/10"))
		.andExpect(status().is(404));

		mock.perform(MockMvcRequestBuilders.get("/api/room/paperino"))
		.andExpect(status().is(400));
	}


	@Test
	void insertTest() throws Exception {
		rRepo.refreshTest();

		RoomDTO toInsert= new RoomDTO();
		toInsert.setName("D");
		toInsert.setCapacity(5);
		toInsert.setNotes("blabla");
		toInsert.setPricePerNight(20.0);

		String r= "{\r\n"
				+ "    \"name\": \"D\",\r\n"
				+ "    \"capacity\": 5,\r\n"
				+ "    \"pricePerNight\": 20.0,\r\n"
				+ "    \"notes\": \"blabla\",\r\n"
				+ "    \"id\": 4\r\n"
				+ "}";

		String dto= asJsonString(toInsert);

		mock.perform(MockMvcRequestBuilders
				.post("/api/room")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(r));

		rRepo.refreshTest();
	}


	@Test
	void updateTest() throws Exception {
		rRepo.refreshTest();

		RoomDTO toInsert= new RoomDTO();
		toInsert.setName("C");
		toInsert.setCapacity(2);
		toInsert.setNotes("bla");
		toInsert.setPricePerNight(20.0);
		toInsert.setId(3);

		String dto= asJsonString(toInsert);

		mock.perform(MockMvcRequestBuilders
				.put("/api/room/3")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(roomJson2));
		rRepo.refreshTest();
	}


	@Test
	void deleteTest() throws Exception {
		rRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders
				.delete("/api/room/3"))
				.andExpect(status().is(200));
		rRepo.refreshTest();
	}


	String roomJson= "[\r\n"
			+ "    {\r\n"
			+ "        \"name\": \"A\",\r\n"
			+ "        \"capacity\": 2,\r\n"
			+ "        \"pricePerNight\": 45.0,\r\n"
			+ "        \"notes\": \"vista Po\",\r\n"
			+ "        \"id\": 1\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"name\": \"B\",\r\n"
			+ "        \"capacity\": 2,\r\n"
			+ "        \"pricePerNight\": 50.0,\r\n"
			+ "        \"notes\": \"vista città\",\r\n"
			+ "        \"id\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"name\": \"C\",\r\n"
			+ "        \"capacity\": 5,\r\n"
			+ "        \"pricePerNight\": 70.0,\r\n"
			+ "        \"notes\": \"vista bidoni dell'umido\",\r\n"
			+ "        \"id\": 3\r\n"
			+ "    }\r\n"
			+ "]";

	String jsonRoom1= "{\r\n"
			+ "    \"name\": \"A\",\r\n"
			+ "    \"capacity\": 2,\r\n"
			+ "    \"pricePerNight\": 45.0,\r\n"
			+ "    \"notes\": \"vista Po\",\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"bookings\": [\r\n"
			+ "        {\r\n"
			+ "            \"id\": 37,\r\n"
			+ "            \"checkIn\": \"2023-11-01T10:00\",\r\n"
			+ "            \"checkOut\": \"2023-11-10T16:00\",\r\n"
			+ "            \"notes\": \"\",\r\n"
			+ "            \"guests\": 1,\r\n"
			+ "            \"confirmed\": true,\r\n"
			+ "            \"customerId\": 1,\r\n"
			+ "            \"roomId\": 1\r\n"
			+ "        }\r\n"
			+ "    ]\r\n"
			+ "}";

	String roomJson2= "{\r\n"
			+ "    \"name\": \"C\",\r\n"
			+ "    \"capacity\": 2,\r\n"
			+ "    \"pricePerNight\": 20.0,\r\n"
			+ "    \"notes\": \"bla\",\r\n"
			+ "    \"id\": 3\r\n"
			+ "}";

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




}
