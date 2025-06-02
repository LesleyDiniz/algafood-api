package com.diniz.algafood.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		
		var problem = createProblemBuilder(status, problemType, ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		var status = HttpStatus.CONFLICT;
		var problemType = ProblemType.ENTIDADE_EM_USO;
		var problem = createProblemBuilder(status, problemType, ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(),
				status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.NEGOCIO_EXCEPTION;
		var problem = createProblemBuilder(status, problemType, ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(),
				status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, 
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					.tittle(ex.getMessage())
					.status(statusCode.value())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.tittle((String) body)
					.status(statusCode.value())
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.tittle(problemType.getTitle())
				.detail(detail);
	}
}