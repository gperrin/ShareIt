package fr.lyon.insa.ot.sims.shareIt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue
	private int id;
	String name;
	String description;

	@ManyToOne
	Sharer sharer;
	ProductStatus status;
	@ManyToOne
	ProductCategory category;
	
	public Product(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String nom) {
		this.name = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Sharer getSharer() {
		return sharer;
	}

	public void setSharer(Sharer sharer) {
		this.sharer = sharer;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sharer == null) ? 0 : sharer.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (category != other.category)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sharer == null) {
			if (other.sharer != null)
				return false;
		} else if (!sharer.equals(other.sharer))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
}
