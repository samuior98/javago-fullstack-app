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
import com.generation.javago.model.dto.admin.AdminDTOWrite;
import com.generation.javago.model.repository.AdminRepository;


@SpringBootTest
(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = JavagoApplication.class
)

@AutoConfigureMockMvc
class AdminControllerTest {

	@Autowired
	MockMvc mock;
	@Autowired
	AdminRepository aRepo;

	@Test
	void getAll() throws Exception
	{
		mock.perform(MockMvcRequestBuilders.get("/api/admin"))
			.andDo(print())
			.andExpect(status().is(200))
			.andExpect(MockMvcResultMatchers.content().json(adminJson));
	}

	@Test
	void getOne() throws Exception
	{
		mock.perform(MockMvcRequestBuilders.get("/api/admin/1"))
		.andDo(print())
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.content().json(adminJson1));

		mock.perform(MockMvcRequestBuilders.get("/api/admin/10"))
		.andExpect(status().is(404));

		mock.perform(MockMvcRequestBuilders.get("/api/admin/paperino"))
		.andExpect(status().is(400));
	}

	//mockRepo.refreshTest();


	@Test
	void insert() throws Exception
	{
		String adminJsonToInsert = "{\r\n"
				+ "    \"email\": \"Pippo@email.com\",\r\n"
				+ "    \"employement\": \"studente\",\r\n"
				+ "    \"salary\": 300.0,\r\n"
				+ "    \"id\" : 5\r\n"
				+ "}";
		AdminDTOWrite valid= new AdminDTOWrite();
		valid.setEmail("Pippo@email.com");
		valid.setPassword("Password#1");
		valid.setEmployement("studente");
		valid.setSalary(300.0);

		String dto= asJsonString(valid);

		mock.perform(MockMvcRequestBuilders
				.post("/api/admin")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(adminJsonToInsert));

		aRepo.refreshTest();
	}


	@Test
	void update() throws Exception
	{
		AdminDTOWrite adminProva= new AdminDTOWrite();
		adminProva.setEmail("Marco@email.com");
		adminProva.setPassword("sdrhse243");
		adminProva.setEmployement("studente");
		adminProva.setSalary(100.0);
		adminProva.setId(1);

		String dto= asJsonString(adminProva);

		mock.perform(MockMvcRequestBuilders
				.put("/api/admin/1")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(adminJsonProva));
		aRepo.refreshTest();
	}


	@Test
	void delete() throws Exception
	{
		mock.perform(MockMvcRequestBuilders
				.delete("/api/admin/1"))
				.andExpect(status().is(200));
		aRepo.refreshTest();
	}



	String adminJsonProva= "{\r\n"
			+ "    \"email\": \"Marco@email.com\",\r\n"
			+ "    \"employement\": \"studente\",\r\n"
			+ "    \"salary\": 100.0,\r\n"
			+ "    \"id\": 1\r\n"
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

	String adminJson = "[\r\n"
			+ "    {\r\n"
			+ "        \"email\": \"Marco@email.com\",\r\n"
			+ "        \"employement\": \"studente\",\r\n"
			+ "        \"salary\": 100.0,\r\n"
			+ "        \"id\": 1\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"email\": \"Filippo@email.com\",\r\n"
			+ "        \"employement\": \"studente\",\r\n"
			+ "        \"salary\": 100.0,\r\n"
			+ "        \"id\": 2\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"email\": \"Alessandro@email.com\",\r\n"
			+ "        \"employement\": \"studente\",\r\n"
			+ "        \"salary\": 100.0,\r\n"
			+ "        \"id\": 3\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "        \"email\": \"Samuele@email.com\",\r\n"
			+ "        \"employement\": \"studente\",\r\n"
			+ "        \"salary\": 100.0,\r\n"
			+ "        \"id\": 4\r\n"
			+ "    }\r\n"
			+ "]";

	String adminJson1 = "{\r\n"
			+ "    \"email\": \"Marco@email.com\",\r\n"
			+ "    \"employement\": \"studente\",\r\n"
			+ "    \"salary\": 100.0,\r\n"
			+ "    \"id\": 1\r\n"
			+ "}";



}
