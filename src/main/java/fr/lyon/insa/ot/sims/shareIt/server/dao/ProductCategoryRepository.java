package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {
	public Collection<ProductCategory> findByName(String name);
}
