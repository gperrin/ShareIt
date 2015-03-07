package fr.lyon.insa.ot.sims.shareIt.server.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.lyon.insa.ot.sims.shareIt.server.beans.DateToStringSerializer;

@Entity
public class Exchange {

	@GeneratedValue
	@Id
	int id;
	
	@ManyToOne
	private Sharer lender;
	@ManyToOne
	private Sharer borrower;
	@ManyToOne
	private Product product;
	private double lenderRating;
	private double borrowerRating;
	private ExchangeStatus status;
	@JsonSerialize(using = DateToStringSerializer.class)
	private Date startDate;
	@JsonSerialize(using = DateToStringSerializer.class)
	private Date endDate;
	
	public Exchange(){
		
	}



	public void setId(int id) {
		this.id = id;
	}



	public Sharer getLender() {
		return lender;
	}



	public void setLender(Sharer lender) {
		this.lender = lender;
	}



	public Sharer getBorrower() {
		return borrower;
	}



	public void setBorrower(Sharer borrower) {
		this.borrower = borrower;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public ExchangeStatus getStatus() {
		return status;
	}



	public void setStatus(ExchangeStatus status) {
		this.status = status;
	}



	public int getId() {
		return id;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public double getLenderRating() {
		return lenderRating;
	}



	public void setLenderRating(double lenderRating) {
		this.lenderRating = lenderRating;
	}



	public double getBorrowerRating() {
		return borrowerRating;
	}



	public void setBorrowerRating(double borrowerRating) {
		this.borrowerRating = borrowerRating;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((borrower == null) ? 0 : borrower.hashCode());
		long temp;
		temp = Double.doubleToLongBits(borrowerRating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((lender == null) ? 0 : lender.hashCode());
		temp = Double.doubleToLongBits(lenderRating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Exchange))
			return false;
		Exchange other = (Exchange) obj;
		if (borrower == null) {
			if (other.borrower != null)
				return false;
		} else if (!borrower.equals(other.borrower))
			return false;
		if (Double.doubleToLongBits(borrowerRating) != Double
				.doubleToLongBits(other.borrowerRating))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (lender == null) {
			if (other.lender != null)
				return false;
		} else if (!lender.equals(other.lender))
			return false;
		if (Double.doubleToLongBits(lenderRating) != Double
				.doubleToLongBits(other.lenderRating))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status != other.status)
			return false;
		return true;
	}



	
	
	
}
