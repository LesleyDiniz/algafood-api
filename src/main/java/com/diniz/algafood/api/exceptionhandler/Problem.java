package com.diniz.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

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
	private List<Object> objects;
	
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	
	@Getter
	@Builder
	public static class Object {
		private String name;
		private String userMessage;
	}
}
