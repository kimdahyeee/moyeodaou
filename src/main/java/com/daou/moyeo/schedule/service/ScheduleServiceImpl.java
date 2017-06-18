package com.daou.moyeo.schedule.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.daou.moyeo.dto.ScheduleDTO;

@Service("scheduleService")
public class ScheduleServiceImpl extends SqlSessionDaoSupport implements ScheduleService {

	@Override
	public int insertScheduleInfo(ScheduleDTO scheduleDto) {
		return getSqlSession().insert("schedule.insertScheduleInfo", scheduleDto);
	}

	@Override
	public List<Map<String, Object>> selectScheduleList(Map<String, Object> scheduleUserInfo) {
		return getSqlSession().selectList("schedule.selectScheduleList", scheduleUserInfo);
	}
	
	@Override
	public List<Map<String, Object>> selectScheduleWeekList(Map<String, Object> inputInfo) {
		return getSqlSession().selectList("schedule.selectScheduleWeekList", inputInfo);
	}

	@Override
	public int deleteSchedule(int scheduleNo) {
		return getSqlSession().delete("schedule.deleteSchedule", scheduleNo);
	}
	
	
}
