package com.diniz.algafood.api.exceptionhandler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";
	private static final String MSG_ERRO_RECURSO_NAO_ENCONTRADO = "Recurso que você está procurando não foi encontrado.";
	private static final String MSG_ERRO_RECURSO_EM_USO = "Recurso que você está tentando excluir está em uso e não pode ser removido.";
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		
		var statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		var problemType = ProblemType.ERRO_DE_SISTEMA;
		var detail = MSG_ERRO_GENERICO_USUARIO_FINAL;
		
		var problem = createProblemBuilder(statusCode, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), statusCode, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, statusCode, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, statusCode, request);
		} 
		
		var problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		var detail = "O corpo da requisição está inválido. Verifique a sintaxe.";
		
		var problem = createProblemBuilder(statusCode, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, statusCode, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		var problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		var detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getResourcePath());
		
		var problem = createProblemBuilder(statusCode, problemType, detail)
				.userMessage(MSG_ERRO_RECURSO_NAO_ENCONTRADO)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, statusCode, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, 
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, statusCode, request);
		}
		
		return super.handleTypeMismatch(ex, headers, statusCode, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, 
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		var problemType = ProblemType.PARAMETRO_INVALIDO;
		var detail = String.format("O parâmetro '%s' recebeu o valor '%s', que é de um tipo inválido. " +
				"Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), 
				ex.getValue(), 
				ex.getRequiredType().getSimpleName());
		
		var problem = createProblemBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, statusCode, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		var path = ex.getPath().stream()
				.map(p -> p.getFieldName())
				.reduce((first, second) -> first + "." + second)
				.orElse("unknown");
		
		var problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		var detail = String.format("A propriedade '%s' não existe. Corrija e informe uma propriedade válida.",
				path);
		var problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		var path = ex.getPath().stream()
				.map(p -> p.getFieldName())
				.reduce((first, second) -> first + "." + second)
				.orElse("unknown");
		
		var problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		var detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. " +
				"Corrija e informe um valor compatível com o tipo %s.",
				path, 
				ex.getValue(), 
				ex.getTargetType().getSimpleName());
		var problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		var problem = createProblemBuilder(status, problemType, ex.getMessage())
				.userMessage(MSG_ERRO_RECURSO_NAO_ENCONTRADO)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		var status = HttpStatus.CONFLICT;
		var problemType = ProblemType.ENTIDADE_EM_USO;
		var problem = createProblemBuilder(status, problemType, ex.getMessage())
				.userMessage(MSG_ERRO_RECURSO_EM_USO)
				.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(),
				status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.NEGOCIO_EXCEPTION;
		var problem = createProblemBuilder(status, problemType, ex.getMessage())
				.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
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
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode statusCode, ProblemType problemType, String detail) {
		return Problem.builder()
				.status(statusCode.value())
				.type(problemType.getUri())
				.tittle(problemType.getTitle())
				.detail(detail);
	}
}