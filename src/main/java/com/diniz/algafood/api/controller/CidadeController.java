package com.diniz.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.diniz.algafood.api.assembler.CidadeInputDisassembler;
import com.diniz.algafood.api.assembler.CidadeModelAssembler;
import com.diniz.algafood.api.model.CidadeInput;
import com.diniz.algafood.api.model.CidadeOutput;
import com.diniz.algafood.domain.exception.EstadoNaoEncontradoException;
import com.diniz.algafood.domain.exception.NegocioException;
import com.diniz.algafood.domain.service.CadastroCidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeOutput> listar() {
		return cidadeModelAssembler.toCollectionModel(cadastroCidade.listar());
	}
		
	@GetMapping("/{cidadeId}")
	public CidadeOutput buscar(@PathVariable Long cidadeId) {
		return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeOutput adicionar(@RequestBody @Valid CidadeInput cidadeInput ) {
		try {
			
			var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeOutput atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput ) {
		var cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
		
		cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		try {
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}

}
