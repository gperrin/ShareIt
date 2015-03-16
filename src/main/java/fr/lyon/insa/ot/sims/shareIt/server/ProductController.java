package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.Collection;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ProductCreatedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.ProductDeletedEvent;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;

@Controller
public class ProductController extends GenericController{
	
	
	@RequestMapping(method = RequestMethod.GET, value="/user/{id:[\\d]+}/product")
	public @ResponseBody Collection<Product> getProducts(@PathVariable("id") int userId){
		Sharer sharer = this.sharerService.getUser(userId);
		if ( sharer == null ) throw new ResourceNotFoundException ( "User", userId);
		return this.productService.getBySharer(sharer);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/user/{id:[\\d]+}/product")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Product createProduct (@PathVariable("id") int userId,
			@RequestParam(required = true, value = "name") String name,
			@RequestParam(required = false, value = "description") String description,
			@RequestParam(required = true, value = "category") int category
			){
		Product product;
		Sharer user = this.sharerService.getUser(userId);
		if ( user == null ) throw new ResourceNotFoundException("user", userId);
		ProductCategory matchingCategory = null;
		matchingCategory = this.productCategoryService.getById(category);
		if ( matchingCategory == null ) throw new ResourceNotFoundException("ProductCategory", category);
		if ( description == null ){
			product = this.productService.createProduct(name, matchingCategory, user);
		}
		else{
			product = this.productService.createProduct(name, matchingCategory, user, description);
		}
		ProductCreatedEvent productCreatedEvent= new ProductCreatedEvent ();
		productCreatedEvent.setDate(new Date());
		productCreatedEvent.setProduct(product);
		productCreatedEvent.setUser(user);
		this.userEventService.persistEvent(productCreatedEvent);
		return product;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product/category",
			produces = "application/json; charset=utf-8")
	public @ResponseBody Collection<ProductCategory> getProductCategories(){
		return this.productCategoryService.getCategories();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product/{id}")
	public @ResponseBody Product getProduct(@PathVariable("id")int id){
		Product product = this.productService.getProduct(id);
		if ( product == null ) throw new ResourceNotFoundException("Product", id);
		return product; 
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/product/{id}")
	public @ResponseStatus(HttpStatus.OK) void removeProduct(@PathVariable("id")int objectId){
		this.productService.removeProduct(objectId);
		ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
		productDeletedEvent.setDate(new Date());
		Product product = this.productService.getProduct(objectId);
		productDeletedEvent.setProduct(product);
		productDeletedEvent.setUser(product.getSharer());
		this.userEventService.persistEvent(productDeletedEvent);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product")
	public @ResponseBody Collection<Product> getProducts(
			@RequestParam(required= false, value="postcode")Integer postcode,
			@RequestParam(required=false, value ="category") Integer categoryId,
			@RequestParam(required=false, value = "name") String name){
		return this.productService.findProducts(postcode, categoryId, name);
	}
}
