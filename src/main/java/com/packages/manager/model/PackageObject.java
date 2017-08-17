package com.packages.manager.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="package_object")
public class PackageObject 
{
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="package_product", 
			 joinColumns = {@JoinColumn(name="package_id")},
			 inverseJoinColumns = {@JoinColumn(name="product_id")})
	private List<Product> products = new ArrayList<Product>();
	
	@Column(name="price")
	private BigDecimal price;
	
	public String toString() {
		return "[id="+id+";name="+name+";description="+description+";price="+price.toString()+"]";
	}
}
