package com.diniz.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.model.Estado;
import com.diniz.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar() {
		return cadastroEstadoService.listar();
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
		var estado = cadastroEstadoService.buscar(estadoId);
		if (estado != null) return ResponseEntity.ok(estado);
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado ) {
		return cadastroEstadoService.salvar(estado);
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado ) {
		var estadoAtual = cadastroEstadoService.buscar(estadoId);
		
		if(estadoAtual == null) return ResponseEntity.notFound().build();
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return ResponseEntity.ok(cadastroEstadoService.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable Long estadoId) {
		try {
			
			cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
	}
}
