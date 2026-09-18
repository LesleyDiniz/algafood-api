package com.diniz.algafood.domain.model;

import java.util.List;

public enum StatusPedido {
	CRIADO(1, "Criado"),
	CONFIRMADO(2, "Confirmado", CRIADO),
	ENTREGUE(3, "Entregue", CONFIRMADO),
	CANCELADO(4, "Cancelado", CRIADO);
	
	private int Id;
	private String descricao;
	private List<StatusPedido> statusAnteriores;
	
	StatusPedido(int Id, String descricao, StatusPedido... statusAnteriores) {
		this.Id = Id;
		this.descricao = descricao;
		this.statusAnteriores = List.of(statusAnteriores);
	}
	
	public int getId() {
		return Id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
}
