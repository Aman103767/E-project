package com.hb.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NamingValidator implements ConstraintValidator<NamingValidation,String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
//		String [] arr  = value.split(" ");
//		for(String i: arr) {
//			if(i.charAt(0)<65 || i.charAt(0)> 90) {
//				return false;
//			}
//		}
		return true;
		
	}

	
}
