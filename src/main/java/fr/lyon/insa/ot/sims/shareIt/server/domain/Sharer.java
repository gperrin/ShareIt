package fr.lyon.insa.ot.sims.shareIt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
public class Sharer {
	@Id
	@GeneratedValue
	private int id;
	private String lastname;
	private String firstname;
	private int age;
	private float rating;
	
	

}
