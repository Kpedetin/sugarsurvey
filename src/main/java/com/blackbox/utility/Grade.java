package com.blackbox.utility;


public enum Grade {
	EXCELLENT,
	VERYGOOD,
	GOOD,
	ACCEPTABLE,
	POOR,
	REJECT;


	public static Grade convertGradeFromString(String value) throws Exception {
		Grade grade;
		switch (value) {
			case "E":
				grade = Grade.EXCELLENT;
				break;
			case "V":
				grade = Grade.VERYGOOD;
				break;
			case "G":
				grade = Grade.GOOD;
				break;
			case "A":
				grade = Grade.ACCEPTABLE;
				break;
			case "P":
				grade = Grade.POOR;
				break;
			case "R":
				grade = Grade.REJECT;
				break;
			default:
				throw new Exception("Unknown grade : " + value);
		}
		return grade;
	}
}
