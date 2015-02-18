package fr.lyon.insa.ot.sims.shareIt.server.services;


import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface ISharerService {

	public Sharer createUser(String lastName, String firstName, int postCode);
	public Sharer getUser (int sharerId);
	public boolean updateUser (Sharer sharer);
	
}
