package com.hb.validations;

import java.util.Set;

import javax.validation.ConstraintViolation;

import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.hb.controller.CustomerController;
import com.hb.models.CustomerDTO;


@ControllerAdvice(assignableTypes = CustomerController.class)
public class CustomerValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
	  return CustomerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CustomerDTO custDto =  (CustomerDTO)target;
		String [] arr  = custDto.getName().split(" ");
		for(String i: arr) {
			if(i.charAt(0)<65 || i.charAt(0)> 90) {
				 errors.rejectValue("name", "try again","name first and last character must be capital");
				 return;

			}
		}
	   
		
	
	}

	
	
	

}
