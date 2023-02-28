package com.hb.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NamingValidator.class)
public @interface NamingValidation {
	
//	public String message() default "Invalid Name first aphabet should be capital";
//	Class<?>[] groups() default { };
//
//	Class<? extends Payload>[] payload() default { };
}
