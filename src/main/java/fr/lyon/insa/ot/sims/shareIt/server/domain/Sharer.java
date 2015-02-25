package fr.lyon.insa.ot.sims.shareIt.server.domain;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.http.MediaType;


@Entity
public class Sharer {
	@Id
	@GeneratedValue()
	private int id;

	private byte[] profilePicture;
	private MediaType proFilePictureType;
	private String lastname;
	private String firstname;
	private int age;
	private char sex;
	private float rating;
	private int postCode;
	private String telephone;
	
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
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + postCode;
		result = prime
				* result
				+ ((proFilePictureType == null) ? 0 : proFilePictureType
						.hashCode());
		result = prime * result + Arrays.hashCode(profilePicture);
		result = prime * result + Float.floatToIntBits(rating);
		result = prime * result + sex;
		result = prime * result
				+ ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Sharer))
			return false;
		Sharer other = (Sharer) obj;
		if (age != other.age)
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id != other.id)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (postCode != other.postCode)
			return false;
		if (proFilePictureType == null) {
			if (other.proFilePictureType != null)
				return false;
		} else if (!proFilePictureType.equals(other.proFilePictureType))
			return false;
		if (!Arrays.equals(profilePicture, other.profilePicture))
			return false;
		if (Float.floatToIntBits(rating) != Float.floatToIntBits(other.rating))
			return false;
		if (sex != other.sex)
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}
	
	

}
