package com.qa.shopping.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.shopping.persistence.domain.Shop;

@Repository
public interface ShopRepo extends JpaRepository<Shop, Long> {

	@Query(value = "SELECT * FROM SHOP WHERE NAME =?1", nativeQuery = true)
	List<Shop> findByName(String name);

	// it allows us to implement
	// create
	// read
	// update
	// delete

	// custom sql statements e.g. find by make or model ....
}
