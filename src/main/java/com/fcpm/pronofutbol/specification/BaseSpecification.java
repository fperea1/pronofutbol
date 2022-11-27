package com.fcpm.pronofutbol.specification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.utils.Utilidades;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BaseSpecification implements Specification<BaseEntity>{
	
	private static final long serialVersionUID = 1L;
	
	private SearchCriteriaColumn criteria;

	public BaseSpecification(SearchCriteriaColumn criteria) {
		super();
		this.criteria = criteria;
	}

	public SearchCriteriaColumn getCriteria() {
		return criteria;
	}

	@Override
	public Predicate toPredicate(Root<BaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
		switch  (criteria.getMatchMode()) {

	        case Constantes.STARTS_WITH:
	        	return builder.like(root.<String> get(criteria.getNameColumn()), criteria.getValue().toString() + "%");
	
	        case Constantes.ENDS_WITH:
	        	return builder.like(root.<String> get(criteria.getNameColumn()), "%" + criteria.getValue().toString());
	
	        case Constantes.CONTAINS:
	        	return builder.like(root.<String> get(criteria.getNameColumn()), "%" + criteria.getValue().toString() + "%");
	
	        case Constantes.NOT_CONTAINS:
	        	return builder.notLike(root.<String> get(criteria.getNameColumn()), "%" + criteria.getValue().toString() + "%");
	
	        case Constantes.EQUALS:
	        	return builder.equal(root.<String> get(criteria.getNameColumn()), criteria.getValue().toString());
	
	        case Constantes.NOT_EQUALS:
	        	return builder.notEqual(root.<String> get(criteria.getNameColumn()), criteria.getValue().toString());
	
	        case Constantes.BOOLEAN:
	        	return builder.equal(root.<Boolean> get(criteria.getNameColumn()), Boolean.parseBoolean((String) criteria.getValue()));
	
	        case Constantes.DATE_IS:
	        	return builder.equal(root.<Date> get(criteria.getNameColumn()), Utilidades.getDateFormat((String) criteria.getValue()));
	
	        case Constantes.DATE_IS_NOT:
	        	return builder.notEqual(root.<Date> get(criteria.getNameColumn()), Utilidades.getDateFormat((String) criteria.getValue()));
	
	        case Constantes.DATE_BEFORE:
	        	return builder.lessThan(root.<Date> get(criteria.getNameColumn()), Utilidades.getDateFormat((String) criteria.getValue()));
	
	        case Constantes.DATE_AFTER:
	        	return builder.greaterThan(root.<Date> get(criteria.getNameColumn()), Utilidades.getDateFormat((String) criteria.getValue()));
	
	        default:
	        	return null;
		}
	
	}

}
