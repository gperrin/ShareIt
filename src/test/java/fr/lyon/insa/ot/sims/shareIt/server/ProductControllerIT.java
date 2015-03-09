package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.Date;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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

import fr.lyon.insa.ot.sims.shareIt.server.dao.ProductRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserEventRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserStatsRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;
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
	private int user1Id;
	private int product1Id;
	
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
		RestAssured.port = serverPort;
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
			.body("category.name", is("Bricolage"));
	}
	
}
