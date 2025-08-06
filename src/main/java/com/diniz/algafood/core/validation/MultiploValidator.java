package com.diniz.algafood.core.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numero;

	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numero = constraintAnnotation.numero();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if (value == null) {
			return true; // Null values are considered valid
		}
		
		var valorDecimal = BigDecimal.valueOf(value.doubleValue());
		var multiploDecimal = BigDecimal.valueOf(numero);
		
		return valorDecimal.remainder(multiploDecimal).compareTo(BigDecimal.ZERO) == 0;
	}
	
}
