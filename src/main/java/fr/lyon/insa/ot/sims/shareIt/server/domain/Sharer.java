package fr.lyon.insa.ot.sims.shareIt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.http.MediaType;


@Entity
public class Sharer {
	@Id
	@GeneratedValue
	private int id;

	private byte[] profilePicture;
	private MediaType proFilePictureType;
	private String lastname;
	private String firstname;
	private int age;
	private float rating;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	public MediaType getProFilePictureType() {
		return proFilePictureType;
	}
	public void setProFilePictureType(MediaType proFilePictureType) {
		this.proFilePictureType = proFilePictureType;
	}
	
	

}
