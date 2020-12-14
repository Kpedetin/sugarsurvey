package com.blackbox.controller;


import com.blackbox.dao.model.Candidate;
import com.blackbox.dto.CandidateDto;
import com.blackbox.dto.ElectionDto;
import com.blackbox.service.ElectionAndCandidateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/election")
public class ElectionController {

	@Autowired
	private ElectionAndCandidateService electionAndCandidateService;

	@PostMapping("/createElection")
	@ResponseStatus(HttpStatus.OK)
	public String createElection(@RequestBody ElectionDto electionDto) {
		return electionAndCandidateService.createElection(electionDto);
	}

	@PutMapping("/addCandidate/{idElection}")
	@ResponseStatus(HttpStatus.CREATED)
	public void addCandidateToElection(@PathVariable("idElection") String idElection,
			@RequestBody CandidateDto candidateDto) throws Exception {
		electionAndCandidateService.addCandidateToElection(candidateDto, idElection);
	}

	@GetMapping("/readCandidates/{idElection}")
	@ResponseStatus(HttpStatus.OK)
	public List<Candidate> readAllCandidateForElection(@PathVariable("idElection") String idElection) {
		return electionAndCandidateService.readCandidateByElection(idElection);
	}
}


