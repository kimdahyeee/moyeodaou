package com.daou.moyeo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daou.moyeo.dto.ScheduleDTO;
import com.daou.moyeo.observer.CalculateSchedule;
import com.daou.moyeo.schedule.service.ScheduleService;

@RestController
public class JsonController {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> hashOps;

	@Resource(name="scheduleService")
	private ScheduleService scheduleService;
	
	private CalculateSchedule calculateSchedule;
	
	public void update(int groupNo) {
		calculateSchedule = new CalculateSchedule(scheduleService,redisTemplate, hashOps);
		calculateSchedule.setGroupNo(groupNo);
		calculateSchedule.calculateSchedule();
	}

	/**
	 * 스케줄 등록
	 * @param scheduleDto
	 * @return
	 * @author KimDaHye
	 */
	@RequestMapping(value = "/insertSchedule", method=RequestMethod.POST, consumes = "application/json")
	public ScheduleDTO insertSchedule(@RequestBody ScheduleDTO scheduleDto) {
		scheduleService.insertScheduleInfo(scheduleDto);
		update(scheduleDto.getGroupNo());
		return scheduleDto;
	}

	/**
	 * 회원 스케줄 출력
	 * @param scheduleUserInfo
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 * @author KimDaHye 20170615
	 */
	@RequestMapping(value = "/calendarEvent", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<Map<String, Object>> calendarEvent(@RequestBody Map<String, Object> scheduleUserInfo, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<Map<String, Object>> scheduleList = scheduleService.selectScheduleList(scheduleUserInfo);
		return scheduleList;
	}
	
	@RequestMapping(value = "/deleteSchedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public int deleteSchedule(@RequestBody Map<String, Object> scheduleUserInfo, HttpServletRequest req, HttpServletResponse res) throws IOException {
		int scheduleNo = (Integer) scheduleUserInfo.get("scheduleNo");
		int result = scheduleService.deleteSchedule(scheduleNo);
		return result;
	}
}
