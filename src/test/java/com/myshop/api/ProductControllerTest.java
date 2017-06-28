package com.myshop.api;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.myshop.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ProductControllerTest 
{
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldAddNewProduct() {
		Product p = new Product("iphone 7", "apple", 20.0f);

		ResponseEntity<Product> response =
			this.restTemplate.postForEntity("/products", p, Product.class);
		
		// Successful HTTP response: 201, “Created”
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		System.out.println("Location: " + response.getHeaders().getLocation());
	}

	@Test
	public void shouldGetExistingProduct() {
		ResponseEntity<String> response =
			this.restTemplate.getForEntity("/products/1", String.class);
		
		// Successful HTTP response: 200, “OK”
		assertEquals(HttpStatus.OK, response.getStatusCode());
		System.out.println("Response body: " + response.getBody());
	}

	@Test
	public void shouldGetAllExistingProducts() {
		ResponseEntity<String> response =
			this.restTemplate.getForEntity("/products", String.class);
		
		// Successful HTTP response: 200, “OK”
		assertEquals(HttpStatus.OK, response.getStatusCode());
		System.out.println("Response body: " + response.getBody());
	}

	@Test
	public void shouldUpdateExistingProduct() {
		// test for xml support
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		
		HttpEntity<Product> entity = new HttpEntity<Product>(headers);
		ResponseEntity<Product> response =
			this.restTemplate.exchange("/products/1", HttpMethod.GET, entity, Product.class);

		//update product details
		Product p = response.getBody();
		p.setName("mate 9");
		p.setDescription("huawei");
		p.setPrice(10.0f);
		
		entity = new HttpEntity<Product>(p, headers);
		this.restTemplate.exchange("/products/1", HttpMethod.PUT, entity, Product.class);

		entity = new HttpEntity<Product>(headers);
		ResponseEntity<String> responseString =
			this.restTemplate.exchange("/products/1", HttpMethod.GET, entity, String.class);

		System.out.println("Response body: " + responseString.getBody());
	}

	@Test
	public void shouldDeleteExistingProduct() {
		this.restTemplate.delete("/products/2");
		ResponseEntity<String> response =
			this.restTemplate.getForEntity("/products/2", String.class);
		// Error HTTP response: 404, “Not Found”
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}
