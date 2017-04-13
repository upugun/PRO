package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.MeetingRoomResources;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.MeetingRoomResourcesService;

@Controller
public class MeetingRoomResourcesController {
	
	@ResponseBody
	@RequestMapping(value="/meetingroom_resources/fetch", method = RequestMethod.POST)
	public List<MeetingRoomResources> fetch(@RequestBody QueryObject q)
	{
		List<MeetingRoomResources> list = MeetingRoomResourcesService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/meetingroom_resources/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody MeetingRoomResources obj)
	{
		Response res = MeetingRoomResourcesService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/meetingroom_resources/update", method = RequestMethod.POST)
	public Response update(@RequestBody MeetingRoomResources obj)
	{
		Response res = MeetingRoomResourcesService.update(obj);
		
		return res;
	}
	
	

}
