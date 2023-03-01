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
@CrossOrigin(origins = "*")
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
		
		Customer cust = custService.createCustomer(cDTO);
		if(errors.hasErrors()) {
			return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(cust,HttpStatus.OK);
		
		
	}
	@PutMapping("/update")
	public  ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDTO customer,Errors errors) throws CustomerException {
		
	
		Customer updatedCustomer= custService.updateCustomer(customer);
		if(errors.hasErrors()) {
			return new ResponseEntity<>(errors.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);
		
	}
	
    @GetMapping("/cart/{customerId}/{quantity}/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable Integer customerId,@PathVariable Integer quantity,@PathVariable Integer productId ) throws CartException, CustomerException{
	    	String mess = cservice.addProductToCart(customerId,quantity, productId);
	    	return new ResponseEntity<String>(mess, HttpStatus.OK);
	    }
	@GetMapping("/getAllProductAddedInCart/{customerId}")
	public ResponseEntity<List<ProductDtoSec>> getAllProductAddedToCart(@PathVariable Integer CustomerId) throws CartException, CustomerException{
	   List<ProductDtoSec> products = cservice.getAllProduct(CustomerId);
	   
	   return new ResponseEntity<List<ProductDtoSec>>(products,HttpStatus.OK);
	    	
	    }
	

	@PostMapping("/orderProduct/{customerId}")
	public ResponseEntity<Orders> Order(@PathVariable Integer CustomerId,@RequestBody AddressDto addDto) throws OrderException, CustomerException{
		
		Orders order = orderService.OrderProducts(CustomerId,addDto);
		
		return new  ResponseEntity<Orders>(order,HttpStatus.OK);
		
		
	}
	@DeleteMapping("/removeProductFromCart/{productId}/{customerId}")
	public ResponseEntity<String> removeProductFromCart(@PathVariable Integer productId,@PathVariable Integer CustomerId) throws CustomerException, CartException{
		String mess = cservice.removeProductfromCart(productId, CustomerId);
		return new ResponseEntity<String>(mess, HttpStatus.OK);
		
	}
	
	@GetMapping("/updatingQuantity/{productId}/{quantity}/{customerId}")
	public ResponseEntity<ProductDtoSec> updateQuantityOfProduct(@PathVariable Integer productId,@PathVariable Integer quantity,@PathVariable Integer CustomerId ) throws CustomerException,CartException{
		ProductDtoSec productdto = cservice.updateQuantity(productId, quantity, CustomerId);
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
	@GetMapping("/getOrderById/{orderId}")
	public ResponseEntity<Orders> getOrderByid(@PathVariable Integer orderId) throws OrderException, CustomerException {
		Orders order = orderService.getOrderById(orderId);
		return new ResponseEntity<Orders>(order,HttpStatus.OK);
	}
	@GetMapping("/getSortedProductByAnyFieldAsc/{field}")
	public ResponseEntity<List<Product>> getSortedProductByAnyField(@PathVariable String field) throws OrderException, CustomerException, ProductException {
		List<Product> products = productService.sortProductAsc(field);
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	@GetMapping("/productPagination/{offset}/{pageSize}")
	public ResponseEntity<Page<Product>> productPagination(@PathVariable Integer offset, @PathVariable Integer pageSize){
		 Page<Product> products = productService.findProductWithPagination(offset, pageSize);
		 return new ResponseEntity<Page<Product>>(products,HttpStatus.OK);
	}
	
	@GetMapping("/searchAllByProductByName/{name}")
	public ResponseEntity<List<Product>> searchAllByProducts(@PathVariable String name) throws ProductException{
		List<Product> products =  productService.searchProductByName(name);
		return new ResponseEntity<>(products,HttpStatus.OK);
		
	}
	@GetMapping("/getAllProduct") 
	public ResponseEntity<List<Product>> getAllProduct() throws ProductException{
		List<Product> p = productService.getAllProduct();
		return new ResponseEntity<List<Product>>(p,HttpStatus.OK);
	}

	

}
