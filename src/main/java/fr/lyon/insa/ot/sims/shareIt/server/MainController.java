package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Exchange;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ExchangeStatus;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Message;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.BusinessLogicException;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;
import fr.lyon.insa.ot.sims.shareIt.server.services.IExchangeService;
import fr.lyon.insa.ot.sims.shareIt.server.services.IMessageService;
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
	
	@Autowired
	IMessageService messageService;
	
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
	
	@ExceptionHandler ( ResourceNotFoundException.class )
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Map<String, String> handleResourceNotFound(ResourceNotFoundException exception){
		Map<String,String> response = new HashMap<>();
		response.put("cause : ", exception.getMessage());
		return response;
	}
	@ExceptionHandler ( BusinessLogicException.class )
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Map<String, String> handleBusinessLogicException(BusinessLogicException exception){
		Map<String,String> response = new HashMap<>();
		response.put("cause : ", exception.getMessage());
		return response;
	}
	@ExceptionHandler ( RuntimeException.class )
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Map<String, String> handleRuntimeException(RuntimeException exception){
		Map<String,String> response = new HashMap<>();
		response.put("cause : ", exception.getMessage());
		return response;
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
		Sharer sharer = this.sharerService.createUser(lastName, firstName, postCode);
		if ( sharer == null){
			throw new RuntimeException("User could not be created.");
		}
		if ( age != null ){
			sharer.setAge(age);
		}
		if ( sex != null ){
			if ( sex != 'M' && sex != 'F'){
				throw new BusinessLogicException("Sex parameter can either be 'M' or 'F'");
			}
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
		if ( sharer == null ) throw new ResourceNotFoundException("user", id);
		return sharer; 
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id:[\\d]+}")
	public @ResponseStatus (HttpStatus.OK) Sharer updateUser(@PathVariable("id") int id,
			@RequestParam(required= false, value = "firstname") String firstName,
			@RequestParam(required= false, value = "lastname") String lastName,
			@RequestParam(required= false, value = "phone") String telephone,
			@RequestParam(required= false, value = "age") Integer age,
			@RequestParam(required= false, value = "sex") Character sex,
			@RequestParam(required= false, value = "postcode") Integer postCode,
			HttpEntity<byte[]> picture){
		Sharer sharer = this.sharerService.getUser(id);
		if ( sharer == null ) throw new ResourceNotFoundException ( "user", id);	
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
			if ( sex != 'M' && sex != 'F'){
				throw new BusinessLogicException("Sex parameter can either be 'M' or 'F'");
			}
			sharer.setSex(sex);
		}
		if ( picture.getBody()!= null){
			byte[] pic = picture.getBody();
			MediaType picType = picture.getHeaders().getContentType();
			sharer.setProfilePicture(pic);
			sharer.setProFilePictureType(picType);
		}
		if (! this.sharerService.updateUser(sharer)) throw new RuntimeException("User could not be updated :'(");
		return sharer;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/user/{id:[\\d]+}/product")
	public @ResponseBody Collection<Product> getProducts(@PathVariable("id") int userId){
		Sharer sharer = this.sharerService.getUser(userId);
		if ( sharer == null ) throw new ResourceNotFoundException ( "User", userId);
		return this.productService.getBySharer(sharer);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/user/{id:[\\d]+}/product")
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
		return product;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product/category")
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
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/product")
	public @ResponseBody Collection<Product> getProducts(
			@RequestParam(required= false, value="postcode")Integer postcode,
			@RequestParam(required=false, value ="category") Integer categoryId){
		if ( postcode == null && categoryId == null){
			return this.productService.findProducts();
		}
		else if ( postcode != null && categoryId == null ){
			return this.productService.findProducts(postcode);
		}
		else if ( postcode == null && categoryId != null ){
			ProductCategory category = this.productCategoryService.getById(categoryId);
			if ( category == null ) throw new ResourceNotFoundException("Category", categoryId);
			return this.productService.findProducts(category);
		}
		else{
			ProductCategory category = this.productCategoryService.getById(categoryId);
			if ( category == null ) throw new ResourceNotFoundException("Category", categoryId);
			return this.productService.findProducts(postcode, category);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/product/{id:[\\d]+}/borrow")
	public @ResponseBody Exchange askProduct(@PathVariable("id") int productId,
			@RequestParam("borrower") int borrowerId ){
		Sharer borrower = this.sharerService.getUser(borrowerId);
		Product product = this.productService.getProduct(productId);
		if ( borrower == null ) throw new ResourceNotFoundException("user", borrowerId);
		if ( product == null ) throw new ResourceNotFoundException("product", productId);
		Exchange exchange = this.exchangeService.createExhange(borrower, product);
		return exchange;				
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id:[\\d]+}/borrowed")
	@ResponseBody Collection<Exchange> getBorrowedProducts(@PathVariable("id") int userId,
			@RequestParam( required = false, value = "status") Integer exchangeStatus){
		Sharer borrower = this.sharerService.getUser(userId);
		if ( borrower == null ) throw new ResourceNotFoundException("User", userId);
		Collection<Exchange> exchanges;
		if ( exchangeStatus != null ){
			ExchangeStatus status;
			try{
				status = ExchangeStatus.values()[exchangeStatus];
				exchanges = this.exchangeService.findByBorrower(borrower, status);
			}catch ( IndexOutOfBoundsException e ){
				throw new ResourceNotFoundException("Exchange Status", exchangeStatus);
			}
		}
		else{
			exchanges = this.exchangeService.findByBorrower(borrower);
		}
		if (exchanges != null ){
			return exchanges;
		}
		return new ArrayList<Exchange> ();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id:[\\d]+}/lended")
	@ResponseBody Collection<Exchange> getLendedProducts(@PathVariable("id") int userId,
			@RequestParam (required = false, value = "status")Integer exchangeStatus){
		Sharer lender = this.sharerService.getUser(userId);
		if ( lender == null ) throw new ResourceNotFoundException("User", userId);
		Collection<Exchange> exchanges ;
		if ( exchangeStatus != null ){
			ExchangeStatus status;
			try{
				status = ExchangeStatus.values()[exchangeStatus];
				exchanges = this.exchangeService.findByLender(lender, status);
			}catch ( IndexOutOfBoundsException e ){
				throw new ResourceNotFoundException("Exchange Status", exchangeStatus);
			}
		}
		else{
			exchanges = this.exchangeService.findByLender(lender);

		}
		if (exchanges != null ){
			return exchanges;
		}
		return new ArrayList<Exchange> ();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/accept")
	@ResponseBody Exchange acceptExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException ( "Exchange", exchangeId );
		exchange = this.exchangeService.acceptExchange(exchange);
		return exchange;	
	}
	@RequestMapping(method = RequestMethod.PUT, value="/exchange/{id:[\\d]+}/reject")
	@ResponseBody Exchange rejectExchange(@PathVariable("id") int exchangeId){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException("Exchange", exchangeId);
		exchange = this.exchangeService.rejectExchange(exchange);
		return exchange;
	}
	@RequestMapping(method=RequestMethod.PUT, value="/exchange/{id:[\\d]+}/complete")
	@ResponseBody Exchange completeExchange(@PathVariable("id") int exchangeId,
			@RequestParam("returned") boolean objectReturned){
		Exchange exchange = this.exchangeService.getById(exchangeId);
		if ( exchange == null ) throw new ResourceNotFoundException ( "Exchange", exchangeId);
		exchange = this.exchangeService.setCompleted(exchange, objectReturned);
		return exchange;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/message/")
	@ResponseStatus (HttpStatus.CREATED) public @ResponseBody Message createMessage(
			@RequestParam(required = true, value = "sender") int sender,
			@RequestParam(required = true, value = "receiver") int receiver,
			@RequestParam(required = true, value = "message") String message
			){
		return this.messageService.createMessage(sender, receiver, message);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/contacts/{id}")
	public @ResponseBody Collection<Sharer> getContacts(@PathVariable("id") int userId){
		return this.messageService.findContacts(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/messages/{userId}/{contactId}")
	public @ResponseBody Collection<Message> getMessages(@PathVariable("userId") int userId, @PathVariable("contactId") int contactId){
		return this.messageService.findMessages(userId, contactId);
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/exchange/status")
	public @ResponseBody Collection<Map<Integer, ExchangeStatus>> getExchangeStatuses (){
		List<Map<Integer, ExchangeStatus>> result = new ArrayList<>();
		ExchangeStatus[] statusValues = ExchangeStatus.values();
		for ( int i = 0 ; i < statusValues.length ; i++ ){
			Map<Integer, ExchangeStatus>  val = new HashMap<>();
			val.put(i, statusValues[i]);
			result.add(val);
		}
		return result;
	}
}
