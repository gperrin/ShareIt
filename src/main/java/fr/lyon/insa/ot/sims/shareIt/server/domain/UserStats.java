package fr.lyon.insa.ot.sims.shareIt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserStats {
	@Id
	@GeneratedValue
	private int id;
	
	private int nbLended;
	private int nbBorrowed;
	private int notes01;
	private int notes12;
	private int notes23;
	private int notes34;
	private int notes45;
	private double averageNote;
	private int nbObjectReturned;
	
	public UserStats (){
		this.averageNote=0;
		this.nbBorrowed=0;
		this.nbLended=0;
		this.nbObjectReturned=0;
		this.notes01=0;
		this.notes12=0;
		this.notes23=0;
		this.notes34=0;
		this.notes45=0;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getNbLended() {
		return nbLended;
	}

	public void setNbLended(int nbLended) {
		this.nbLended = nbLended;
	}

	public int getNbBorrowed() {
		return nbBorrowed;
	}

	public void setNbBorrowed(int nbBorrowed) {
		this.nbBorrowed = nbBorrowed;
	}

	public int getNotes01() {
		return notes01;
	}

	public void setNotes01(int notes01) {
		this.notes01 = notes01;
	}

	public int getNotes12() {
		return notes12;
	}

	public void setNotes12(int notes12) {
		this.notes12 = notes12;
	}

	public int getNotes23() {
		return notes23;
	}

	public void setNotes23(int notes23) {
		this.notes23 = notes23;
	}

	public int getNotes34() {
		return notes34;
	}

	public void setNotes34(int notes34) {
		this.notes34 = notes34;
	}

	public int getNotes45() {
		return notes45;
	}

	public void setNotes45(int notes45) {
		this.notes45 = notes45;
	}

	public double getAverageNote() {
		return averageNote;
	}

	public void setAverageNote(double averageNote) {
		this.averageNote = averageNote;
	}

	public int getNbObjectReturned() {
		return nbObjectReturned;
	}

	public void setNbObjectReturned(int nbObjectReturned) {
		this.nbObjectReturned = nbObjectReturned;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(averageNote);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + nbBorrowed;
		result = prime * result + nbLended;
		result = prime * result + nbObjectReturned;
		result = prime * result + notes01;
		result = prime * result + notes12;
		result = prime * result + notes23;
		result = prime * result + notes34;
		result = prime * result + notes45;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserStats))
			return false;
		UserStats other = (UserStats) obj;
		if (Double.doubleToLongBits(averageNote) != Double
				.doubleToLongBits(other.averageNote))
			return false;
		if (id != other.id)
			return false;
		if (nbBorrowed != other.nbBorrowed)
			return false;
		if (nbLended != other.nbLended)
			return false;
		if (nbObjectReturned != other.nbObjectReturned)
			return false;
		if (notes01 != other.notes01)
			return false;
		if (notes12 != other.notes12)
			return false;
		if (notes23 != other.notes23)
			return false;
		if (notes34 != other.notes34)
			return false;
		if (notes45 != other.notes45)
			return false;
		return true;
	}
}
