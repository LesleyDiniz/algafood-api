package com.diniz.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diniz.algafood.api.assembler.ProdutoInputDisassembler;
import com.diniz.algafood.api.assembler.ProdutoModelAssembler;
import com.diniz.algafood.api.model.ProdutoOutput;
import com.diniz.algafood.api.model.input.ProdutoInput;
import com.diniz.algafood.domain.service.CadastroProdutoService;
import com.diniz.algafood.domain.service.CadastroRestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@GetMapping
	public List<ProdutoOutput> listar(@PathVariable Long restauranteId) {
		var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return produtoModelAssembler.toCollectionModel(cadastroProduto.listarPorRestaurante(restaurante));
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoOutput buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		var produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		return produtoModelAssembler.toModel(produto);
	}
		
	@PostMapping()
	public ProdutoOutput adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		var produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		cadastroProduto.salvar(produto);
		
		return produtoModelAssembler.toModel(produto);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoOutput atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		var produtoExistente = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoExistente);
		produtoExistente = cadastroProduto.salvar(produtoExistente);
		
		return produtoModelAssembler.toModel(produtoExistente);
	}
	
}
