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
	
	@Autowired
	ISharerService sharerService;
	
	@Override
	public Product createProduct(String name, ProductCategory category,
			Sharer sharer) {
		Product product = new Product();
		product.setCategory(category);
		product.setDescription("");
		product.setNom(name);
		product.setSharer(sharer);
		//product.setStatus(ProductStatus.available);
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
		product.setDescription(description);
		product.setNom(name);
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

	

	@Override
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

	@Override
	public void removeProduct(int objectId) {
		this.productRepository.delete(objectId);
	}

	@Override
	public Collection<Product> findProducts(int postcode) {
		Collection<Sharer> usersInTown = new ArrayList<>();
		usersInTown.addAll( this.sharerService.getSharers(postcode));
		Collection<Product> products = new ArrayList<>();
		for(Sharer s: usersInTown){
			products.addAll(this.productRepository.findBySharer(s));
		}
		return products;
	}

	@Override
	public Collection<Product> findProducts(ProductCategory category) {
		Collection<Product> products = new ArrayList<>();
		products.addAll(this.productRepository.findByCategory(category));
		return products;
	}

	@Override
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
	@Override
	public Collection<Product> findProducts(){
		return (Collection<Product>) this.productRepository.findAll();
	}

}
