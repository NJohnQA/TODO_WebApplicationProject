package com.qa.shopping.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.shopping.persistence.domain.Shop;

@Repository
public interface ShopRepo extends JpaRepository<Shop, Long> {

	// it allows us to implement
	// create
	// read
	// update
	// delete

	// custom sql statements e.g. find by make or model ....
}
