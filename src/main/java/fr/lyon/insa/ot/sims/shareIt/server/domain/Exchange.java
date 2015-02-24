package fr.lyon.insa.ot.sims.shareIt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	private ExchangeStatus status;
	
	
	
	public Exchange(){
		
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((borrower == null) ? 0 : borrower.hashCode());
		result = prime * result + id;
		result = prime * result + ((lender == null) ? 0 : lender.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		if (id != other.id)
			return false;
		if (lender == null) {
			if (other.lender != null)
				return false;
		} else if (!lender.equals(other.lender))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	
	
}
