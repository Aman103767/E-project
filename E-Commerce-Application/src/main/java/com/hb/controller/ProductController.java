package com.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.exceptions.ProductException;
import com.hb.models.Product;
import com.hb.models.ProductDTO;
import com.hb.service.AdminService;
import com.hb.service.CustomerService;
import com.hb.service.OrderService;
import com.hb.service.ProductService;

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
	
	@PostMapping("/create")
	public ResponseEntity<Product> createProduct( @RequestBody ProductDTO product ) throws ProductException {
		Product p = pService.createProduct(product);
		//if(errors.hasErrors()) {
		//	return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		//}
		return new ResponseEntity<Product>(p,HttpStatus.OK);
	}
	
}
