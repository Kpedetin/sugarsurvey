package com.blackbox.controller;


import com.blackbox.dto.VoteDto;
import com.blackbox.service.VoteService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vote")
public class VoteController {

	@Autowired
	private VoteService voteService;

	@PostMapping("/addVote/{idElection}")
	@ResponseStatus(HttpStatus.CREATED)
	public void addVote(@PathVariable(value = "idElection") String idElection, @RequestBody VoteDto voteDto) {
		voteDto.setElectionId(idElection);
		voteService.addVote(voteDto);
	}

	@GetMapping("/readResult/{idElection}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> readWinner(@PathVariable(value = "idElection") String idElection) {
		return voteService.readWinner(idElection);
	}

}
