package com.hb.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.hb.exceptions.CustomerException;
import com.hb.models.Customer;
import com.hb.models.LoginForm;
import com.hb.repository.CustomerDao;



@RestController

public class LoginController {
	
	
	
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginRequest, 
                                         HttpServletResponse response,
                                         HttpServletRequest request) {
        try {
        	   System.out.println(loginRequest);
            org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
          
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Cookie sessionCookie = new Cookie("JSESSIONID", request.getSession().getId());
            response.addCookie(sessionCookie);
            
            return new ResponseEntity<Cookie>(sessionCookie,HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

	
	
	@Autowired
	private CustomerDao customerDao;
	@GetMapping("/signIn")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(Authentication auth) throws CustomerException{
    	
    	Customer customer = customerDao.findByMobileNumber(auth.name());
    	if(customer == null) {
    		throw new CustomerException("customer not found");
    	}
    	return new ResponseEntity<>(customer,HttpStatus.ACCEPTED);
    
    	
    }
	@GetMapping("/current-user")
    public ResponseEntity<Customer> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) throws CustomerException {
		
		if(userDetails == null) {
			throw new CustomerException("Please login First");
		}
		Customer customer = customerDao.findByMobileNumber(userDetails.getUsername());
        return new ResponseEntity<>(customer,HttpStatus.ACCEPTED);
    }

}
