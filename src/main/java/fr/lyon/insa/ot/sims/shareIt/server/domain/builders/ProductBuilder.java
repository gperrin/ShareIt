package fr.lyon.insa.ot.sims.shareIt.server.domain.builders;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public class ProductBuilder {
	private Product product;
	
	public ProductBuilder id ( int id ){
		product.setId(id);
		return this;
	}
	public ProductBuilder name ( String name){
		product.setName(name);
		return this;
	}
	public ProductBuilder description ( String description ){
		product.setDescription(description);
		return this;
	}
	public ProductBuilder sharer ( Sharer sharer ){
		product.setSharer(sharer);
		return this;
	}
	public ProductBuilder productStatus ( ProductStatus productStatus ){
		product.setStatus(productStatus);
		return this;
	}
	public ProductBuilder category ( ProductCategory category){
		product.setCategory(category);
		return this;
	}
	public Product build ( ){
		return product;
	}
}
