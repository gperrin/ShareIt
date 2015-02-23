package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface IProductService {

	public Product createProduct ( String name, ProductCategory category, Sharer sharer);
	public Product createProduct ( String name, ProductCategory category, Sharer sharer, String description);
	public Product getProduct(int id);
	public void removeProduct(int objectId);
	public Collection<Product> findProducts();
	public Collection<Product> findProducts(int postcode );
	public Collection<Product> findProducts(ProductCategory category);
	public Collection<Product> findProducts(int postcode, ProductCategory category);
	

}
