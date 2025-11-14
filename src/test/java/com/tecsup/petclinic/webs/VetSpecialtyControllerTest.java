package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetSpecialtyControllerTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCreateVetSpecialty() throws Exception {
		int VET_ID = 1;
		int SPECIALTY_ID = 1;

		VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.build();

		this.mockMvc.perform(post("/vet-specialties")
						.content(om.writeValueAsString(vetSpecialtyDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	public void testCreateVetSpecialtyKO() throws Exception {
		int VET_ID = 999;
		int SPECIALTY_ID = 999;

		VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.build();

		this.mockMvc.perform(post("/vet-specialties")
						.content(om.writeValueAsString(vetSpecialtyDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteVetSpecialty() throws Exception {
		int VET_ID = 2;
		int SPECIALTY_ID = 1;

		VetSpecialtyDTO createDTO = VetSpecialtyDTO.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.build();

		this.mockMvc.perform(post("/vet-specialties")
						.content(om.writeValueAsString(createDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		VetSpecialtyDTO deleteDTO = VetSpecialtyDTO.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.build();

		this.mockMvc.perform(delete("/vet-specialties")
						.content(om.writeValueAsString(deleteDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteVetSpecialtyKO() throws Exception {
		int VET_ID = 999;
		int SPECIALTY_ID = 999;

		VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.build();

		this.mockMvc.perform(delete("/vet-specialties")
						.content(om.writeValueAsString(vetSpecialtyDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

}

