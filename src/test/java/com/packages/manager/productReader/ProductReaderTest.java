package com.packages.manager.productReader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.json.JSONException;
import org.junit.Test;

import com.packages.manager.model.dto.ProductDTO;
import com.packages.manager.productreader.ProductReader;

public class ProductReaderTest {

	@Test
	public void test() {
		ProductDTO productDTO = null;
		try {
			productDTO = ProductReader.getProduct("VqKb4tyj9V6i");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(productDTO);
		assertTrue("VqKb4tyj9V6i".equals(productDTO.getEan()));
		assertTrue("Shield".equals(productDTO.getName()));
		assertTrue(new BigDecimal("1149").compareTo(productDTO.getPrice()) == 0);
	}

}
