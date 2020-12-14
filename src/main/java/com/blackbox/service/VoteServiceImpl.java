package com.blackbox.service;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.blackbox.dao.model.Vote;
import com.blackbox.dao.repository.VoteRepository;
import com.blackbox.dto.VoteDto;
import com.blackbox.utility.Grade;
import com.blackbox.utility.Mapping;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Override
	public void addVote(VoteDto voteDto) {
		voteRepository.insert(Mapping.mapVoteDtoToVote(voteDto));
	}

	@Override
	public Map<String, Object> readWinner(String idElection) {
		return computeResult(idElection);
	}

	private List<Vote> readAllVotesByIdElection(String idElection) {
		Vote queryVote = Vote.builder().electionId(idElection).build();
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("electionId", exact());
		return voteRepository.findAll(Example.of(queryVote, matcher));
	}

	public Map<String, Object> computeResult(String electionId) {
		Map<String, Object> result = new HashMap<>();
		List<Vote> allVotesByElection = readAllVotesByIdElection(electionId);

		HashMap<String, HashMap<Grade, Integer>> votesByCandidate = extractVoteByCandidate(allVotesByElection);
		HashMap<String, HashMap<String, Object>> statisticByCandidate = computeStatisticForAllCandidates(votesByCandidate);
		String winner = computeWinner(statisticByCandidate);

		result.put("WINNER", winner);
		result.put("STATISTICS", statisticByCandidate);
		result.put("VOTES", votesByCandidate);
		return result;
	}


	public static HashMap<String, HashMap<Grade, Integer>> extractVoteByCandidate(List<Vote> vote) {

		HashMap<String, HashMap<Grade, Integer>> aggregatedVoteByCandidate = new HashMap<>();
		vote.forEach(currentVote -> {
			HashMap<String, Grade> aVote = currentVote.getAppreciation();
			Set<String> candidates = aVote.keySet();
			candidates.forEach(candidate -> {
				HashMap<Grade, Integer> count;
				count = aggregatedVoteByCandidate.containsKey(candidate) ? aggregatedVoteByCandidate.get(candidate)
						: new HashMap<>();
				Grade grade = aVote.get(candidate);
				count.put(grade, ofNullable(count.get(grade)).map(e -> e + 1).orElse(1));
				aggregatedVoteByCandidate.put(candidate, count);
			});
		});

		return aggregatedVoteByCandidate;
	}

	public static HashMap<String, HashMap<String, Object>> computeStatisticForAllCandidates(
			HashMap<String, HashMap<Grade, Integer>> aggregatedVoteByCandidate) {
		HashMap<String, HashMap<String, Object>> allStatistics = new HashMap<>();
		aggregatedVoteByCandidate.keySet().forEach(candidate -> {
			try {
				allStatistics.put(candidate, computeStatisticByCandidate(aggregatedVoteByCandidate.get(candidate)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return allStatistics;
	}

	public static HashMap<String, Object> computeStatisticByCandidate(Map<Grade, Integer> score) throws Exception {

		Grade medianGrade = null;
		long belowMedian = 0L;
		long aboveMedian = 0L;
		long cumulPercentage = 0L;

		Integer numberOfVoters = score.values()
		                              .stream()
		                              .reduce(Integer::sum)
		                              .orElseThrow(() -> new Exception("Number of voter is null"));

		for (Grade currentGrade : Grade.values()) {
			if (score.containsKey(currentGrade)) {
				long percentageVote = score.get(currentGrade) * 100 / numberOfVoters;
				if (cumulPercentage + percentageVote >= 50.00) {
					if (medianGrade != null) {
						aboveMedian += percentageVote;
					} else {
						medianGrade = currentGrade;
					}
				} else {
					belowMedian += percentageVote;
				}
				cumulPercentage += percentageVote;
			}
		}

		HashMap<String, Object> statistic = new HashMap<>();
		statistic.put("MEDIAN", medianGrade);
		statistic.put("BELOWMEDIAN", belowMedian);
		statistic.put("ABOVEMEDIAN", aboveMedian);
		return statistic;

	}

	public static String computeWinner(HashMap<String, HashMap<String, Object>> allStatistic) {
		HashMap<Grade, Set<String>> candidatesSortedByGrade = new HashMap<>();
		Set<String> setOfCompetedCandidate;

		for (String candidate : allStatistic.keySet()) {
			Grade medianCandidateGrade = (Grade) allStatistic.get(candidate).get("MEDIAN");
			setOfCompetedCandidate =
					candidatesSortedByGrade.containsKey(medianCandidateGrade) ? candidatesSortedByGrade.get(medianCandidateGrade)
							: new HashSet<>();
			setOfCompetedCandidate.add(candidate);
			candidatesSortedByGrade.put(medianCandidateGrade, setOfCompetedCandidate);
		}

		List<Map.Entry<Grade, Set<String>>> orderedGradeByCandidates = candidatesSortedByGrade.entrySet()
		                                                                                      .stream()
		                                                                                      .sorted(Map.Entry.comparingByKey(Comparator
				                                                                                      .comparingInt(Enum::ordinal)))
		                                                                                      .collect(Collectors.toList());

		Set<String> value = orderedGradeByCandidates.get(0).getValue();
		String winner = null;
		if (value.size() == 1) {
			Iterator<String> iterator = value.iterator();
			winner = iterator.next();
		} else {
			long minBelowMedianGradepercentage = 100L;
			//This is not the right rule to dispatch candidates who is competiting in the same grade
			for (String candidate : value) {
				Long belowMedian = (Long) allStatistic.get(candidate).get("BELOWMEDIAN");
				if (minBelowMedianGradepercentage > belowMedian) {
					winner = candidate;
					minBelowMedianGradepercentage = belowMedian;
				}
			}
		}
		return winner;

	}

}
