package com.packages.manager.productreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import com.packages.manager.model.dto.ProductDTO;

public class ProductReader {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		Authenticator.setDefault (new Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication ("user", "pass".toCharArray());
		    }
		});
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static ProductDTO getProduct(String productEan) throws IOException, JSONException {
		JSONObject json = readJsonFromUrl("https://product-service.herokuapp.com/api/v1/products/"+productEan);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setEan(json.getString("id"));
		productDTO.setName(json.getString("name"));
		productDTO.setPrice(json.getBigDecimal("usdPrice"));
		return productDTO;
	}
}
