package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;

@Service
public class ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	SharerService sharerService;
	
	public Product createProduct(String name, ProductCategory category,
			Sharer sharer) {
		Product product = new Product();
		product.setCategory(category);
		product.setDescription("");
		product.setName(name);
		product.setSharer(sharer);
		product.setStatus(ProductStatus.disponible);
		try{
			this.productRepository.save(product);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return product;
	}

	public Product createProduct(String name, ProductCategory category,
			Sharer sharer, String description) {
		Product product = new Product();
		product.setCategory(category);
		product.setDescription(description);
		product.setName(name);
		product.setSharer(sharer);
		product.setStatus(ProductStatus.disponible);
		try{
			this.productRepository.save(product);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return product;
	}

	public Product getProduct(int id) {
		Product product = null;
		try{
			product = this.productRepository.findOne(id);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return product;
	}

	public void removeProduct(int objectId) {
		Product product = this.productRepository.findOne(objectId);
		if ( product != null ){
			product.setStatus(ProductStatus.retire);
			this.productRepository.save(product);
		}
		else{
			throw new ResourceNotFoundException("Product", objectId);
		}
	}

	public Collection<Product> findProducts(int postcode) {
		Collection<Sharer> usersInTown = new ArrayList<>();
		usersInTown.addAll( this.sharerService.getSharers(postcode));
		Collection<Product> products = new ArrayList<>();
		for(Sharer s: usersInTown){
			products.addAll(this.productRepository.findBySharer(s));
		}
		return products;
	}

	public Collection<Product> findProducts(ProductCategory category) {
		Collection<Product> products = new ArrayList<>();
		products.addAll(this.productRepository.findByCategory(category));
		return products;
	}

	public Collection<Product> findProducts( int postcode, ProductCategory category) {
		Collection<Product> products = new ArrayList<>();
		products.addAll(this.productRepository.findByCategory(category));
		Collection<Product> filteredResults = new ArrayList<>();
		for ( Product product : products ){
			if ( product.getSharer().getPostCode() == postcode){
				filteredResults.add(product);
			}
		}
		return filteredResults;
	}

	public Collection<Product> findProducts(){
		return (Collection<Product>) this.productRepository.findAll();
	}

	public Collection<Product> getBySharer(Sharer sharer) {
		return this.productRepository.findBySharer(sharer);
	}

}
