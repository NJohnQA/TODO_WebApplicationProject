package com.qa.shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.qa.shopping.dto.ShopDto;
import com.qa.shopping.persistence.domain.Shop;
import com.qa.shopping.persistence.repo.ShopRepo;

@SpringBootTest
@ActiveProfiles("dev")
public class ShopServiceUnitTest {
	
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
		
		// create
		@Test
		void createTest() throws Exception {
			
			
			Shop NewShopId = new Shop(5L,"Console", "Argos");
			when(this.repo.save(NewShopId)).thenReturn(NewShopId);
			assertThat(this.service.create(NewShopId))
					.isEqualTo(this.mapToDTO(NewShopId));
			verify(this.repo, atLeastOnce()).save(NewShopId);
		}
		
		// read all
		@Test
		void readAllTest() throws Exception {
			when(this.repo.findAll()).thenReturn(LISTOFSHOPS);
			
			List<ShopDto> listOfTaskDTOs = LISTOFSHOPS.stream().map(this::mapToDTO)
					.collect(Collectors.toList());
			
			assertThat(this.service.readAll()).isEqualTo(listOfTaskDTOs);
			verify(this.repo, atLeastOnce()).findAll();
		}
		
		// read one
		@Test
		void readOneTest() throws Exception {
			Long id = 1L;
			when(this.repo.findById(id)).thenReturn(Optional.of(TEST_SHOP_1));
			assertThat(this.service.readOne(id))
					.isEqualTo(this.mapToDTO(TEST_SHOP_1));
			verify(this.repo, atLeastOnce()).findById(id);
		}
		
		//update
		@Test
		void partialUpdateNameTest() throws Exception {
			Long id = 1L;
			
			Shop update = new Shop("Stationary", "Amazon");
			ShopDto updatedDto = this.mapToDTO(update);
			
			when(this.repo.findById(id)).thenReturn(Optional.of(TEST_SHOP_1));
			when(this.repo.save(update)).thenReturn(update);
			
			assertThat(this.service.update(updatedDto, id))
					.isEqualTo(updatedDto);
			
			verify(this.repo, atLeastOnce()).findById(id);
			verify(this.repo, atLeastOnce()).save(update);
		}
		
		// delete success
		@Test
		void deleteSuccessTest() throws Exception {
			Long id = 1L;
			when(this.repo.existsById(id)).thenReturn(false);
			assertThat(this.service.delete(id)).isEqualTo(true);
			verify(this.repo, atLeastOnce()).existsById(id);
		}
		
		// delete failure
		@Test
		void deleteFailureTest() throws Exception {
			Long id = 1L;
			when(this.repo.existsById(id)).thenReturn(true);
			assertThat(this.service.delete(id)).isEqualTo(false);
			verify(this.repo, atLeastOnce()).existsById(id);
		}

}
