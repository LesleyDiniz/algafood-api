package com.diniz.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diniz.algafood.api.assembler.PedidoInputDisassembler;
import com.diniz.algafood.api.assembler.PedidoModelAssembler;
import com.diniz.algafood.api.assembler.PedidoResumoModelAssembler;
import com.diniz.algafood.api.model.PedidoOutput;
import com.diniz.algafood.api.model.PedidoResumoOutput;
import com.diniz.algafood.api.model.input.PedidoInput;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.exception.NegocioException;
import com.diniz.algafood.domain.model.Pedido;
import com.diniz.algafood.domain.model.Usuario;
import com.diniz.algafood.domain.service.CadastroPedidoService;
import com.diniz.algafood.domain.service.EmissaoPedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private CadastroPedidoService cadastroPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@GetMapping
	public List<PedidoResumoOutput> listar() {
		return pedidoResumoModelAssembler.toCollectionModel(cadastroPedido.listar());
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoOutput buscar(@PathVariable String codigoPedido) {
		return pedidoModelAssembler.toModel(emissaoPedido.buscarOuFalhar(codigoPedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoOutput criar(@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		
		try {
			pedido.setCliente(new Usuario());
			pedido.getCliente().setId(1L);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
		pedido = emissaoPedido.emitir(pedido);		
		return pedidoModelAssembler.toModel(pedido);
	}
}
