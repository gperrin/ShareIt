package fr.lyon.insa.ot.sims.shareIt.server.domain.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import fr.lyon.insa.ot.sims.shareIt.server.beans.IEventProcessor;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;


@Entity
@DiscriminatorValue("product_deleted")
public class ProductDeletedEvent extends UserEvent{

	@OneToOne
	Product product;
	
	public ProductDeletedEvent (){
		
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ProductDeletedEvent))
			return false;
		ProductDeletedEvent other = (ProductDeletedEvent) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public void processEvent(IEventProcessor eventProcessor) {
		eventProcessor.processProductDeletedEvent(this);
	}

	
}
