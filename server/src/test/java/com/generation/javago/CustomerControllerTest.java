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
import com.generation.javago.model.dto.customer.CustomerDTOWrite;
import com.generation.javago.model.repository.CustomerRepository;


@SpringBootTest
(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = JavagoApplication.class
)

@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	MockMvc mock;

	@Autowired
	CustomerRepository cRepo;


	@Test
	void getAllTest() throws Exception {
		cRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders.get("/api/customer"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(customerJson));
	}


	@Test
	void getOneTest() throws Exception {
		cRepo.refreshTest();

		mock.perform(MockMvcRequestBuilders.get("/api/customer/1"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(customerJson1));

		mock.perform(MockMvcRequestBuilders.get("/api/customer/10"))
		.andExpect(status().is(404));

		mock.perform(MockMvcRequestBuilders.get("/api/customer/paperino"))
		.andExpect(status().is(400));
	}


	@Test
	void insertTest() throws Exception {
		cRepo.refreshTest();
		CustomerDTOWrite toInsert= new CustomerDTOWrite();
		toInsert.setEmail("pippo@email.com");
		toInsert.setPassword("456");

		String customerToInsert= "{\r\n"
				+ "    \"id\": 2,\r\n"
				+ "    \"email\": \"pippo@email.com\"\r\n"
				+ "}";

		String dto= asJsonString(toInsert);

		mock.perform(MockMvcRequestBuilders
				.post("/api/customer")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(customerToInsert));
		cRepo.refreshTest();
	}


	@Test
	void updateTest() throws Exception {
		CustomerDTOWrite toInsert= new CustomerDTOWrite();
		toInsert.setEmail("Marco@email.com");
		toInsert.setPassword("12345");

		String dto= asJsonString(toInsert);

		mock.perform(MockMvcRequestBuilders
				.put("/api/customer/1")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(customerJson2));
		cRepo.refreshTest();
	}


	@Test
	void deleteTest() throws Exception {
		mock.perform(MockMvcRequestBuilders
				.delete("/api/customer/1"))
				.andExpect(status().is(200));
		cRepo.refreshTest();
	}


	String customerJson=
			"[\r\n"
			+ "    {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"email\": \"Marco@email.com\",\r\n"
			+ "        \"bookings\": [\r\n"
			+ "            {\r\n"
			+ "                \"id\": 37,\r\n"
			+ "                \"checkIn\": \"2023-11-01T10:00\",\r\n"
			+ "                \"checkOut\": \"2023-11-10T16:00\",\r\n"
			+ "                \"notes\": \"\",\r\n"
			+ "                \"guests\": 1,\r\n"
			+ "                \"confirmed\": true,\r\n"
			+ "                \"customerId\": 1,\r\n"
			+ "                \"roomId\": 1\r\n"
			+ "            },\r\n"
			+ "            {\r\n"
			+ "                \"id\": 38,\r\n"
			+ "                \"checkIn\": \"2023-11-28T10:00\",\r\n"
			+ "                \"checkOut\": \"2023-12-10T16:00\",\r\n"
			+ "                \"notes\": \"\",\r\n"
			+ "                \"guests\": 4,\r\n"
			+ "                \"confirmed\": true,\r\n"
			+ "                \"customerId\": 1,\r\n"
			+ "                \"roomId\": 3\r\n"
			+ "            }\r\n"
			+ "        ]\r\n"
			+ "    }\r\n"
			+ "]";


	String customerJson1= "{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"email\": \"Marco@email.com\",\r\n"
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
			+ "        },\r\n"
			+ "        {\r\n"
			+ "            \"id\": 38,\r\n"
			+ "            \"checkIn\": \"2023-11-28T10:00\",\r\n"
			+ "            \"checkOut\": \"2023-12-10T16:00\",\r\n"
			+ "            \"notes\": \"\",\r\n"
			+ "            \"guests\": 4,\r\n"
			+ "            \"confirmed\": true,\r\n"
			+ "            \"customerId\": 1,\r\n"
			+ "            \"roomId\": 3\r\n"
			+ "        }\r\n"
			+ "    ]\r\n"
			+ "}";

	String customerJson2= "{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"email\": \"Marco@email.com\"}";

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
