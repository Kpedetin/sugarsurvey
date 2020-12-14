package com.blackbox.dao.repository;

import com.blackbox.dao.model.Election;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ElectionRepository extends MongoRepository<Election, String> {

	Optional<Election> findByIdAndOpenedToVoteFalse(String electionId);
}
