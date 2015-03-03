package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Entity
@DiscriminatorValue("user_rated")
public class UserRatedEvent extends UserEvent{
	
	private double rating;
	@ManyToOne
	private Sharer rater;
	
	public UserRatedEvent (){
		
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Sharer getRater() {
		return rater;
	}

	public void setRater(Sharer rater) {
		this.rater = rater;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((rater == null) ? 0 : rater.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof UserRatedEvent))
			return false;
		UserRatedEvent other = (UserRatedEvent) obj;
		if (rater == null) {
			if (other.rater != null)
				return false;
		} else if (!rater.equals(other.rater))
			return false;
		if (Double.doubleToLongBits(rating) != Double
				.doubleToLongBits(other.rating))
			return false;
		return true;
	}
	
}
