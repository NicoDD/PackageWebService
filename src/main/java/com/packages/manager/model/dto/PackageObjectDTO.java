package com.packages.manager.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageObjectDTO 
{
	private int id;
	
	private String name;
	
	private String description;
	
	private List<ProductDTO> products;
	
	private BigDecimal price;
}
