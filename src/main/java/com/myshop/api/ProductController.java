package com.myshop.api;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.myshop.domain.Product;
import com.myshop.domain.Products;
import com.myshop.repository.ProductRepository;

@RestController
@Transactional
@RequestMapping("/products")
public class ProductController 
{
	private ProductRepository repo;

	@Autowired
	public ProductController(ProductRepository repo) {
		this.repo = repo;
	}

	@RequestMapping(method=RequestMethod.POST,
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Product> addProduct(@RequestBody Product p, UriComponentsBuilder ucb) {
		Product saved = repo.save(p);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/products/")
				.path(String.valueOf(saved.getId()))
				.build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<Product> responseEntity = new ResponseEntity<Product>(headers, HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(value="{id}", method=RequestMethod.GET,
			produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Product getProduct(@PathVariable Long id) {
		Product p = repo.findOne(id);
		if (p == null) {
			throw new NotFoundException();
		}
		return p;
	}

	@RequestMapping(method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public List<Product> getProducts() {
		List<Product> list = repo.findAll();
		if (list == null || list.size() == 0) {
			list = null;
		}
		return list;
	}
  
	@RequestMapping(method=RequestMethod.GET, produces={MediaType.APPLICATION_XML_VALUE})
	public Products getProductsXml() {
		List<Product> list = getProducts();
		return (list == null) ? null : new Products(list);
	}

	@RequestMapping(value="{id}", method=RequestMethod.PUT,
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void updateProduct(@PathVariable Long id, @RequestBody Product p) {
		if (id != p.getId()) {
			throw new BadRequestException();
		}
		if (!repo.exists(id)) {
			throw new NotFoundException();
		}
		repo.save(p);
	}

	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public void deleteProduct(@PathVariable Long id) {
		if (!repo.exists(id)) {
			throw new NotFoundException();
		}
		repo.delete(id);
	}
}
