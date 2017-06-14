package com.daou.moyeo.schedule.service;

import java.util.List;

import com.daou.moyeo.dto.ScheduleDTO;

public interface ScheduleService {
	public int insertScheduleInfo(ScheduleDTO scheduleDto);
	public List<Object> selectScheduleList(int memberNo);
}
