package com.daou.moyeo.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ScheduleDTO {
	
	@JsonProperty("groupNo")
	private int groupNo;
	@JsonProperty("memberNo")
	private int memberNo;
	@JsonProperty("scheduleInfos")
	private List<ScheduleInfo> scheduleInfos = new ArrayList<ScheduleInfo>();
	
	public int getGroupNo() {
		return groupNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public List<ScheduleInfo> getScheduleInfos() {
		return scheduleInfos;
	}

	public static class ScheduleInfo{
		private int scheduleDay;
		private int scheduleStartTime;
		private int scheduleFinishTime;
		
		public int getScheduleDay() {
			return scheduleDay;
		}
		public int getScheduleStartTime() {
			return scheduleStartTime;
		}
		public int getScheduleFinishTime() {
			return scheduleFinishTime;
		}
	
	}

}
