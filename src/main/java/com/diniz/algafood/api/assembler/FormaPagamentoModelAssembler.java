package com.diniz.algafood.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.FormaPagamentoOutput;
import com.diniz.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoOutput toModel(FormaPagamento formaPagamento) {
		
		return modelMapper.map(formaPagamento, FormaPagamentoOutput.class);
	}
	
	public List<FormaPagamentoOutput> toCollectionModel(List<FormaPagamento> formaPagamentos) {
		return formaPagamentos.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.toList();
	}
}
