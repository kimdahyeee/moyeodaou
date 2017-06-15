package com.daou.moyeo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daou.moyeo.dto.ScheduleDTO;
import com.daou.moyeo.schedule.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class JsonController {

	@Resource(name="scheduleService")
	private ScheduleService scheduleService;

	@RequestMapping(value = "/insertSchedule", method=RequestMethod.POST, consumes = "application/json")
	public ScheduleDTO insertSchedule(@RequestBody ScheduleDTO scheduleDto) {
		scheduleService.insertScheduleInfo(scheduleDto);
		return scheduleDto;
	}

	@RequestMapping(value = "/calendarEvent", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<Map<String, Object>> calendarEvent(@RequestBody Map<String, Object> scheduleUserInfo, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<Map<String, Object>> scheduleList = scheduleService.selectScheduleList(scheduleUserInfo);
		return scheduleList;
	}
}
