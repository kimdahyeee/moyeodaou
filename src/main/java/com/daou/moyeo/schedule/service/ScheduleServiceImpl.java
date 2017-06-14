package com.daou.moyeo.schedule.service;

import java.util.List;

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
	public List<Object> selectScheduleList(int memberNo) {
		return getSqlSession().selectList("schedule.selectScheduleList", memberNo);
	}
	
}
