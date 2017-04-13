package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Location;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.LocationService;
import net.jw.service.MeetingRoomService;

@Controller
public class LocationController {
	
	@ResponseBody
	@RequestMapping(value="/location/fetch", method = RequestMethod.POST)
	public List<Location> fetch(@RequestBody QueryObject q)
	{
		List<Location> list = LocationService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/location/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Location obj)
	{
		Response res = LocationService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/location/update", method = RequestMethod.POST)
	public Response update(@RequestBody Location obj)
	{
		Response res = LocationService.update(obj);
		
		return res;
	}
	

	@ResponseBody
	@RequestMapping(value="/location/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody Location obj)
	{
		Response res = LocationService.activate(obj);
	
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/location/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody Location obj)
	{
		Response res = LocationService.inactivate(obj);
		

		return res;
	}

	

}
