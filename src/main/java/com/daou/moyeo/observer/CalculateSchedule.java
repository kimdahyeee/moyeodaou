/*
 * Author : Daeho Han
 * Date : 2017.06.15
 * Title : CalculateSchedule 
 * Contents : Observable 클래스를 상속 받아 가능한 날짜를 계산하고 Observer들에게 notify 하는 클래스
 */

package com.daou.moyeo.observer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.annotation.Resource;

import com.daou.moyeo.schedule.service.ScheduleService;


public class CalculateSchedule extends Observable {
	
	private ScheduleService scheduleService;
	
	private int[][] resultSchedule;
	
	public CalculateSchedule(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	public void calculateSchedule(int groupNo) {
		int[][] resultSchedule = new int[15][7];
		List<Map<String, Object>> scheduleList = loadScheduleList(groupNo);
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
		
		this.resultSchedule = resultSchedule;
		scheduleChanged();
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
		setChanged();
		notifyObservers();
	}
	
	public int[][] getAvailableDate() {
		return resultSchedule;
	}
}
