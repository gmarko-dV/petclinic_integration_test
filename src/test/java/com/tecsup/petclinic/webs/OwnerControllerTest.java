package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class OwnerControllerTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindAllOwners() throws Exception {

		final int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}

	/**
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testFindOwnerOK() throws Exception {

		String FIRST_NAME = "George";
		String LAST_NAME = "Franklin";
		String ADDRESS = "110 W. Liberty St.";
		String CITY = "Madison";
		String TELEPHONE = "6085551023";

		this.mockMvc.perform(get("/owners/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
				.andExpect(jsonPath("$.lastName", is(LAST_NAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)))
				.andExpect(jsonPath("$.city", is(CITY)))
				.andExpect(jsonPath("$.telephone", is(TELEPHONE)));
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindOwnerKO() throws Exception {

		mockMvc.perform(get("/owners/666"))
				.andExpect(status().isNotFound());

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testCreateOwner() throws Exception {

		String FIRST_NAME = "John";
		String LAST_NAME = "Doe";
		String ADDRESS = "123 Main St.";
		String CITY = "Springfield";
		String TELEPHONE = "5551234567";

		OwnerDTO newOwnerTO = OwnerDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address(ADDRESS)
				.city(CITY)
				.telephone(TELEPHONE)
				.build();

		this.mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
				.andExpect(jsonPath("$.lastName", is(LAST_NAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)))
				.andExpect(jsonPath("$.city", is(CITY)))
				.andExpect(jsonPath("$.telephone", is(TELEPHONE)));

	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteOwner() throws Exception {

		String FIRST_NAME = "Jane";
		String LAST_NAME = "Smith";
		String ADDRESS = "456 Oak Ave.";
		String CITY = "Madison";
		String TELEPHONE = "5559876543";

		OwnerDTO newOwnerTO = OwnerDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address(ADDRESS)
				.city(CITY)
				.telephone(TELEPHONE)
				.build();

		ResultActions mvcActions = mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer idInt = JsonPath.parse(response).read("$.id");
		Long id = idInt.longValue();

		mockMvc.perform(delete("/owners/" + id))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteOwnerKO() throws Exception {

		mockMvc.perform(delete("/owners/" + "1000"))
				.andExpect(status().isNotFound());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testUpdateOwner() throws Exception {

		String FIRST_NAME = "Robert";
		String LAST_NAME = "Johnson";
		String ADDRESS = "789 Pine St.";
		String CITY = "Monona";
		String TELEPHONE = "5551112233";

		String UP_FIRST_NAME = "Robert";
		String UP_LAST_NAME = "Williams";
		String UP_ADDRESS = "321 Elm St.";
		String UP_CITY = "Sun Prairie";
		String UP_TELEPHONE = "5554445566";

		OwnerDTO newOwnerTO = OwnerDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address(ADDRESS)
				.city(CITY)
				.telephone(TELEPHONE)
				.build();

		// CREATE
		ResultActions mvcActions = mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Integer idInt = JsonPath.parse(response).read("$.id");
		Long id = idInt.longValue();

		// UPDATE

		OwnerDTO upOwnerTO = OwnerDTO.builder()
				.id(id)
				.firstName(UP_FIRST_NAME)
				.lastName(UP_LAST_NAME)
				.address(UP_ADDRESS)
				.city(UP_CITY)
				.telephone(UP_TELEPHONE)
				.build();

		mockMvc.perform(put("/owners/" + id)
						.content(om.writeValueAsString(upOwnerTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// FIND
		mockMvc.perform(get("/owners/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(jsonPath("$.firstName", is(UP_FIRST_NAME)))
				.andExpect(jsonPath("$.lastName", is(UP_LAST_NAME)))
				.andExpect(jsonPath("$.address", is(UP_ADDRESS)))
				.andExpect(jsonPath("$.city", is(UP_CITY)))
				.andExpect(jsonPath("$.telephone", is(UP_TELEPHONE)));

		// DELETE
		mockMvc.perform(delete("/owners/" + id))
				.andExpect(status().isOk());
	}

}

