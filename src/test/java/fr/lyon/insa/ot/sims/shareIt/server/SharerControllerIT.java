package fr.lyon.insa.ot.sims.shareIt.server;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import org.hamcrest.core.*;

import java.util.Date;

import org.apache.http.HttpStatus;
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

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserEventRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserStatsRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;
import fr.lyon.insa.ot.sims.shareIt.server.domain.builders.SharerBuilder;
import fr.lyon.insa.ot.sims.shareIt.server.domain.builders.UserStatsBuilder;
import fr.lyon.insa.ot.sims.shareIt.server.domain.events.UserCreatedEvent;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SharerControllerIT {
	 
	@Value("${local.server.port}")
	private int serverPort;
	
	@Autowired
	SharerRepository sharerRepository;
	@Autowired
	UserStatsRepository userStatsRepository;
	@Autowired
	UserEventRepository userEventRepository;
	private int user1Id;
	private int user2Id;
	 @Before
	  public void setUp() {
		 userEventRepository.deleteAll();
		 sharerRepository.deleteAll();
		 userStatsRepository.deleteAll(); 
		 UserStats userStats = new UserStatsBuilder().build();
		 userStats = this.userStatsRepository.save(userStats);
		 UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
		 userCreatedEvent.setDate(new Date());
		 Sharer sharer = new SharerBuilder().firstname("Kenny")
				 .lastname("McCormik").age(12).postCode(69100)
				 .userStats(userStats).sex('M').profilePicture(null)
				 .profilePictureType(null).rating(0).telephone("").build();
		 sharer = this.sharerRepository.save(sharer);
		 user1Id= sharer.getId();
		 userCreatedEvent.setUser(sharer);
		 this.userEventRepository.save(userCreatedEvent);
		 UserStats userStats2 = new UserStats();
		 userStats2 = userStatsRepository.save(userStats2 );
		 UserCreatedEvent userCreatedEvent2 = new UserCreatedEvent();
		 userCreatedEvent2.setDate(new Date());
		 Sharer sharer2 = new SharerBuilder().firstname("Jean")
				 .lastname("Paul")
				 .age(24)
				 .postCode(42100)
				 .userStats(userStats2)
				 .sex('M')
				 .profilePicture(null)
				 .profilePictureType(null)
				 .rating(0)
				 .telephone("")
				 .build();
		 sharer2 = this.sharerRepository.save(sharer2);
		 user2Id= sharer2.getId();
		 userCreatedEvent2.setUser(sharer2);
		 this.userEventRepository.save(userCreatedEvent2);
		 RestAssured.port = serverPort;
	  }
	 
	 @Test
	 public void createUserShouldReturnSavedUser(){
		 given()
		 	.param("firstname","Jean")
		 	.param("lastname", "Valjean")
		 	.param("postcode", 69100)
		 	.param("age", 25)
		 .when()
		 	.post("/user")
		 .then()
		 	.statusCode(HttpStatus.SC_CREATED)
		 	.body("firstname", is("Jean"))
		 	.body("lastname", is("Valjean"))
		 	.body("postCode", is(69100))
		 	.body("age", is(25));
	 }
	 @Test
	 public void getUserShouldReturnSavedUser ( ){
		 when()
		 	.get("/user/"+user1Id)
	 	.then()
	 		.statusCode(HttpStatus.SC_OK)
		 	.body("firstname",is("Kenny"))
		 	.body("lastname",is("McCormik"))
		 	.body("age", is(12))
		 	.body("postCode", is (69100 ))
		 	.body("id", is(user1Id));
	 }
	 @Test
	 public void updateUserShouldReturnSavedUser(){
		 given()
		 	.param("phone","0123456789")
		 .when()
		 	.put("/user/"+user2Id)
		 .then()
		 	.statusCode(HttpStatus.SC_OK)
		 	.body( "firstname", is("Jean"))
		 	.body("lastname",is("Paul"))
		 	.body("age", is(24))
		 	.body("postCode", is(42100))
		 	.body("telephone", is("0123456789"))
		 	.body("sex", is("M"));
	 }
}
