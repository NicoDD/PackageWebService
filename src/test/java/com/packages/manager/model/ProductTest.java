package com.packages.manager.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.packages.manager.model.dto.ProductDTO;
import com.packages.manager.services.ProductService;

public class ProductTest {

	@Test
	public void testCreateProduct() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setEan("ABCDEFGHIJKL");
		productDTO.setName("testCase");
		productDTO.setPrice(new BigDecimal("25.0"));
		Product product = ProductService.createProduct(productDTO);
		assertNotNull(product);
		assertTrue(product.getEan().equals(productDTO.getEan()));
		assertTrue(product.getName().equals(productDTO.getName()));
		assertTrue(product.getPrice().compareTo(productDTO.getPrice()) == 0);
	}
	
	@Test
	public void testUpdateProduct() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setEan("ABCDEFGHIJKL");
		productDTO.setName("testCaseUpdated");
		productDTO.setPrice(new BigDecimal("255.0"));
		Product product = ProductService.updateProduct(productDTO, null);
		assertNotNull(product);
		assertTrue(product.getEan().equals(productDTO.getEan()));
		assertTrue(product.getName().equals(productDTO.getName()));
		assertTrue(product.getPrice().compareTo(productDTO.getPrice()) == 0);
	}

}
