package com.packages.manager.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="product")
public class Product {
	
	@Id @GeneratedValue
	@Column(name="id")
	private String id;

	@Column(name="ean")
	private String ean;
	
	@Column(name="name")
	private String name;

	@Column(name="usdprice")
	private BigDecimal price;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="package_product", 
			 joinColumns = {@JoinColumn(name="product_id")},
			 inverseJoinColumns = {@JoinColumn(name="package_id")})
	private List<PackageObject> packages;
}
