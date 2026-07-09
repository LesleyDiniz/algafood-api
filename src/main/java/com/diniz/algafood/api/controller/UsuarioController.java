package com.diniz.algafood.api.controller;

import java.time.OffsetDateTime;
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

import com.diniz.algafood.api.assembler.UsuarioInputDisassembler;
import com.diniz.algafood.api.assembler.UsuarioModelAssembler;
import com.diniz.algafood.api.model.UsuarioOutput;
import com.diniz.algafood.api.model.input.SenhaInput;
import com.diniz.algafood.api.model.input.UsuarioComSenhaInput;
import com.diniz.algafood.api.model.input.UsuarioInput;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.exception.NegocioException;
import com.diniz.algafood.domain.service.CadastroUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioOutput> listar() {
		return usuarioModelAssembler.toCollectionModel(cadastroUsuario.listar());
	}
		
	@GetMapping("/{usuarioId}")
	public UsuarioOutput buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioOutput adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput ) {
		try {
			
			var usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
			usuario.setDataCadastro(OffsetDateTime.now());
			return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioOutput atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioSemSenhaInput ) {
		var usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		usuarioInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);
		try {
			return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@PutMapping("/{usuarioId}/senha")
	public UsuarioOutput atualizar(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput ) {
		var usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		try {
			return usuarioModelAssembler.toModel(cadastroUsuario.alterarSenha(usuarioAtual, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha()));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}		
	}
		
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId) {
		cadastroUsuario.excluir(usuarioId);
	}

}
