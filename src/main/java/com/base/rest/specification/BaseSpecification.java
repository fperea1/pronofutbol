package com.base.rest.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.base.rest.constant.Constantes;
import com.base.rest.entities.BaseEntity;
import com.base.rest.utils.bd.SearchCriteriaColumn;

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
	
	        default:
	        	return null;
		}
	
	}

}
