package com.diniz.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.diniz.algafood.domain.repository.filter.PedidoFilter;
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
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//		var pedidos = pedidoResumoModelAssembler.toCollectionModel(cadastroPedido.listar());
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidos);
//		
//		var filterProvider = new SimpleFilterProvider().addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if (StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
	@GetMapping
	public Page<PedidoResumoOutput> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		var pedidosPage = cadastroPedido.pesquisar(filtro, pageable);
		
		var pedidosResumoOutputPage = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent());
		
		return new PageImpl<>(pedidosResumoOutputPage, pageable, pedidosPage.getTotalElements());
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
