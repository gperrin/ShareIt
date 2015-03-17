package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.ProductCategory;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
	public Collection<Product> findBySharer(Sharer sharer);
	public Collection<Product> findByCategory(ProductCategory category);	
	@Query(value = "SELECT * FROM Product WHERE difference(name,?1) > 2", nativeQuery = true)
	public Collection<Product> findByNameLike( String name);   


}
