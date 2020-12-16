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

import com.qa.shopping.dto.ItemDto;
import com.qa.shopping.persistence.domain.Item;
import com.qa.shopping.service.ItemService;

@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application porperties file.
public class ItemControllerUnitTest {
	@Autowired
	private ItemController controller;

	@MockBean
	private ItemService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private ItemDto maptoDto(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}

	// During our test we want give it some data to use
	private final Item TEST_ITEM_1 = new Item(1L, "PS4", "Console", 1);
	private final Item TEST_ITEM_2 = new Item(2L, "PSP", "Console", 1);
	private final Item TEST_ITEM_3 = new Item(3L, "Nintendo DS", "Console", 1);
	private final Item TEST_ITEM_4 = new Item(4L, "Sony SRS-XB30", "Speakers", 1);

	// I also want to create a list of cars that i can use later
	private final List<Item> LISTOFITEMS = List.of(TEST_ITEM_1, TEST_ITEM_2, TEST_ITEM_3, TEST_ITEM_4);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_ITEM_1)).thenReturn(this.maptoDto(TEST_ITEM_1));
		assertThat(new ResponseEntity<ItemDto>(this.maptoDto(TEST_ITEM_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_ITEM_1));
		verify(this.service, atLeastOnce()).create(TEST_ITEM_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_ITEM_1.getId())).thenReturn(this.maptoDto(TEST_ITEM_1));
		assertThat(new ResponseEntity<ItemDto>(this.maptoDto(TEST_ITEM_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_ITEM_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_ITEM_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<ItemDto> dtos = LISTOFITEMS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_ITEM_1), TEST_ITEM_1.getId())).thenReturn(this.maptoDto(TEST_ITEM_1));
		assertThat(new ResponseEntity<ItemDto>(this.maptoDto(TEST_ITEM_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_ITEM_1.getId(), this.maptoDto(TEST_ITEM_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_ITEM_1), TEST_ITEM_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_ITEM_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_ITEM_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(TEST_ITEM_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_ITEM_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_ITEM_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_ITEM_1.getId());

	}

	// Find by name
	@Test
	void findByName() throws Exception {
		List<ItemDto> dtos = LISTOFITEMS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.findByName(TEST_ITEM_1.getName())).thenReturn(dtos);
		assertThat(this.controller.findByName(TEST_ITEM_1.getName()))
				.isEqualTo(new ResponseEntity<List<ItemDto>>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).findByName(TEST_ITEM_1.getName());
	}

}
