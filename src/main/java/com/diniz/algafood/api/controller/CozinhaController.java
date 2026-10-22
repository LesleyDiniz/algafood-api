package com.diniz.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diniz.algafood.api.assembler.CozinhaInputDisassembler;
import com.diniz.algafood.api.assembler.CozinhaModelAssembler;
import com.diniz.algafood.api.model.CozinhaOutput;
import com.diniz.algafood.api.model.input.CozinhaInput;
import com.diniz.algafood.domain.model.Cozinha;
import com.diniz.algafood.domain.service.CadastroCozinhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@GetMapping
	public Page<CozinhaOutput> listar(Pageable pageable) {
		Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);
		
		List<CozinhaOutput> listaCozinhas = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		
		return new PageImpl<>(listaCozinhas, pageable, cozinhasPage.getTotalElements());
	}
		
	@GetMapping("/{cozinhaId}")
	public CozinhaOutput buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaOutput adicionar(@RequestBody @Valid CozinhaInput cozinhaInput ) {
		var cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaOutput atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput ) {
		var cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);		
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);		
	}
	
}
