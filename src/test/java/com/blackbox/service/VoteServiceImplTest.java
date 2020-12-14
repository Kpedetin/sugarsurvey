package com.blackbox.service;

import com.blackbox.dao.model.Vote;
import com.blackbox.utility.Grade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoteServiceImplTest {

	private final List<Vote> votes = new ArrayList<>();

	@BeforeEach
	public void setUp() {

		HashMap<String, Grade> appreciation1 = new HashMap<>();
		HashMap<String, Grade> appreciation2 = new HashMap<>();
		HashMap<String, Grade> appreciation3 = new HashMap<>();
		HashMap<String, Grade> appreciation4 = new HashMap<>();
		HashMap<String, Grade> appreciation5 = new HashMap<>();

		appreciation1.put("VGE", Grade.EXCELLENT);
		appreciation1.put("FLAMBY", Grade.ACCEPTABLE);
		appreciation1.put("MACRON", Grade.POOR);

		appreciation2.put("VGE", Grade.GOOD);
		appreciation2.put("FLAMBY", Grade.ACCEPTABLE);
		appreciation2.put("MACRON", Grade.EXCELLENT);

		appreciation3.put("VGE", Grade.ACCEPTABLE);
		appreciation3.put("FLAMBY", Grade.EXCELLENT);
		appreciation3.put("MACRON", Grade.REJECT);

		appreciation4.put("VGE", Grade.GOOD);
		appreciation4.put("FLAMBY", Grade.REJECT);
		appreciation4.put("MACRON", Grade.POOR);

		appreciation5.put("VGE", Grade.POOR);
		appreciation5.put("FLAMBY", Grade.EXCELLENT);
		appreciation5.put("MACRON", Grade.GOOD);

		Vote vote1 = Vote.builder().electionId("1").appreciation(appreciation1).build();
		Vote vote2 = Vote.builder().electionId("1").appreciation(appreciation2).build();
		Vote vote3 = Vote.builder().electionId("1").appreciation(appreciation3).build();
		Vote vote4 = Vote.builder().electionId("1").appreciation(appreciation4).build();
		Vote vote5 = Vote.builder().electionId("1").appreciation(appreciation5).build();

		votes.add(vote1);
		votes.add(vote2);
		votes.add(vote3);
		votes.add(vote4);
		votes.add(vote5);

	}

	@Test
	void extractVoteByCandidateTestOk() {

		HashMap<String, HashMap<Grade, Integer>> aggregatedVotesbycandidates =
				VoteServiceImpl.extractVoteByCandidate(votes);
		Set<String> originalCandidates = new HashSet<>();
		originalCandidates.add("VGE");
		originalCandidates.add("FLAMBY");
		originalCandidates.add("MACRON");
		Set<String> candidates = aggregatedVotesbycandidates.keySet();
		Assertions.assertEquals(candidates, originalCandidates);

		HashMap<Grade, Integer> vgeAggregatedVote = new HashMap<>();
		vgeAggregatedVote.put(Grade.EXCELLENT, 1);
		vgeAggregatedVote.put(Grade.GOOD, 2);
		vgeAggregatedVote.put(Grade.POOR, 1);
		vgeAggregatedVote.put(Grade.ACCEPTABLE, 1);

		Assertions.assertEquals(aggregatedVotesbycandidates.get("VGE"), vgeAggregatedVote);
	}


	@Test
	void WinnerTestOneCandidateForHighestMedian() {
		HashMap<String, HashMap<Grade, Integer>> aggregatedVotesbycandidates =
				VoteServiceImpl.extractVoteByCandidate(votes);
		HashMap<String, HashMap<String, Object>> listOfCandidateStatistic = new HashMap<>();
		aggregatedVotesbycandidates.keySet().forEach(candidate -> {
			try {
				listOfCandidateStatistic.put(candidate,
						VoteServiceImpl.computeStatisticByCandidate(aggregatedVotesbycandidates.get(candidate)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Assertions.assertEquals("VGE", VoteServiceImpl.computeWinner(listOfCandidateStatistic));
	}
}



