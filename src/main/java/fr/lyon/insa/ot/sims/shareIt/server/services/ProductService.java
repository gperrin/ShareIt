package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Service
public class ProductService implements IProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Product createProduct(String name, ProductCategory category,
			Sharer sharer) {
		Product product = new Product();
		product.setCategory(category);
		product.setDescription("");
		product.setNom(name);
		product.setSharer(sharer);
		product.setStatus(ProductStatus.available);
		try{
			this.productRepository.save(product);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return product;
	}

	@Override
	public Product createProduct(String name, ProductCategory category,
			Sharer sharer, String description) {
		Product product = new Product();
		product.setCategory(category);
		product.setDescription("");
		product.setNom(name);
		product.setSharer(sharer);
		product.setStatus(ProductStatus.available);
		try{
			this.productRepository.save(product);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return product;
	}

	@Override
	public Collection<ProductCategory> getProductCategories() {
		List<ProductCategory> categoryList = new ArrayList<>(Arrays.asList(ProductCategory.values()));
		return categoryList;
	}

}
