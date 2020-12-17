package com.qa.shopping.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.shopping.dto.ShopDto;
import com.qa.shopping.persistence.domain.Shop;
import com.qa.shopping.service.ShopService;

@RestController
@CrossOrigin
@RequestMapping("/shop") // this is to further define the path
@Profile({"dev","prod"})
public class ShopController {

	private ShopService service;

	@Autowired
	public ShopController(ShopService service) {
		super();
		this.service = service;
	}

	// Create method
	@PostMapping("/create")
	public ResponseEntity<ShopDto> create(@RequestBody Shop shop) {
		ShopDto created = this.service.create(shop);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
		// http status code - 201 (created)

	}

	// read all method
	@GetMapping("/readall")
	public ResponseEntity<List<ShopDto>> read() {
		return ResponseEntity.ok(this.service.readAll());
		// ok - 200
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<ShopDto> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<ShopDto> update(@PathVariable Long id, @RequestBody ShopDto shopDto) {
		return new ResponseEntity<>(this.service.update(shopDto, id), HttpStatus.ACCEPTED);
	}

	// Delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ShopDto> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				// no_content - if deleted successfully then should return nothing
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		// if the record isnt found!
	}

}
