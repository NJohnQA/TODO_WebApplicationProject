package com.qa.shopping.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.shopping.persistence.domain.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
	// JpaRepository<Books, Long> {

	// it allows us to implement
	// create
	// read
	// update
	// delete

	// custom sql statements e.g. find by make or model .........

	// find all by name
	// JPQL
	@Query(value = "SELECT * FROM ITEM WHERE NAME =?1", nativeQuery = true)
	List<Item> findByName(String name);
}
