//package com.diniz.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.diniz.algafood.domain.model.Cozinha;
//import com.diniz.algafood.domain.repository.CozinhaRepository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//@Repository
//public class CozinhaRepositoryImpl implements CozinhaRepository{
//
//	
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<Cozinha> listar() {
//		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
//		
//		return query.getResultList();
//	}
//	
//	@Override
//	public Cozinha buscar(Long id) {
//		return manager.find(Cozinha.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public Cozinha salvar(Cozinha cozinha) {
//		return manager.merge(cozinha);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		var cozinha = buscar(id);
//		if(cozinha == null) throw new EmptyResultDataAccessException(1);
//		manager.remove(cozinha);
//	}
//
//	@Override
//	public List<Cozinha> listarPorNome(String nome) {
//		return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
//				.setParameter("nome", "%" + nome + "%")
//				.getResultList();
//	}
//
//}


