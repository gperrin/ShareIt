package fr.lyon.insa.ot.sims.shareIt.server.services;


import java.util.Collection;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

public interface ISharerService {

	public Sharer createUser(String lastName, String firstName, int postCode);
	public Sharer getUser (int sharerId);
	public boolean updateUser (Sharer sharer);
	public Collection<Sharer> getSharers(int postcode);
	
	
}
