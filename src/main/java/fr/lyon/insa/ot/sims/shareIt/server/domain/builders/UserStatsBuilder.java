package fr.lyon.insa.ot.sims.shareIt.server.domain.builders;

import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;

public class UserStatsBuilder {

	private UserStats userStats;
	
	public UserStatsBuilder id ( int id ){
		userStats.setId(id);
		return this;
	}
	public UserStatsBuilder nbLended ( int nbLended ){
		userStats.setNbLended(nbLended);
		return this;
	}
	public UserStatsBuilder nbBorrowed ( int nbBorrowed ){
		userStats.setNbBorrowed(nbBorrowed);
		return this;
	}
	public UserStatsBuilder notes01 ( int notes01 ){
		userStats.setNotes01(notes01);
		return this;
	}
	public UserStatsBuilder notes12 ( int notes12 ){
		userStats.setNotes12(notes12);
		return this;
	}
	public UserStatsBuilder notes23 ( int notes23 ){
		userStats.setNotes23(notes23);
		return this;
	}
	public UserStatsBuilder notes34 ( int notes34 ){
		userStats.setNotes34(notes34);
		return this;
	}
	public UserStatsBuilder notes45 ( int notes45 ){
		userStats.setNotes45(notes45);
		return this;
	}
	public UserStatsBuilder averageNote ( double averageNote){
		userStats.setAverageNote(averageNote);
		return this;
	}
	public UserStatsBuilder nbObjectReturned ( int nbObjectReturned ){
		userStats.setNbObjectReturned(nbObjectReturned);
		return this;
	}
	public UserStats build ( ){
		return userStats;
	}
}
