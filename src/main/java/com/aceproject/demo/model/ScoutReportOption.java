package com.aceproject.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoutReportOption {
	
	private String teamCode;
	private boolean costUp;
	
	public ScoutReportOption(String teamCode, boolean costUp) {
		this.teamCode = teamCode;
		this.costUp = costUp;
	}
	
	// 옵션 선택 x
	public static final ScoutReportOption DEFAULT_OPTION = new ScoutReportOption(null, false);
	

}
