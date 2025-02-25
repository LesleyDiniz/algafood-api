//package com.diniz.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.diniz.algafood.domain.model.Permissao;
//import com.diniz.algafood.domain.repository.PermissaoRepository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//@Repository
//public class PermissaoRepositoryImpl implements PermissaoRepository{
//
//	
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<Permissao> listar() {
//		TypedQuery<Permissao> query = manager.createQuery("from Permissao", Permissao.class);
//		
//		return query.getResultList();
//	}
//	
//	@Override
//	public Permissao buscar(Long id) {
//		return manager.find(Permissao.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public Permissao salvar(Permissao permissao) {
//		return manager.merge(permissao);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Permissao permissao) {
//		permissao = buscar(permissao.getId());
//		manager.remove(permissao);
//	}
//
//}
