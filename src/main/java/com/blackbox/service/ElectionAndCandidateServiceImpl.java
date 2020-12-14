package com.blackbox.service;

import com.blackbox.dao.model.Candidate;
import com.blackbox.dao.model.Election;
import com.blackbox.dao.repository.CandidateRepository;
import com.blackbox.dao.repository.ElectionRepository;
import com.blackbox.dto.CandidateDto;
import com.blackbox.dto.ElectionDto;
import com.blackbox.utility.Mapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionAndCandidateServiceImpl implements ElectionAndCandidateService {

	@Autowired
	private ElectionRepository electionRepository;
	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	// Json API compliancy need to be implement
	public String createElection(ElectionDto election) {
		Election insert = electionRepository.insert(Mapping.mapElectionDtoToElection(election));
		return insert.getId();
	}

	@Override
	public void addCandidateToElection(CandidateDto candidateDto, String electionId) throws Exception {
		Candidate candidate = electionRepository.findByIdAndOpenedToVoteFalse(electionId)
		                                        .map(selectedElection -> Mapping.mapToCandidate(candidateDto,
				                                        selectedElection))
		                                        .orElseThrow(() -> new Exception(
				                                        "No Election opened or found with " + "mentioned identifier"));

		candidateRepository.save(candidate);
	}


	@Override
	public List<Candidate> readCandidateByElection(String electionId) {
		return candidateRepository.findByElectionId(electionId);
	}

}
