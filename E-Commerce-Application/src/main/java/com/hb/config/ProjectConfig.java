package com.hb.config;

import java.util.Collections; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.hb.service.CustomerDetailsService;

@Configuration
public class ProjectConfig {

	 
	 @Autowired
	    private CustomerDetailsService userDetailsService;
	 

	 
	@Bean
	public SecurityFilterChain hostBooksSecurityConfig(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.authorizeHttpRequests((auth) -> auth
				.antMatchers("/Customer/update/{customerId}",
						"/Customer/cart/{customerId}/{quantity}/{productId}",
						"/Customer/getAllProductAddedInCart/{customerId}",
						"/Customer/removeProductFromCart/{productId}/{customerId}",
						"/Customer/updatingQuantity/{productId}/{quantity}/{customerId}",
						"/Customer/removeAllProductfromCart/{customerId}",
						"/Customer/cancelOrder/{orderId}",
						"/Customer/getAllProduct",
						"/Customer/delete/{customerId}",
						 "/Customer/getAllOrdersByCustomer/{customerId}",
						 "/admin/viewAll",
						 "/admin/viewById/{customerId}",
						 "/admin/removeProduct/{productId}",
						 "/admin/getProductById/{productId}",
						 "/admin/getAllOrders",
						 "/admin/getAllOrdersInLastMonth",
						 "/admin/getSortedOrdersByAnyField/{field}",
						 "/order/orderProduct/{customerId}",
						 "/order/getOrderById/{orderId}",
						 "/product/create",
						 "/product/updateProduct/{productId}","/current-user"
						).hasAuthority("USER")
				.antMatchers("/Customer/create","/product/pagination","/login","/logout").permitAll()
			
				).csrf().disable().httpBasic();
		
		return http.build();
	}
	
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	  @Bean
	    public AuthenticationManager authenticationManager() throws Exception {
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(userDetailsService);
	        provider.setPasswordEncoder(passwordEncoder());
	        return new ProviderManager(Collections.singletonList(provider));
	    }
	  
//	  @Bean 
//	  public  HttpSessionIdResolver httpSessionIdResolver() {
//		// TODO Auto-generated method stub
//		  return new CookieHttpSessionIdResolver();
//
//	}
}
