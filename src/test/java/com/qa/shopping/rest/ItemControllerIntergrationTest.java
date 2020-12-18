package com.qa.shopping.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.shopping.dto.ItemDto;
import com.qa.shopping.persistence.domain.Item;

@SpringBootTest
@AutoConfigureMockMvc
// sql runs in order schema followed by data file - JH dont make the mistake
@Sql(scripts = { "classpath:schema.sql",
		"classpath:item-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class ItemControllerIntergrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private ItemDto mapToDTO(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}

	// During our test we want give it some data to use
	private final Item TEST_ITEM_1 = new Item(1L, "PS4", "Console", 1);
	private final Item TEST_ITEM_2 = new Item(2L, "PSP", "Console", 1);
	private final Item TEST_ITEM_3 = new Item(3L, "Nintendo DS", "Console", 1);
	private final Item TEST_ITEM_4 = new Item(4L, "Sony SRS-XB30", "Speakers", 1);
	
	// I also want to create a list of cars that i can use later
	private final List<Item> LISTOFITEMS = List.of(TEST_ITEM_1, TEST_ITEM_2, TEST_ITEM_3, TEST_ITEM_4);

		private final String URI = "/item";

		// Create test
		@Test
		void createTest() throws Exception {
			ItemDto testDTO = mapToDTO(new Item("phone", "iPhone 11", 1));
			String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

			RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

			ResultMatcher checkStatus = status().isCreated();

			ItemDto testSavedDTO = mapToDTO(new Item("phone", "iPhone 11", 1));
			testSavedDTO.setId(5L);
			String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

			ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);


		}
		
		// read all
		@Test
		void readAllTest() throws Exception {
			
			RequestBuilder request = get(URI + "/readall").contentType(MediaType.APPLICATION_JSON);
			ResultMatcher checkStatus = status().isOk();
			
			String testSavedDtoAsJSON = this.jsonifier.writeValueAsString(List.of(LISTOFITEMS));
			ResultMatcher checkBody = content().json(testSavedDtoAsJSON);
			
			
			this.mvc.perform(request).andExpect(checkStatus);	
		}
		
		// read one
		@Test
		void readOneTest() throws Exception {
			
			Long id = 1L;
			
			RequestBuilder request = get(URI + "/read/" + id).contentType(MediaType.APPLICATION_JSON);
			ResultMatcher checkStatus = status().isOk();
			
			ItemDto testReadOneDTO = mapToDTO(TEST_ITEM_1);
			String testReadOneDTOAsJSON = this.jsonifier.writeValueAsString(testReadOneDTO);
			
			ResultMatcher checkBody = content().json(testReadOneDTOAsJSON);
			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
			
		}
		
		// (partial) update
		@Test
		void UpdateTest() throws Exception {
			
			Long id = 1L;
			
			Item update = new Item("PS", "Console", 1);
			
			ItemDto testDTO = mapToDTO(update);
			String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

			RequestBuilder request = put(URI + "/update/" + id).contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
			ResultMatcher checkStatus = status().isAccepted();
			
			Item save = new Item("PS", "Console", 1);
			
			ItemDto testSavedDTO = mapToDTO(save);
			testSavedDTO.setId(id);
			String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
			
			ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
			
		}
		
		// delete
		@Test
		void deleteTest() throws Exception {
			
			Long id = 4L;
			
			RequestBuilder request = delete(URI + "/delete/" + id).contentType(MediaType.APPLICATION_JSON);
			ResultMatcher checkStatus = status().isNoContent();
			
			this.mvc.perform(request).andExpect(checkStatus);
			
		}
		
		

}
