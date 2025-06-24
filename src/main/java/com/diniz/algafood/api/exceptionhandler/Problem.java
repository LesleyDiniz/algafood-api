package com.diniz.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter 
@Builder
public class Problem {

	private String tittle;
	private String type;
	private Integer status;
	private String detail;
	
	private String userMessage;
	
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
}
