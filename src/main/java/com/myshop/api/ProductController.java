package com.myshop.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.myshop.domain.Product;
import com.myshop.repository.NotFoundException;
import com.myshop.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController 
{
	private ProductRepository repo;

	@Autowired
	public ProductController(ProductRepository repo) {
		this.repo = repo;
	}

	  @RequestMapping(method=RequestMethod.POST, consumes="application/json")
	  public ResponseEntity<Product> addProduct(@RequestBody Product p, UriComponentsBuilder ucb) {
		Product saved = repo.save(p);
	    
	    HttpHeaders headers = new HttpHeaders();
	    URI locationUri = ucb.path("/products/")
	        .path(String.valueOf(saved.getId()))
	        .build()
	        .toUri();
	    headers.setLocation(locationUri);
	    
	    ResponseEntity<Product> responseEntity = new ResponseEntity<Product>(headers, HttpStatus.CREATED);
	    return responseEntity;
	  }

	  @RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
	  public Product getProduct(@PathVariable Long id) {
			Product p = repo.findOne(id);
			if (p == null) {
				throw new NotFoundException(id);
			}
			return p;
	  }

	  @ExceptionHandler(NotFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public String productNotFound(NotFoundException e) {
	    return "Product with id " + e.getId() + " not found";
	  }
}
