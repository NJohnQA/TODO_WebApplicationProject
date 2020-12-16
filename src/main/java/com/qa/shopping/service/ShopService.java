package com.qa.shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.shopping.dto.ShopDto;
import com.qa.shopping.exceptions.ItemNotFoundException;
import com.qa.shopping.persistence.domain.Shop;
import com.qa.shopping.persistence.repo.ShopRepo;
import com.qa.shopping.util.SpringBeanUtil;

@Service
public class ShopService {
	// this is where our business logic will happen

//		this is also where CRUD logic will take place.

	// implements are crud functionality
	private ShopRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// create our mapToDto
	private ShopDto mapToDTO(Shop shop) {
		return this.mapper.map(shop, ShopDto.class);
	}

	@Autowired
	public ShopService(ShopRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public ShopDto create(Shop shop) {
		return this.mapToDTO(this.repo.save(shop));
	}

	// read all method
	public List<ShopDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public ShopDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(ItemNotFoundException::new));
	}

	// update
	public ShopDto update(ShopDto shopDto, Long id) {
		// check if record exists
		Shop toUpdate = this.repo.findById(id).orElseThrow(ItemNotFoundException::new);
		// set the record to update
		toUpdate.setStoreName(shopDto.getStoreName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(shopDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);// true
		return !this.repo.existsById(id);// true
	}

	// Find by name
	public List<ShopDto> findByName(String name) {
		return this.repo.findByName(name).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

}
