//package com.diniz.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.diniz.algafood.domain.model.Cidade;
//import com.diniz.algafood.domain.repository.CidadeRepository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//@Repository
//public class CidadeRepositoryImpl implements CidadeRepository{
//
//	
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<Cidade> listar() {
//		TypedQuery<Cidade> query = manager.createQuery("from Cidade", Cidade.class);
//		
//		return query.getResultList();
//	}
//	
//	@Override
//	public Cidade buscar(Long id) {
//		return manager.find(Cidade.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public Cidade salvar(Cidade cidade) {
//		return manager.merge(cidade);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		var cidade = buscar(id);
//		if(cidade == null) throw new EmptyResultDataAccessException(1);
//		manager.remove(cidade);
//	}
//
//}
