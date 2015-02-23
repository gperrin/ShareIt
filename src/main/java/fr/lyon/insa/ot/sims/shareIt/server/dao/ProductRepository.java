package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
	public Collection<Product> findBySharer(Sharer sharer);
}
