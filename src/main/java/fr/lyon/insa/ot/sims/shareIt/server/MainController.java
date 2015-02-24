package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.services.IExchangeService;
import fr.lyon.insa.ot.sims.shareIt.server.services.IProductCategoryService;
import fr.lyon.insa.ot.sims.shareIt.server.services.IProductService;
import fr.lyon.insa.ot.sims.shareIt.server.services.ISharerService;



@Controller
public class MainController {
	
	@Autowired
	SharerRepository sharerRepository;
	
	@Autowired
	ISharerService sharerService;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	IProductCategoryService productCategoryService;
	
	@Autowired
	IExchangeService exchangeService;
	
	@RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
	@RequestMapping
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public @ResponseBody HashMap<String, Object> test(){
		HashMap<String, Object> result = new HashMap<>();
		result.put("gros fail", true);
		
		return result;
	}
	
	@RequestMapping(value = "/picture" , method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT) void postPicture (HttpEntity<byte[]> requestEntity){
		/*byte[] payload = requestEntity.getBody();
		HttpHeaders headers = requestEntity.getHeaders();
		System.out.println(headers.getContentType().toString());
	    InputStream logo = new ByteArrayInputStream(payload);
	    System.out.println(payload.length);
	    Sharer user = new Sharer();
	    user.setAge(22);
	    user.setFirstname("jean paul");
	    user.setLastname("dupond");
	    user.setProfilePicture(payload);
	    user.setProFilePictureType(headers.getContentType());
	    user.setRating(2);
	    this.sharerRepository.save(user);*/
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/user")
	@ResponseStatus (HttpStatus.CREATED) public @ResponseBody Sharer createUser(
			@RequestParam(required = true, value="firstname")String firstName,
			@RequestParam(required = true, value="lastname")String lastName,
			@RequestParam(required = true, value="postcode")int postCode,
			@RequestParam(required = false, value = "age") Integer age,
			@RequestParam(required = false , value = "sex") Character sex,
			@RequestParam(required = false , value = "phone") String telephone){
		//TODO : créer utilisateur
		Sharer sharer = this.sharerService.createUser(lastName, firstName, postCode);
		if ( sharer == null){
			return null;//TODO : err msg
		}
		if ( age != null ){
			sharer.setAge(age);
		}
		if ( sex != null ){
			sharer.setSex(sex);
		}
		if ( telephone != null ){
			sharer.setTelephone(telephone);
		}
		if (sex != null || telephone != null || age != null){
			this.sharerService.updateUser(sharer);
		}
		return sharer;
	}
	@RequestMapping(method = RequestMethod.GET, value="/user/{id:[\\d]+}")
	public @ResponseBody Sharer getUser(@PathVariable("id")int id){
		Sharer sharer = this.sharerService.getUser(id);
		return sharer; //TODO : rediriger sur erreur si nul
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id:[\\d]+}")
	public @ResponseStatus (HttpStatus.OK) void updateUser(@PathVariable("id") int id,
			@RequestParam(required= false, value = "firstname") String firstName,
			@RequestParam(required= false, value = "lastname") String lastName,
			@RequestParam(required= false, value = "phone") String telephone,
			@RequestParam(required= false, value = "age") Integer age,
			@RequestParam(required= false, value = "sex") Character sex,
			@RequestParam(required= false, value = "postcode") Integer postCode,
			HttpEntity<byte[]> picture){
		Sharer sharer = this.sharerService.getUser(id);
		if ( sharer != null ){
			if ( firstName != null ){
				sharer.setFirstname(firstName);
			}
			if ( lastName != null ){
				sharer.setLastname(lastName);
			}
			if ( telephone != null ){
				sharer.setTelephone(telephone);
			}
			if ( age != null ){
				sharer.setAge(age);
			}
			if ( postCode != null ){
				sharer.setPostCode(postCode);
			}
			if ( sex != null ){
				sharer.setSex(sex);
			}
			if ( picture.getBody()!= null){
				byte[] pic = picture.getBody();
				MediaType picType = picture.getHeaders().getContentType();
				sharer.setProfilePicture(pic);
				sharer.setProFilePictureType(picType);
			}
			this.sharerService.updateUser(sharer);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/user/{id:[\\d]+}/product")
	public @ResponseBody Product createProduct (@PathVariable("id") int userId,
			@RequestParam(required = true, value = "name") String name,
			@RequestParam(required = false, value = "description") String description,
			@RequestParam(required = true, value = "category") int category
			){
		Product product;
		Sharer user = this.sharerService.getUser(userId);
		if ( user == null ){
			return null; //TODO : err msg
		}
		ProductCategory matchingCategory = null;
		matchingCategory = this.productCategoryService.getById(category);
		if ( matchingCategory == null ){
			return null; //TODO : message d'erreur
		}
		if ( description == null ){
			product = this.productService.createProduct(name, matchingCategory, user);
		}
		else{
			product = this.productService.createProduct(name, matchingCategory, user, description);
		}
		return product;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product/category")
	public @ResponseBody Collection<ProductCategory> getProductCategories(){
		return this.productCategoryService.getCategories();
	}
	

	/*@RequestMapping(method = RequestMethod.GET, value="/product")
	public @ResponseBody Collection<Product> getProductByCategory (
			@RequestParam(required = true, value = "category") String category){
		
	}*/

	@RequestMapping(method = RequestMethod.GET, value="/product/{id}")
	public @ResponseBody Product getProduct(@PathVariable("id")int id){
		Product product = this.productService.getProduct(id);
		
		return product; //TODO : rediriger sur erreur si nul
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/product/{id}")
	public @ResponseStatus(HttpStatus.OK) void removeProduct(@PathVariable("id")int objectId){
		this.productService.removeProduct(objectId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product")
	public @ResponseBody Collection<Product> getProducts(
			@RequestParam(required= false, value="postcode")Integer postcode,
			@RequestParam(required=false, value ="category") Integer categoryId){
		ProductCategory category = null;
		if ( categoryId != null ){
			category = this.productCategoryService.getById(categoryId);
		}
		if ( category == null ){
			if ( postcode == null ){ //recherche de tous les produits
				return this.productService.findProducts();
			}
			else{ // recherche par code postal
				return this.productService.findProducts(postcode);
			}
		}
		else{
			if ( postcode == null ){ //recherche par categorie
				return this.productService.findProducts(category);
			}
			else{ // recherche par code postal et categorie
				return this.productService.findProducts( postcode, category );
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/product/{id:[\\d]+}/borrow")
	public @ResponseBody Exchange askProduct(@PathVariable("id") int productId,
			@RequestParam("borrower") int borrowerId,
			@RequestParam("lender") int lenderId){
		Sharer borrower = this.sharerService.getUser(borrowerId);
		Sharer lender = this.sharerService.getUser(lenderId);
		Product product = this.productService.getProduct(productId);
		if (borrower != null && lender != null && product != null){
			Exchange exchange = this.exchangeService.createExhange(borrower, lender, product);
			if ( exchange != null ){
				return exchange;
			}
			else{
				return null; // msg d'erreur ici
			}
		}
		else{
			return null; //TODO : msg erreur
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id:[\\d]+}/borrowed")
	@ResponseBody Collection<Exchange> getBorrowedProducts(@PathVariable("id") int userId){
		Sharer borrower = this.sharerService.getUser(userId);
		if (borrower != null ){
			Collection<Exchange> exchanges = this.exchangeService.findByBorrower(borrower);
			if (exchanges != null ){
				return exchanges;
			}
		}
			return new ArrayList<Exchange> ();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id:[\\d]+}/lended")
	@ResponseBody Collection<Exchange> getLendedProducts(@PathVariable("id") int userId){
		Sharer lender = this.sharerService.getUser(userId);
		if (lender!= null ){
			Collection<Exchange> exchanges = this.exchangeService.findByLender(lender);
			if (exchanges != null ){
				return exchanges;
			}
		}
			return new ArrayList<Exchange> ();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/accept")
	@ResponseBody Exchange acceptExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange != null ){
			exchange = this.exchangeService.acceptExchange(exchange);
			return exchange;
		}
		else{
			//TODO :err msg
			return null;
		}
	}
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/reject")
	@ResponseBody Exchange rejectExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange != null ){
			exchange = this.exchangeService.rejectExchange(exchange);
			return exchange;
		}
		else{
			//TODO :err msg
			return null;
		}
	}
	@RequestMapping(method=RequestMethod.PUT, value="/exchange/{id:[\\d]+}/complete")
	@ResponseBody Exchange completeExchange(@PathVariable("id") int exchangeId,
			@RequestParam("returned") boolean objectReturned){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange != null ){
			exchange = this.exchangeService.setCompleted(exchange, objectReturned);
			return exchange;
		}
		else{
			//TODO : err msg
			return null;
		}
	}
}
