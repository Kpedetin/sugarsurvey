package com.blackbox.dao.repository;

import com.blackbox.dao.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository<Vote, String> {

}
