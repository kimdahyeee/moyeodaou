/*
 * Author : Daeho Han
 * Date : 2017.06.15
 * Title : CalculateSchedule 
 * Contents : Observable 클래스를 상속 받아 가능한 날짜를 계산하고 Observer들에게 notify 하는 클래스
 */

package com.daou.moyeo.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.daou.moyeo.schedule.service.ScheduleService;

public class CalculateSchedule {
	
	
	private ScheduleService scheduleService;
	
	private String[] dayList = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	
	private int groupNo;

	private Map<String, String> availableDateMap;
	
	private RedisTemplate<String, String> redisTemplate;
	
	private HashOperations<String, String, String> hashOps;
	
	public CalculateSchedule(ScheduleService scheduleService,
			RedisTemplate<String, String> redisTemplate,
			HashOperations<String, String, String> hashOps) {
		this.scheduleService = scheduleService;
		this.redisTemplate = redisTemplate;
		this.hashOps = hashOps;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	
	public void calculateSchedule() {
		int groupNo = this.groupNo;
		int[][] resultSchedule = new int[15][7];
		List<Map<String, Object>> scheduleList = loadScheduleList(groupNo);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		int temp_start = 0;
		int temp_end = 0;
		String temp_str = "";
		boolean isStart = false;
		
		Iterator itr = scheduleList.iterator();
		
		while(itr.hasNext()) {
			Map<String, Object> info = (Map)itr.next();
			
			int scheduleDay = Integer.parseInt(info.get("scheduleDay").toString());
			int startTime = Integer.parseInt(info.get("scheduleStartTime").toString());
			int finishTime = Integer.parseInt(info.get("scheduleFinishTime").toString());
			
			int start = startTime - 6;
			int end = finishTime - 6;
			
			for(int i = start; i < end; i++) {
				resultSchedule[i][scheduleDay] += 1;
			}
			
			System.out.println("SchduleDay :" + scheduleDay);
			System.out.println("StartTime :" + startTime);
			System.out.println("FinsihTime : " + finishTime);
			
		}
		
		for(int i = 0; i < 15; i++)  {
			
			for(int j = 0; j < 7; j++) {
				System.out.print(" [");
				System.out.print(resultSchedule[i][j]);
				System.out.print("]");
			}
			System.out.println("");
		}
		
		/* caculate available Date logic */
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 15; j++) {
				if(resultSchedule[j][i] == 0 && isStart == false) {
					temp_start = j + 6;
					isStart = true;
				} else if (resultSchedule[j][i] == 0 && isStart == true) {
					temp_end = j + 6;
					if(j == 14) {
						temp_str += ((temp_start + "-" + temp_end) + ";");
						isStart = false;
					}
				} else if (resultSchedule[j][i] != 0 && isStart == true) {
					temp_str += ((temp_start + "-" + temp_end) + ";");
					isStart = false;
				} 
			}
			System.out.println(dayList[i] + " : " + temp_str);
			resultMap.put(dayList[i], temp_str);
			temp_str = "";
			temp_start = 0;
			temp_end = 0;
		}
		
		setAvailableDateMap(resultMap);
		scheduleChanged();
	}
	
	public void setAvailableDateMap(Map<String, String> availableDateMap) {
		this.availableDateMap = availableDateMap;
	}
	
	public Map<String, String> getAvailableDateMap() {
		return this.availableDateMap;
	}
	
	public List<Map<String, Object>> loadScheduleList(int groupNo) {
		Map<String, Object> inputInfo = new HashMap<String, Object>();
		String weekStartDate = getThisWeekStartDate();
		
		System.out.println(weekStartDate);
		
		inputInfo.put("groupNo", groupNo);
		inputInfo.put("weekStartDate", weekStartDate);
		
		return scheduleService.selectScheduleWeekList(inputInfo);
	}
	
	public String getThisWeekStartDate() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		calendar.add(calendar.DATE, -day);
		
		return calendar.get(calendar.YEAR) 
				+ "-" + (calendar.get(calendar.MONTH)+1)
				+ "-" + (calendar.get(calendar.DATE));
	}
	
	public void scheduleChanged() {
		hashOps.putAll("available_date:" + this.groupNo, this.availableDateMap);
	}
	
}
