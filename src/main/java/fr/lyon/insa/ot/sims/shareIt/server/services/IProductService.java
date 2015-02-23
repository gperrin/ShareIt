package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface IProductService {

	public Product createProduct ( String name, ProductCategory category, Sharer sharer);
	public Product createProduct ( String name, ProductCategory category, Sharer sharer, String description);
	public Collection<ProductCategory> getProductCategories ( );
	public Product getProduct(int id);
}
