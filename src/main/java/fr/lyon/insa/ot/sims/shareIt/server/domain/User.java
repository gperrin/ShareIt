package fr.lyon.insa.ot.sims.shareIt.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	private int id;
	private String lastname;
	private String firstname;
	private int age;
	private float rating;

}
