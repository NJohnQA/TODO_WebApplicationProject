package com.qa.shopping.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.shopping.dto.ShopDto;
import com.qa.shopping.persistence.domain.Item;
import com.qa.shopping.persistence.domain.Shop;


@SpringBootTest
@AutoConfigureMockMvc
// sql runs in order schema followed by data file - JH dont make the mistake
@Sql(scripts = { "classpath:schema.sql",
		"classpath:shop-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class ShopControllerIntergrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private ShopDto mapToDTO(Shop shop) {
		return this.mapper.map(shop, ShopDto.class);
	}

	// During our test we want give it some data to use
	private final Shop TEST_SHOP_1 = new Shop(1L, "Console", "Argos");
	private final Shop TEST_SHOP_2 = new Shop(2L, "Food", "Asda");
	private final Shop TEST_SHOP_3 = new Shop(3L, "Beauty", "Aldi");;
	private final Shop TEST_SHOP_4 = new Shop(4L, "TV", "Tesco");

	// I also want to create a list of cars that i can use later
	private final List<Shop> LISTOFSHOPS = List.of(TEST_SHOP_1, TEST_SHOP_2, TEST_SHOP_3, TEST_SHOP_4);


	private final String URI = "/shop";

	// Create test
	@Test
	void createTest() throws Exception {
		ShopDto testDTO = mapToDTO(new Shop("Stationary", "WHSmith"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isCreated();

		ShopDto testSavedDTO = mapToDTO(new Shop("Stationary", "WHSmith"));
		testSavedDTO.setId(5L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}
	
	@Test
	void readAllTest() throws Exception {
	
		RequestBuilder request = get(URI + "/readall").contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		
		String testSavedDtoAsJSON = this.jsonifier.writeValueAsString(List.of(LISTOFSHOPS));
		ResultMatcher checkBody = content().json(testSavedDtoAsJSON);
		
		
		this.mvc.perform(request).andExpect(checkStatus);	

	}

	@Test
	void readOneTest() throws Exception {
		Long id = 1L;
		
		RequestBuilder request = get(URI + "/read/" + id).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		
		ShopDto testSavedDTO = mapToDTO(TEST_SHOP_1);
		String testSavedDtoAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
		
		ResultMatcher checkBody = content().json(testSavedDtoAsJSON);
	
	    this.mvc.perform(request).andExpect(checkStatus);

	

	}



	@Test
	void updateTest() throws Exception {
		Long id = 1L;
		
		Shop update = new Shop("Stationary", "Amazon");
		String testDTOAsJSON = this.jsonifier.writeValueAsString(update);
		RequestBuilder request = put(URI + "/update/"+id).contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();
		
		ShopDto testSavedDTO = mapToDTO(new Shop("Stationary", "Amazon"));
		testSavedDTO.setId(id);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		
		this.mvc.perform(request).andExpect(checkStatus);
		
		

		

//		ShopDto testSavedDTO = mapToDTO(new Shop(5L, "Stationary", "Amazon"));
//		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
//
//		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		
		//this.mvc.perform(request).andExpect(checkStatus).andReturn();

		//this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}

	@Test
	void deleteTest() throws Exception {
		Long id = 1L;

		RequestBuilder request = delete(URI+"/delete/"+id).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isNoContent();

		this.mvc.perform(request).andExpect(checkStatus);//error

	}

}
