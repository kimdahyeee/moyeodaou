package com.daou.moyeo;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daou.moyeo.dto.ScheduleDTO;
import com.daou.moyeo.schedule.service.ScheduleService;

@RestController
public class JsonController {
	
	@Resource(name="scheduleService")
	private ScheduleService scheduleService;
	
	@RequestMapping(value = "/insertSchedule", method=RequestMethod.POST, consumes = "application/json")
	public void insertSchedule(@RequestBody ScheduleDTO scheduleDto, Model model) {
		scheduleService.insertScheduleInfo(scheduleDto);
	}
}
