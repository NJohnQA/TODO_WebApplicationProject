package com.qa.shopping.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


import com.qa.shopping.dto.ShopDto;
import com.qa.shopping.persistence.domain.Shop;
import com.qa.shopping.service.ShopService;

@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application porperties file.
public class ShopControllerUnitTesting {
	@Autowired
	private ShopController controller;

	@MockBean
	private ShopService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private ShopDto maptoDto(Shop shop) {
		return this.mapper.map(shop, ShopDto.class);
	}

	// During our test we want give it some data to use
	private final Shop TEST_SHOP_1 = new Shop(1L, "Console", "Argos");
	private final Shop TEST_SHOP_2 = new Shop(2L, "Food", "Asda");
	private final Shop TEST_SHOP_3 = new Shop(3L, "Beauty", "Aldi");;
	private final Shop TEST_SHOP_4 = new Shop(4L, "TV", "Tesco");

	// I also want to create a list of cars that i can use later
	private final List<Shop> LISTOFSHOPS = List.of(TEST_SHOP_1, TEST_SHOP_2, TEST_SHOP_3, TEST_SHOP_4);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_SHOP_1)).thenReturn(this.maptoDto(TEST_SHOP_1));
		assertThat(new ResponseEntity<ShopDto>(this.maptoDto(TEST_SHOP_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_SHOP_1));
		verify(this.service, atLeastOnce()).create(TEST_SHOP_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_SHOP_1.getId())).thenReturn(this.maptoDto(TEST_SHOP_1));
		assertThat(new ResponseEntity<ShopDto>(this.maptoDto(TEST_SHOP_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_SHOP_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_SHOP_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<ShopDto> dtos = LISTOFSHOPS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_SHOP_1), TEST_SHOP_1.getId())).thenReturn(this.maptoDto(TEST_SHOP_1));
		assertThat(new ResponseEntity<ShopDto>(this.maptoDto(TEST_SHOP_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_SHOP_1.getId(), this.maptoDto(TEST_SHOP_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_SHOP_1), TEST_SHOP_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_SHOP_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_SHOP_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(TEST_SHOP_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_SHOP_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_SHOP_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_SHOP_1.getId());

	}

	// Find by name
	@Test
	void findByName() throws Exception {
		List<ShopDto> dtos = LISTOFSHOPS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.findByName(TEST_SHOP_1.getStoreName())).thenReturn(dtos);
		assertThat(this.controller.findByName(TEST_SHOP_1.getStoreName()))
				.isEqualTo(new ResponseEntity<List<ShopDto>>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).findByName(TEST_SHOP_1.getStoreName());
	}

}
