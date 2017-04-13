package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.jw.meeting.model.Booking;
import net.jw.meeting.model.BookingUsers;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.MeetingRoom ;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.meeting.model.Users;
import net.jw.service.BookingService;
import net.jw.service.MailService;
import net.jw.service.MeetingRoomService;
import net.jw.service.UsersService;
import net.jw.system.AppConstant;
import net.jw.system.MailTemplate;

@Controller
public class MeetingRoomController {
	
	@ResponseBody
	@RequestMapping(value="/meetingroom/fetch", method = RequestMethod.POST)
	public List<MeetingRoom > fetch(@RequestBody QueryObject q)
	{
		List<MeetingRoom > list = MeetingRoomService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/meetingroom/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody MeetingRoom  obj)
	{
		Response res = MeetingRoomService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/meetingroom/update", method = RequestMethod.POST)
	public Response update(@RequestBody MeetingRoom  obj)
	{
		Response res = MeetingRoomService.update(obj);
		
		return res;
	}
	
	

	@ResponseBody
	@RequestMapping(value="/meetingroom/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody MeetingRoom obj)
	{
		Response res = MeetingRoomService.activate(obj);
	
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/meetingroom/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody MeetingRoom obj)
	{
		Response res = MeetingRoomService.inactivate(obj);
		

		return res;
	}

}
