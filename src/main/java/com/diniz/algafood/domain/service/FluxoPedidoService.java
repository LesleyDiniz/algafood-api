package com.diniz.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);		
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}
}
