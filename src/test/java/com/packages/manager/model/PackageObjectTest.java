package com.packages.manager.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.packages.manager.model.dto.PackageObjectDTO;
import com.packages.manager.model.dto.ProductDTO;
import com.packages.manager.services.PackageService;
import com.packages.manager.services.ProductService;

public class PackageObjectTest {

	@Test
	public void testCreatePackage() {
		PackageObjectDTO packageDTO = new PackageObjectDTO();
		packageDTO.setName("PackageName");
		packageDTO.setDescription("my package description");
		packageDTO.setPrice(new BigDecimal("46"));
		PackageObject packageObject = PackageService.createPackageObject(packageDTO);
		assertTrue(packageObject.getName().equals(packageDTO.getName()));
		assertTrue(packageObject.getPrice().compareTo(packageDTO.getPrice()) == 0);
	}

	@Test
	public void testUpdatePackage() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setEan("BBCDEFGHIJKL");
		productDTO.setName("testCase");
		productDTO.setPrice(new BigDecimal("25.0"));
		Product product = ProductService.createProduct(productDTO);
		PackageObjectDTO packageDTO = new PackageObjectDTO();
		packageDTO.setName("PackageName2");
		packageDTO.setDescription("my package description 2");
		packageDTO.setPrice(productDTO.getPrice());
		PackageObject packageObject = PackageService.createPackageObject(packageDTO);
		PackageService.updatePackageObject(packageObject, product);
		assertTrue(packageObject.getProducts().get(0) == product);
	}

	@Test
	public void testDeletePackage() {
		PackageObjectDTO packageDTO = new PackageObjectDTO();
		packageDTO.setName("PackageName");
		packageDTO.setDescription("my package description");
		packageDTO.setPrice(new BigDecimal("102"));
		PackageObject packageObject = PackageService.createPackageObject(packageDTO);
		assertTrue(PackageService.deletePackage(packageObject.getId()));
	}

	@Test
	public void testGetAllPackages() {
		List<PackageObject> packages = PackageService.getAllPackages();
		assertFalse(packages.isEmpty());
		for (PackageObject packageObject : packages) {
			System.out.println(packageObject.toString());
			for (Product product : packageObject.getProducts()) {
				System.out.println("      Associated Product => " + product.toString());
			}
		}
	}

}
