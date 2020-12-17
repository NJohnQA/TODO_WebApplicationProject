package com.qa.shopping.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.qa.shopping.dto.ShopDto;
import com.qa.shopping.persistence.domain.Shop;
import com.qa.shopping.persistence.repo.ShopRepo;
@SpringBootTest
@ActiveProfiles(profiles = "dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShopServiceIntegrationTesting {
	
	@Autowired
	private ShopService service;

	@Autowired
	private ShopRepo repo;

	@Autowired
	private ModelMapper mapper;
	

		// During our test we want give it some data to use
		private final Shop TEST_SHOP_1 = new Shop(1L, "Console", "Argos");
		private final Shop TEST_SHOP_2 = new Shop(2L, "Food", "Asda");
		private final Shop TEST_SHOP_3 = new Shop(3L, "Beauty", "Aldi");;
		private final Shop TEST_SHOP_4 = new Shop(4L, "TV", "Tesco");

		// I also want to create a list of cars that i can use later
		private final List<Shop> LISTOFSHOPS = List.of(TEST_SHOP_1, TEST_SHOP_2, TEST_SHOP_3, TEST_SHOP_4);
		
		private ShopDto mapToDTO(Shop shop) {
			return this.mapper.map(shop, ShopDto.class);
		}
		
		private Shop mapToPOJO(ShopDto shopDto) {
			return this.mapper.map(shopDto, Shop.class);
		}
		
		@BeforeEach
		void setup() {
			this.repo.saveAll(LISTOFSHOPS);
		}

		// create
		@Test
		void createTest() throws Exception {
			assertThat(this.service.create(TEST_SHOP_1)).isEqualTo(this.mapToDTO(TEST_SHOP_1));
		}
		
		// read all
		@Test
		void readAllTest() throws Exception {
			assertThat(this.service.readAll().stream().map(this::mapToPOJO).collect(Collectors.toList()))
					.isEqualTo(LISTOFSHOPS);
		}
		
		// read one
		@Test
		void readOneTest() throws Exception {
			Long id = 1L;
			assertThat(this.service.readOne(id)).isEqualTo(this.mapToDTO(TEST_SHOP_1));
		}
		
		//update
		@Test
		void updateTest() throws Exception {
			Long id = 1L;
			Shop update = new Shop("Stationary", "Amazon");
			ShopDto updatedDto = this.mapToDTO(update);
			assertThat(this.service.update(updatedDto, id))
					.isEqualTo(updatedDto);
		}
		
		// delete
		@Test
		void deleteTest() throws Exception {
			Long id = 1L;
			assertThat(this.service.delete(id)).isEqualTo(true);
		}
}
