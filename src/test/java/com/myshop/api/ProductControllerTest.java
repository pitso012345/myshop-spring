package com.myshop.api;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
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

}
