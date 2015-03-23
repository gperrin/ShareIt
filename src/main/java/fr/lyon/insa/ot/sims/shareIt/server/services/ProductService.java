package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
	
	@Autowired
	ProductCategoryService productCategoryService;
	
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
	
	public List<Product> findProducts(Integer postcode, Integer categoryId, String name, int userId){
		Collection<Product> result = this.findProducts();
		Sharer user = this.sharerService.getUser(userId);
		if ( user == null ) throw new ResourceNotFoundException ( "User", userId);
	    if (categoryId != null) {
	    	ProductCategory category = this.productCategoryService.getById(categoryId);
	    	if ( category == null )throw new ResourceNotFoundException("Product category", categoryId);
	    	result = CollectionUtils.intersection(result, this.productRepository.findByCategory(category));
	    }
	    if (postcode != null) {
	    	Collection<Sharer> usersInTown = new ArrayList<>();
			usersInTown.addAll( this.sharerService.getSharers(postcode));
			Collection<Product> products = new ArrayList<>();
			for(Sharer s: usersInTown){
				products.addAll(this.productRepository.findBySharer(s));
			}
			result = CollectionUtils.intersection(result, products);
	    }
	    if (name != null && !name.isEmpty()) {
	    	result = CollectionUtils.intersection(result, this.productRepository.findByNameLike(name));
	    } 
	    List<Sharer> nearbySharers = this.sharerService.getNearbySharers(userId);
	    List<Product> orderedProducts = new LinkedList<>();
	    for ( Sharer sharer : nearbySharers ){
	    	for ( Product p : result){
	    		if ( p.getSharer().equals( sharer)){
	    			orderedProducts.add(p);
	    		}
	    	}
	    }
		return orderedProducts;
	}

	public Collection<Product> findProducts(){
		return (Collection<Product>) this.productRepository.findAll();
	}

	public Collection<Product> getBySharer(Sharer sharer) {
		return this.productRepository.findBySharer(sharer);
	}
	public Product updateProduct ( Product product){
		return this.productRepository.save(product);
	}

}
