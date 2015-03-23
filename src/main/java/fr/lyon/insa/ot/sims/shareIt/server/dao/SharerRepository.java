package fr.lyon.insa.ot.sims.shareIt.server.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Repository
public interface SharerRepository extends CrudRepository<Sharer, Integer>{
	
	public Collection<Sharer> findByPostCode(int postCode);
	@Query(value = "select t.id, t.age, t.firstname, t.lastname, t.location, t.post_code, t.pro_file_picture_type, t.profile_picture, t.rating, t.sex, t.telephone, t.user_stats_id from sharer s inner join sharer t on ST_DWithin(s.location, t.location,?2) and s.id != t.id and s.id=?1 order by st_distance(s.location, t.location)", nativeQuery=true)
	public List<Sharer> findNearbySharers(int sharerId, double distance);
}
