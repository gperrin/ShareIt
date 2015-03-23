package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Repository
public interface SharerRepository extends CrudRepository<Sharer, Integer>{
	
	public Collection<Sharer> findByPostCode(int postCode);
	@Query(value = "select distinct * from sharer s inner join sharer t on ST_DWithin(s.location, t.location,?2) and s.id != t.id and s.id=?1 order by st_distance(s.location, t.location)", nativeQuery=true)
	public Collection<Sharer> findNearbySharers(int sharerId, int distance);
}
