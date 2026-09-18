package com.diniz.algafood.domain.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.NegocioException;
import com.diniz.algafood.domain.exception.PedidoNaoEncontradoException;
import com.diniz.algafood.domain.model.Pedido;
import com.diniz.algafood.domain.model.Produto;
import com.diniz.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId)
		);
	}
	
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido)
		);
	}

	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);

		pedido.setDataCriacao(java.time.OffsetDateTime.now());
		pedido.setSubtotal(new BigDecimal(0));
		pedido.setTaxaFrete(new BigDecimal(0));
		pedido.setValorTotal(new BigDecimal(0));
		
		preencheItensPedido(pedido);
		
		return pedidoRepository.save(pedido);
	}
	
	private void preencheItensPedido(Pedido pedido) {
		var restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
		pedido.getItens().forEach(item -> {
			var produto = restaurante.getProdutos().stream()
					.filter(p -> p.equals(item.getProduto()))
					.findFirst()
					.orElseThrow(() -> new NegocioException(
							String.format("O produto com código %d não pertence ao restaurante com código %d!", 
									item.getProduto().getId(), restaurante.getId())));
			
			item.setPrecoUnitario(produto.getPreco());
			item.setPrecoTotal(item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())));
		});
	}
	
	private void validarPedido(Pedido pedido) {
		var restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
		
		if (!restaurante.getAtivo()) {
			throw new NegocioException(
					String.format("Restaurante com código %d não está ativo!", restaurante.getId()));
		}
		
		if (!restaurante.getAberto()) {
			throw new NegocioException(
					String.format("Restaurante com código %d não está aberto!", restaurante.getId()));
		}
		
		var formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());
		if(restaurante.getFormasPagamento().stream()
				.noneMatch(fp -> fp.equals(pedido.getFormaPagamento()))) {
			throw new NegocioException(
					String.format("Forma de pagamento com código %d não é aceita pelo restaurante com código %d!", 
							pedido.getFormaPagamento().getId(), restaurante.getId()));
		}

		var cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
		var cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
		
	}
	
	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = cadastroProduto.buscarOuFalhar(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
}
