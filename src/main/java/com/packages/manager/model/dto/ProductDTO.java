package com.packages.manager.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	
	private String id;

	private String ean;
	
	private String name;

	private BigDecimal price;
	
	private List<ProductDTO> products;
}
