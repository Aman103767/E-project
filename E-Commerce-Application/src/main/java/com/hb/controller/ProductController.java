package com.hb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.exceptions.ProductException;
import com.hb.models.Product;
import com.hb.models.ProductDTO;
import com.hb.models.ProductPage;
import com.hb.models.ProductSearchCritaria;
import com.hb.service.AdminService;
import com.hb.service.CustomerService;
import com.hb.service.OrderService;
import com.hb.service.ProductService;
import com.hb.service.ProductServiceImpl;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*")
public class ProductController {

	
	@Autowired
	private AdminService AService;
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
    private CustomerService custService;
	
	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@PostMapping("/create")
	public ResponseEntity<Product> createProduct( @RequestBody ProductDTO product ) throws ProductException {
		Product p = pService.createProduct(product);
		//if(errors.hasErrors()) {
		//	return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		//}
		return new ResponseEntity<Product>(p,HttpStatus.OK);
	}
	@PutMapping("/updateProduct/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer productId,
			@Valid @RequestBody ProductDTO product,Errors errors ) throws ProductException {
		Product p = pService.updateProduct(product,productId);
		if(errors.hasErrors()) {
			return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Product>(p,HttpStatus.OK);
	}
	@GetMapping("/pagination/{pageNumber}/{pageSize}/{name}/{sortBy}/{direction}")
	public ResponseEntity<Page<Product>> getProduct(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,
			                                      @PathVariable String name, @PathVariable String sortBy,@PathVariable Boolean direction){
		  
		ProductPage productPage = new ProductPage();
		ProductSearchCritaria productSearchCritaria =  new ProductSearchCritaria();
	    productPage.setPageNumber(pageNumber);
	    productPage.setPageSize(pageSize);
	    if(direction == true) {
	    	productPage.setSortDirection(Sort.Direction.DESC);
	    }
	    
	    productPage.setSortBy(sortBy);
	    productSearchCritaria.getProduct().setProductName(name);
	    productSearchCritaria.getProduct().getCategory().setCategoryName(name);
	    productSearchCritaria.getProduct().setDimension(name);
	    productSearchCritaria.getProduct().setManufacturer(name);
	    productSearchCritaria.getProduct().setSpecification(name);
	   

	    
		return new ResponseEntity<>(productServiceImpl.getProduct(productPage, productSearchCritaria),HttpStatus.OK);
	}
}
