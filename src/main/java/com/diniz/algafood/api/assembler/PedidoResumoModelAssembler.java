package com.diniz.algafood.api.assembler;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.PedidoResumoOutput;
import com.diniz.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoOutput toModel(Pedido pedido) {
		
		return modelMapper.map(pedido, PedidoResumoOutput.class);
	}
	
	public List<PedidoResumoOutput> toCollectionModel(Collection<Pedido> pedidos) {
		return pedidos.stream()
				.sorted((f1, f2) -> f1.getId().compareTo(f2.getId()))
				.map(pedido -> toModel(pedido))
				.toList();
	}
}
