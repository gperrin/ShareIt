package fr.lyon.insa.ot.sims.shareIt.server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
