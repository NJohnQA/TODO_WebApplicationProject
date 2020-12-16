package com.qa.shopping.dto;

import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;
//import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopDto {
	private Long id;
	private String storeName;
	private String category;

	
	private List<ItemDto> shop = new ArrayList<>();

}
