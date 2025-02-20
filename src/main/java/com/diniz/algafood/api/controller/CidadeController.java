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
import org.springframework.web.bind.annotation.RestController;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.model.Cidade;
import com.diniz.algafood.domain.service.CadastroEstadoService;
import com.diniz.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@GetMapping
	public List<Cidade> listar() {
		return cadastroCidade.listar();
	}
		
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
		var cidade = cadastroCidade.buscar(cidadeId);
		
		if (cidade != null) return ResponseEntity.ok(cidade);
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade ) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(cadastroCidade.salvar(cidade));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade ) {
		var cidadeAtual = cadastroCidade.buscar(cidadeId);
		
		if(cidadeAtual == null) return ResponseEntity.notFound().build();
		
		var estado = cadastroEstado.buscar(cidade.getEstado().getId());
		if (estado == null) {
			return ResponseEntity.badRequest().body("Estado não encontrada");
		}
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		return ResponseEntity.ok(cadastroCidade.salvar(cidadeAtual));
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {
		try {
			
			cadastroCidade.excluir(cidadeId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
	}
}
