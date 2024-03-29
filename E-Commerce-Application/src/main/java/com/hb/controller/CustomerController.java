package com.hb.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hb.exceptions.AdminException;
import com.hb.exceptions.CartException;
import com.hb.exceptions.CustomerException;
import com.hb.exceptions.OrderException;
import com.hb.exceptions.ProductException;
import com.hb.models.AddressDto;
import com.hb.models.Cart;
import com.hb.models.Customer;
import com.hb.models.CustomerDTO;
import com.hb.models.Orders;
import com.hb.models.Product;
import com.hb.models.ProductDtoSec;
import com.hb.service.CartService;
import com.hb.service.CustomerService;
import com.hb.service.OrderService;
import com.hb.service.ProductService;
import com.hb.validations.CustomerValidation;

@RestController
@RequestMapping("/Customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {
	
    @Autowired
	CustomerService custService;
    
    @Autowired
	CartService cservice;
    
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomerValidation validator;
	
	

	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	binder.setValidator(validator);
    }
	
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO cDTO,Errors errors) throws CustomerException{
		if(errors.hasErrors()) {
			return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
		Customer cust = custService.createCustomer(cDTO);
		
		return new ResponseEntity<Customer>(cust,HttpStatus.OK);
		
		
	}
	@PutMapping("/update/{customerId}")
	public  ResponseEntity<?> updateCustomer(@PathVariable Integer customerId ,@Valid @RequestBody CustomerDTO customer,Errors errors) throws CustomerException {
		
		if(errors.hasErrors()) {
			return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
			
		Customer updatedCustomer= custService.updateCustomer(customer,customerId);
			
		return new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);
		
	}

	
    @GetMapping("/cart/{customerId}/{quantity}/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable Integer customerId,@PathVariable Integer quantity,@PathVariable Integer productId ) throws CartException, CustomerException{
	    	String mess = cservice.addProductToCart(customerId,quantity, productId);
	    	return new ResponseEntity<String>(mess, HttpStatus.OK);
	    }
	@GetMapping("/getAllProductAddedInCart/{customerId}")
	public ResponseEntity<List<ProductDtoSec>> getAllProductAddedToCart(@PathVariable Integer customerId) throws CartException, CustomerException{
	   List<ProductDtoSec> products = cservice.getAllProduct(customerId);
	   
	   return new ResponseEntity<List<ProductDtoSec>>(products,HttpStatus.OK);
	    	
	    }
	

	
	@DeleteMapping("/removeProductFromCart/{productId}/{customerId}")
	public ResponseEntity<String> removeProductFromCart(@PathVariable Integer productId,@PathVariable Integer customerId) throws CustomerException, CartException{
		String mess = cservice.removeProductfromCart(productId, customerId);
		return new ResponseEntity<String>(mess, HttpStatus.OK);
		
	}
	
	@GetMapping("/updatingQuantity/{productId}/{quantity}/{customerId}")
	public ResponseEntity<ProductDtoSec> updateQuantityOfProduct(@PathVariable Integer productId,@PathVariable Integer quantity,@PathVariable Integer customerId ) throws CustomerException,CartException{
		ProductDtoSec productdto = cservice.updateQuantity(productId, quantity, customerId);
		return new  ResponseEntity<ProductDtoSec>(productdto,HttpStatus.OK);
	}
	
	@DeleteMapping("/removeAllProductfromCart/{customerId}")
	public ResponseEntity<Cart> removeAllProduct(@PathVariable Integer customerId) throws CustomerException, CartException {
		Cart cart = cservice.removeAllProduct( customerId);
		return new  ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	@DeleteMapping("/cancelOrder/{orderId}")
	public ResponseEntity<String> CancelOrder(@PathVariable Integer orderId) throws OrderException, CustomerException{
		 
		String mess = orderService.cancelOrder(orderId);
		return new ResponseEntity<String>(mess,HttpStatus.OK);
		
	}


	@GetMapping("/getAllProduct") 
	public ResponseEntity<List<Product>> getAllProduct() throws ProductException{
		List<Product> p = productService.getAllProduct();
		return new ResponseEntity<List<Product>>(p,HttpStatus.OK);
	}
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws CustomerException{
		String str = custService.deleteCustomer(customerId);
		return new  ResponseEntity<String>(str,HttpStatus.OK);
	}
	
	@GetMapping("/getAllOrdersByCustomer/{customerId}")
	public ResponseEntity<List<Orders>> getAllOrders(@PathVariable Integer customerId) throws OrderException, CustomerException {
		List<Orders> orders = orderService.getAllOrdersByCustomer(customerId);
		return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
	}

	

}
