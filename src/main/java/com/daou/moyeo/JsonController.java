package com.daou.moyeo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daou.moyeo.dto.ScheduleDTO;
import com.daou.moyeo.observer.AvailableDateTransfer;
import com.daou.moyeo.observer.CalculateSchedule;
import com.daou.moyeo.schedule.service.ScheduleService;

@RestController
public class JsonController {

	@Resource(name="scheduleService")
	private ScheduleService scheduleService;
	
	
	static CalculateSchedule calculateSchedule;
	static AvailableDateTransfer availableDateTransfer;
	
	public void jsonController() {
		calculateSchedule = new CalculateSchedule(scheduleService);
		availableDateTransfer = new AvailableDateTransfer(calculateSchedule);
	}
	
	public void changeInfo(int groupNo) {
		calculateSchedule.calculateSchedule(groupNo);
	}
	
	@RequestMapping(value = "/insertSchedule", method=RequestMethod.POST, consumes = "application/json")
	public ScheduleDTO insertSchedule(@RequestBody ScheduleDTO scheduleDto) {
		scheduleService.insertScheduleInfo(scheduleDto);
		jsonController();
		changeInfo(scheduleDto.getGroupNo());
		return scheduleDto;
	}

	@RequestMapping(value = "/calendarEvent", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<Map<String, Object>> calendarEvent(@RequestBody Map<String, Object> scheduleUserInfo, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<Map<String, Object>> scheduleList = scheduleService.selectScheduleList(scheduleUserInfo);
		return scheduleList;
	}
}
