package com.blackbox.utility;

import com.blackbox.dao.model.Candidate;
import com.blackbox.dao.model.Election;
import com.blackbox.dao.model.Question;
import com.blackbox.dao.model.User;
import com.blackbox.dao.model.Vote;
import com.blackbox.dto.CandidateDto;
import com.blackbox.dto.ElectionDto;
import com.blackbox.dto.QuestionDto;
import com.blackbox.dto.UserDto;
import com.blackbox.dto.VoteDto;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class Mapping {

	public static User mapUserDtoToUser(UserDto userDto) {
		return User.builder()
		           .firstName(userDto.getFirstName())
		           .lastName(userDto.getLastName())
		           .mail(userDto.getMail())
		           .password(userDto.getPassword())
		           .build();

	}

	public static UserDto mapUserToUserDto(User user) {
		return UserDto.builder().firstName(user.getFirstName()).lastName(user.getLastName()).mail(user.getMail()).build();
	}

	public static Question mapQuestionDtoToQuestion(QuestionDto questionDto) {
		return Question.builder()
		               .label(questionDto.getLabelQuestion())
		               .proposals(questionDto.getProposals())
		               .rightAnswers(questionDto.getRightAnswers())
		               .detail(questionDto.getDetail())
		               .categoryId(questionDto.getCategoryId())
		               .build();
	}

	public static QuestionDto mapQuestionToQuestionDto(Question question) {
		return QuestionDto.builder()
		                  .categoryId(question.getCategoryId())
		                  .labelQuestion(question.getLabel())
		                  .proposals(question.getProposals())
		                  .rightAnswers(question.getRightAnswers())
		                  .detail(question.getDetail())
		                  .build();

	}

	public static VoteDto mapVoteToVoteDto(Vote vote) {
		return VoteDto.builder().electionId(vote.getElectionId())
		              //  .appreciation(vote.getAppreciation())
		              .build();
	}

	public static Vote mapVoteDtoToVote(VoteDto voteDto) {

		HashMap<String, Grade> appreciations = new HashMap<>();

		voteDto.getAppreciation().forEach((key, value) -> {
			try {
				appreciations.put(key, Grade.convertGradeFromString(value));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return Vote.builder().electionId(voteDto.getElectionId()).appreciation(appreciations).build();
	}

	public static Election mapElectionDtoToElection(ElectionDto electionDto) {
		return Election.builder().name(electionDto.getName()).description(electionDto.getDescription()).build();

	}

	public static Candidate mapCandidateDtoToCandidate(CandidateDto candidateDto) {
		return Candidate.builder()
		                .name(candidateDto.getName())
		                .description(candidateDto.getDescription())
		                .link(candidateDto.getLink())
		                .build();
	}

	public static Candidate mapToCandidate(CandidateDto candidateDto, Election election) {
		return Candidate.builder()
		                .electionId(election.getId())
		                .name(candidateDto.getName())
		                .description(candidateDto.getDescription())
		                .link(candidateDto.getLink())
		                .build();

	}
}
