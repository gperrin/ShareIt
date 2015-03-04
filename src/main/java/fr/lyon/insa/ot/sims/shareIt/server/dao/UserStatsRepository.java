package fr.lyon.insa.ot.sims.shareIt.server.dao;

import org.springframework.data.repository.CrudRepository;

import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;

public interface UserStatsRepository extends CrudRepository<UserStats, Integer> {

}
