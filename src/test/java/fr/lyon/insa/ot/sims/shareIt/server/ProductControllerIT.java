package fr.lyon.insa.ot.sims.shareIt.server;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import java.util.Date;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;

import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductCategoryRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserEventRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserStatsRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;
import fr.lyon.insa.ot.sims.shareIt.server.domain.builders.ProductBuilder;
import fr.lyon.insa.ot.sims.shareIt.server.domain.builders.SharerBuilder;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserCreatedEvent;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ProductControllerIT {

	@Value("${local.server.port}")
	private int serverPort;
	
	@Autowired
	SharerRepository sharerRepository;
	@Autowired
	UserStatsRepository userStatsRepository;
	@Autowired
	UserEventRepository userEventRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	private int user1Id;
	private int product1Id;
	private int user2Id;
	private int product2Id;
	
	@Before
	public void setUp(){
		userEventRepository.deleteAll();
		productRepository.deleteAll();
		sharerRepository.deleteAll();
		userStatsRepository.deleteAll();
		
		UserStats user1Stats = new UserStats();
		user1Stats = this.userStatsRepository.save(user1Stats);
		Sharer user1 = new SharerBuilder()
				.firstname("Mickey")
				.lastname("Mouse")
				.postCode(69100)
				.userStats(user1Stats)
				.age(12)
				.profilePicture(null)
				.profilePictureType(null)
				.telephone(null)
				.sex('M')
				.telephone("0123456789")
				.build();
		user1 = this.sharerRepository.save(user1);
		this.user1Id= user1.getId();
		UserCreatedEvent user1CreatedEvent = new UserCreatedEvent();
		user1CreatedEvent.setDate(new Date());
		user1CreatedEvent.setUser(user1);
		this.userEventRepository.save(user1CreatedEvent);
		ProductCategory bricolage = productCategoryRepository.findOne(2);
		Product product1 = new ProductBuilder()
			.category(bricolage)
			.name("Marteau")
			.description("Un beau marteau")
			.sharer(user1)
			.build();
		product1 = this.productRepository.save(product1);
		this.product1Id= product1.getId();
		
		UserStats user2Stats = new UserStats();
		user2Stats = this.userStatsRepository.save(user2Stats);
		Sharer user2 = new SharerBuilder()
				.firstname("Mike")
				.lastname("Tyson")
				.postCode(89000)
				.userStats(user2Stats)
				.age(35)
				.profilePicture(null)
				.profilePictureType(null)
				.telephone(null)
				.sex('M')
				.telephone("0123786789")
				.build();
		user1 = this.sharerRepository.save(user2);
		this.user2Id= user2.getId();
		UserCreatedEvent user2CreatedEvent = new UserCreatedEvent();
		user2CreatedEvent.setDate(new Date());
		user2CreatedEvent.setUser(user2);
		this.userEventRepository.save(user2CreatedEvent);
		
		ProductCategory instruments= productCategoryRepository.findOne(5);
		Product product2 = new ProductBuilder()
			.category(instruments)
			.name("Violon")
			.description("violon")
			.sharer(user2)
			.build();
		product2 = this.productRepository.save(product2);
		this.product2Id= product2.getId();
		
		RestAssured.port = serverPort;
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig()
                .defaultContentCharset("UTF-8"));
	}
	@Test
	public void createProductShouldReturnSavedProduct(){
		given()
			.param("name", "perceuse")
			.param("description", "Une perceuse")
			.param("category", 2)
		.when()
			.post("/user/"+this.user1Id+"/product")
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.body("name", is("perceuse"))
			.body("description", is("Une perceuse"))
			.body("sharer.firstname", is("Mickey"))
			.body("category.name", is("Bricolage"));
	}
	@Test
	public void getProductShouldReturnProduct(){
		when()
			.get("/product/"+this.product1Id)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("name", is("Marteau"))
			.body("description", is("Un beau marteau"))
			.body("sharer.firstname", is("Mickey"))
			.body("category.name", is("Bricolage"));
	}
	@Test
	public void getProductCategoryShouldReturnCategories(){
		given()
		
			.contentType("application/json; charset=UTF-8")
		.when()
			.get("/product/category")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("name", containsInAnyOrder("Animaux", "Bricolage", "Cuisine",
					"DÃ©coration", "Instruments", "Jardinage", "Livres", "MultimÃ©dia",
					"VÃ©hicules", "VÃªtements")); //l'encodage des caractères est bon sur le serveur mais 
					// pas en 8tf-8 en utilisant rest assured, pas sûr de pourquoi	
	}
	@Test
	public void getProductByPostCodeAndCategoryShouldReturnOneProduct(){
		given()
			.param("postcode",69100)
			.param("category", 2)
		.when()
			.get("/product")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("name", hasItems("Marteau"));
	}
	@Test
	public void getProductShouldReturnAllProducts(){
		when()
			.get("/product")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("name", containsInAnyOrder("Marteau", "Violon"));
	}
	
}
