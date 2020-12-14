package com.blackbox.service;

import com.blackbox.dao.model.Candidate;
import com.blackbox.dto.CandidateDto;
import com.blackbox.dto.ElectionDto;
import java.util.List;

public interface ElectionAndCandidateService {

	String createElection(ElectionDto election);

	void addCandidateToElection(CandidateDto candidate, String idElection) throws Exception;

	List<Candidate> readCandidateByElection(String electionId);

}
