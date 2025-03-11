package com.diniz.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.model.Restaurante;
import com.diniz.algafood.domain.service.CadastroCozinhaService;
import com.diniz.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public List<Restaurante> listar() {
		return cadastroRestaurante.listar();
	}
		
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		var restaurante = cadastroRestaurante.buscar(restauranteId);
		
		if (restaurante != null) return ResponseEntity.ok(restaurante);
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante ) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(cadastroRestaurante.salvar(restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante ) {
		var restauranteAtual = cadastroRestaurante.buscar(restauranteId);
		
		if(restauranteAtual == null) return ResponseEntity.notFound().build();
		
		var cozinha = cadastroCozinha.buscar(restaurante.getCozinha().getId());
		if (cozinha == null) {
			return ResponseEntity.badRequest().body("Cozinha não encontrada");
		}
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");
		return ResponseEntity.ok(cadastroRestaurante.salvar(restauranteAtual));
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
		try {
			
			cadastroRestaurante.excluir(restauranteId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
		var restauranteAtual = cadastroRestaurante.buscar(restauranteId);
		
		if(restauranteAtual == null) return ResponseEntity.notFound().build();
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			BeanUtils.copyProperties(valorPropriedade, restauranteDestino, nomePropriedade);
		});		
	}
}
