package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Role ;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.MeetingRoomService;
import net.jw.service.RoleService;

@Controller
public class RoleController {
	
	@ResponseBody
	@RequestMapping(value="/role/fetch", method = RequestMethod.POST)
	public List<Role > fetch(@RequestBody QueryObject q)
	{
		List<Role > list = RoleService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/role/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Role  obj)
	{
		Response res = RoleService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/role/update", method = RequestMethod.POST)
	public Response update(@RequestBody Role  obj)
	{
		Response res = RoleService.update(obj);
		
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/role/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody Role obj)
	{
		Response res = RoleService.activate(obj);
	
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/role/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody Role obj)
	{
		Response res = RoleService.inactivate(obj);
		

		return res;
	}
	

}
