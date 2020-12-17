package com.qa.shopping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
	private Long id;
	private String itemName;
	private String category;
	private int quantity;
	

	// this will spit out JSON
	
}
