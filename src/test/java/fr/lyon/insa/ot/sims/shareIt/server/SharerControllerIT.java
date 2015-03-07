package fr.lyon.insa.ot.sims.shareIt.server;

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

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SharerControllerIT {
	 
	@Value("${local.server.port}")
	private int serverPort;
	
	@Autowired
	SharerRepository sharerRepository;
	
	 @Before
	  public void setUp() {
	    //sharerRepository.deleteAll();
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
}
