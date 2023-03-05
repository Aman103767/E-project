package com.hb.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.exceptions.CustomerException;
import com.hb.models.Address;

import com.hb.models.Customer;
import com.hb.models.CustomerDTO;
import com.hb.repository.CustomerDao;


@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDao custDao;


	@Override
	public Customer createCustomer(CustomerDTO customer) throws CustomerException {
		
       Customer existingCustomer= custDao.findByMobileNumber(customer.getMobileNumber());
		
		if(existingCustomer != null) 
			throw new CustomerException("Customer Already Registered with Mobile number");
			
		// TODO Auto-generated method stub
		Customer cust = new Customer();
		cust.setUsername(customer.getUsername());
		cust.setEmail(customer.getEmail());
		cust.setMobileNumber(customer.getMobileNumber());
		cust.setPassword(customer.getPassword());
		custDao.save(cust);
		return cust;
		
		
	}
	
	@Override
	public Customer updateCustomer(CustomerDTO customer,Integer customerId) throws CustomerException{
	
	
			
			Customer cust = custDao.findById(customerId).get();
			cust.setUsername(customer.getUsername());
			cust.setEmail(customer.getEmail());
			cust.setMobileNumber(customer.getMobileNumber());
			cust.setPassword(customer.getPassword());
			//If LoggedInUser id is same as the id of supplied Customer which we want to update
			return custDao.save(cust);
		}
		
	
	
	
	@Override
	public Customer viewCustomer(Integer customerId) throws CustomerException {
		
	
			
		
		
		// TODO Auto-generated method stub
		Optional<Customer> customer = custDao.findById(customerId);
		if(customer != null ) {
			return customer.get();
		}
		else {
			throw new CustomerException("No Customer found");
		}
		

	}
	
	@Override
	public List<Customer> viewCustomerAll() throws CustomerException {
		
		
		
		// TODO Auto-generated method stub
		List<Customer> customerList = custDao.findAll();
		if(customerList.size() != 0) {
			return customerList;
		}
		else {
			throw new CustomerException("No Customer found");
		}
		
	}

	@Override
	public String deleteCustomer(Integer customerId) throws CustomerException {
		// TODO Auto-generated method stub
		 Optional<Customer> customer = custDao.findById(customerId);
		 if(customer.isPresent()) {
			 custDao.delete(customer.get());
			 return "account is deleted";
		 }
		 throw new CustomerException("Customer not found");
		
	}
	

}
