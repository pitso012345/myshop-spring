package com.myshop.api;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
//add test data from script @Sql
//@FixMethodOrder
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
	public void shouldUpdateExistingProduct() {
		Product p = this.restTemplate.getForObject("/products/1", Product.class);
		//update product details
		p.setName("mate 9");
		p.setDescription("huawei");
		p.setPrice(10.0f);
		this.restTemplate.put("/products/1", p);

		String body = this.restTemplate.getForObject("/products/1", String.class);
		System.out.println("Response body: " + body);
	}

	@Test
	public void shouldDeleteExistingProduct() {
		this.restTemplate.delete("/products/1");
		ResponseEntity<String> response =
				this.restTemplate.getForEntity("/products/1", String.class);
		// Error HTTP response: 404, “Not Found”
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}
