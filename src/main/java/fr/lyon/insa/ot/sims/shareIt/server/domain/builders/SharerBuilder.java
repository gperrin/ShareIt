package fr.lyon.insa.ot.sims.shareIt.server.domain.builders;

import org.springframework.http.MediaType;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;

public class SharerBuilder {
	private Sharer sharer = new Sharer();
	
	public SharerBuilder id ( int id ){
		sharer.setId(id);
		return this;
	}
	
	public SharerBuilder profilePicture (byte[] profilePicture){
		sharer.setProfilePicture(profilePicture);
		return this;
	}
	public SharerBuilder profilePictureType ( MediaType profilePictureType){
		sharer.setProFilePictureType(profilePictureType);
		return this;
	}
	public SharerBuilder lastname( String lastname){
		sharer.setLastname(lastname);
		return this;
	}
	public SharerBuilder firstname( String firstname){
		sharer.setFirstname(firstname);
		return this;
	}
	public SharerBuilder age( int age){
		sharer.setAge(age);
		return this;
	}
	public SharerBuilder sex ( char sex){
		sharer.setSex(sex);
		return this;
	}
	public SharerBuilder rating ( float rating){
		sharer.setRating(rating);
		return this;
	}
	public SharerBuilder postCode ( int postCode){
		sharer.setPostCode(postCode);
		return this;
	}
	public SharerBuilder telephone ( String telephone ){
		sharer.setTelephone(telephone);
		return this;
	}
	public SharerBuilder userStats ( UserStats userStats){
		sharer.setUserStats(userStats);
		return this;
	}
	public Sharer build(){
		return sharer;
	}
}
