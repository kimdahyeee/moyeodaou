package com.daou.moyeo;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daou.moyeo.schedule.service.ScheduleService;

@Controller
public class ScheduleController {
	@Resource(name="scheduleService")
	private ScheduleService scheduleService;
	
	@RequestMapping(value = "/group/{groupNo}/calendar")
	public String calendarView(@PathVariable("groupNo") int groupNo, Model model, Authentication auth) {
		model.addAttribute("groupNo", groupNo);
		return "calendar";
	}
}
