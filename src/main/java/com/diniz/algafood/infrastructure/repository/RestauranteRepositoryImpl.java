//package com.diniz.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.diniz.algafood.domain.model.Restaurante;
//import com.diniz.algafood.domain.repository.RestauranteRepository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//@Repository
//public class RestauranteRepositoryImpl implements RestauranteRepository {
//
//	
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<Restaurante> listar() {
//		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);
//		
//		return query.getResultList();
//	}
//	
//	@Override
//	public Restaurante buscar(Long id) {
//		return manager.find(Restaurante.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public Restaurante salvar(Restaurante restaurante) {
//		return manager.merge(restaurante);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		var restaurante = buscar(id);
//		manager.remove(restaurante);
//	}
//
//}
