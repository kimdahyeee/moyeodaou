package com.daou.moyeo.schedule.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.daou.moyeo.dto.ScheduleDTO;

public interface ScheduleService {
	public int insertScheduleInfo(ScheduleDTO scheduleDto);
	public List<Map<String,Object>> selectScheduleWeekList(Map<String, Object> inputInfo);
	public List<Map<String, Object>> selectScheduleList(Map<String, Object> scheduleUserInfo);
}
