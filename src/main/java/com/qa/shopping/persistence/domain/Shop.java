package com.qa.shopping.persistence.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classes that represent tables in our DB
@Data
@NoArgsConstructor
public class Shop {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String storeName;
	
	@NotNull
	private String category;
	
	@OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Item> item;

	public Shop(Long id,  String storeName, String itemType) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.category = itemType;
	}

	public Shop(String storeName, String itemType) {
		super();
		this.storeName = storeName;
		this.category = itemType;
	}
	
	

}
