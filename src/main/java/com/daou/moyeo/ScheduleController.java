package com.daou.moyeo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daou.moyeo.dto.ScheduleDTO;
import com.daou.moyeo.schedule.service.ScheduleService;
import com.daou.moyeo.user.vo.UserDetailsVO;

@Controller
public class ScheduleController {
	@Resource(name="scheduleService")
	private ScheduleService scheduleService;
	
	@RequestMapping(value = "/group/{groupNo}/calendar")
	public String calendarView(@PathVariable("groupNo") int groupNo, Model model, Authentication auth) {
		UserDetailsVO u = (UserDetailsVO) auth.getPrincipal();
		int memberNo = u.getMemberNo();
		
		List<Object> scheduleList = scheduleService.selectScheduleList(memberNo);
		if(scheduleList != null){
			System.out.println(scheduleList);
			model.addAttribute(scheduleList);
		}
		model.addAttribute("groupNo", groupNo);
		
		return "calendar";
	}
}
