package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.PetTypeDTO;
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

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class TypesControllerTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindAllTypes() throws Exception {
		this.mockMvc.perform(get("/types"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id").exists());
	}

	@Test
	public void testFindTypeOK() throws Exception {
		String NAME = "cat";

		this.mockMvc.perform(get("/types/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(NAME)));
	}

	@Test
	public void testFindTypeKO() throws Exception {
		mockMvc.perform(get("/types/666"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreateType() throws Exception {
		String NAME = "Fish";

		PetTypeDTO newTypeTO = PetTypeDTO.builder()
				.name(NAME)
				.build();

		this.mockMvc.perform(post("/types")
						.content(om.writeValueAsString(newTypeTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(NAME)));
	}

	@Test
	public void testDeleteType() throws Exception {
		String NAME = "Reptile";

		PetTypeDTO newTypeTO = PetTypeDTO.builder()
				.name(NAME)
				.build();

		ResultActions mvcActions = mockMvc.perform(post("/types")
						.content(om.writeValueAsString(newTypeTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/types/" + id))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteTypeKO() throws Exception {
		mockMvc.perform(delete("/types/1000"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateType() throws Exception {
		String NAME = "Amphibian";
		String UP_NAME = "Mammal";

		PetTypeDTO newTypeTO = PetTypeDTO.builder()
				.name(NAME)
				.build();

		ResultActions mvcActions = mockMvc.perform(post("/types")
						.content(om.writeValueAsString(newTypeTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Integer id = JsonPath.parse(response).read("$.id");

		PetTypeDTO upTypeTO = PetTypeDTO.builder()
				.id(id)
				.name(UP_NAME)
				.build();

		mockMvc.perform(put("/types/" + id)
						.content(om.writeValueAsString(upTypeTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		mockMvc.perform(get("/types/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.name", is(UP_NAME)));

		mockMvc.perform(delete("/types/" + id))
				.andExpect(status().isOk());
	}

}

