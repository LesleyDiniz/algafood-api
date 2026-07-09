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

import com.diniz.algafood.api.assembler.GrupoInputDisassembler;
import com.diniz.algafood.api.assembler.GrupoModelAssembler;
import com.diniz.algafood.api.model.GrupoOutput;
import com.diniz.algafood.api.model.input.GrupoInput;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.exception.NegocioException;
import com.diniz.algafood.domain.service.CadastroGrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoOutput> listar() {
		return grupoModelAssembler.toCollectionModel(cadastroGrupo.listar());
	}
		
	@GetMapping("/{grupoId}")
	public GrupoOutput buscar(@PathVariable Long grupoId) {
		return grupoModelAssembler.toModel(cadastroGrupo.buscarOuFalhar(grupoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoOutput adicionar(@RequestBody @Valid GrupoInput grupoInput ) {
		try {
			
			var grupo = grupoInputDisassembler.toDomainObject(grupoInput);
			
			return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupo));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{grupoId}")
	public GrupoOutput atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput ) {
		var grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
		
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		try {
			return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupoAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);
	}

}
