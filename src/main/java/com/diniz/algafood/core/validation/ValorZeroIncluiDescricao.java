package com.diniz.algafood.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidator.class})
public @interface ValorZeroIncluiDescricao {

	String message() default "descrição obrigatória inválida";
	Class<?>[] groups() default {};
	Class<? extends jakarta.validation.Payload>[] payload() default {};
	
	String valorField();
	String descricaoField();
	String descricaoObrigatoria();
	
}
