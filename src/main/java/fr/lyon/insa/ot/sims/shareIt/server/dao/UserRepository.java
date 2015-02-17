package fr.lyon.insa.ot.sims.shareIt.server.dao;

import org.springframework.data.repository.CrudRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
