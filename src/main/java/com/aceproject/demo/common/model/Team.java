package com.aceproject.demo.common.model;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team extends Dto {
	private int teamId;
	private String teamName;
	private DateTime lastRefreshTime;
}
