package com.blackbox.dao.model;

import com.blackbox.utility.Grade;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Vote {

	private String electionId;
	private HashMap<String, Grade> appreciation;
}
