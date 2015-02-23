package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;

public interface IProductCategoryService {

	
	public void createCategory( String name);
	public Collection<ProductCategory> getCategories();
	public ProductCategory getById(int id);
}
