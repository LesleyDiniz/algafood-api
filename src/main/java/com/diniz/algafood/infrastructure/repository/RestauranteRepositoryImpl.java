package com.diniz.algafood.infrastructure.repository;

import static com.diniz.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.diniz.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.diniz.algafood.domain.model.Restaurante;
import com.diniz.algafood.domain.repository.RestauranteRepository;
import com.diniz.algafood.domain.repository.RestauranteRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries  {

	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

//	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//		var jpql = new StringBuilder();
//		jpql.append("from Restaurante where 0 = 0 ");
//		
//		var params = new HashMap<String, Object>();
//				
//		if(StringUtils.hasLength(nome)) {
//			jpql.append("and nome like :nome ");
//			params.put("nome", "%" + nome + "%");
//		}
//		
//		if(taxaFreteInicial != null) {
//			jpql.append("and taxaFrete >= :taxaInicial ");
//			params.put("taxaInicial", taxaFreteInicial);
//		}
//		
//		if(taxaFreteFinal != null) {
//			jpql.append("and taxaFrete <= :taxaFinal ");
//			params.put("taxaFinal", taxaFreteFinal);
//		}
//		
//		var query = manager.createQuery(jpql.toString(), Restaurante.class);
//		
//		params.forEach((chave, valor) -> query.setParameter(chave, valor));
//		
//		return query.getResultList();
//	}
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class); // from Restaurante
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
		}
		
		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(
					root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if (taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(
					root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		return query.getResultList();
	}
	
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}

}
