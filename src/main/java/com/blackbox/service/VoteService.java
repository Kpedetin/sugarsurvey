package com.blackbox.service;

import com.blackbox.dao.model.Vote;
import com.blackbox.dto.VoteDto;
import java.util.Map;
import org.springframework.stereotype.Service;


public interface VoteService {
	void addVote(VoteDto voteDto);
	Map<String,Object> readWinner(String idElection);
}
