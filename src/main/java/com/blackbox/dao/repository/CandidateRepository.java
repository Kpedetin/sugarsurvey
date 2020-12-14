package com.blackbox.dao.repository;

import com.blackbox.dao.model.Candidate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepository extends MongoRepository<Candidate, String> {

	List<Candidate> findByElectionId(String electionId);
}
