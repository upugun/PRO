package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Facilities ;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.FacilitiesService;
import net.jw.service.MeetingRoomService;

@Controller
public class FacilitiesController {
	
	@ResponseBody
	@RequestMapping(value="/facilities/fetch", method = RequestMethod.POST)
	public List<Facilities > fetch(@RequestBody QueryObject q)
	{
		List<Facilities > list = FacilitiesService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/facilities/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Facilities  obj)
	{
		Response res = FacilitiesService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/facilities/update", method = RequestMethod.POST)
	public Response update(@RequestBody Facilities  obj)
	{
		Response res = FacilitiesService.update(obj);
		
		return res;
	}
	

	@ResponseBody
	@RequestMapping(value="/facilities/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody Facilities obj)
	{
		Response res = FacilitiesService.activate(obj);
	
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/facilities/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody Facilities obj)
	{
		Response res = FacilitiesService.inactivate(obj);
		

		return res;
	}

}
