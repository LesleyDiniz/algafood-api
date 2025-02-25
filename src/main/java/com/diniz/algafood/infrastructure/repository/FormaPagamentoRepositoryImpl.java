//package com.diniz.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.diniz.algafood.domain.model.FormaPagamento;
//import com.diniz.algafood.domain.repository.FormaPagamentoRepository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//@Repository
//public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository{
//
//	
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<FormaPagamento> listar() {
//		TypedQuery<FormaPagamento> query = manager.createQuery("from FormaPagamento", FormaPagamento.class);
//		
//		return query.getResultList();
//	}
//	
//	@Override
//	public FormaPagamento buscar(Long id) {
//		return manager.find(FormaPagamento.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public FormaPagamento salvar(FormaPagamento FormaPagamento) {
//		return manager.merge(FormaPagamento);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(FormaPagamento FormaPagamento) {
//		FormaPagamento = buscar(FormaPagamento.getId());
//		manager.remove(FormaPagamento);
//	}
//
//}
