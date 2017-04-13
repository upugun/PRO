package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Resources;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.ResourcesService;

@Controller
public class ResourcesController {
	
	@ResponseBody
	@RequestMapping(value="/resources/fetch", method = RequestMethod.POST)
	public List<Resources> fetch(@RequestBody QueryObject q)
	{
		List<Resources> list = ResourcesService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/resources/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Resources obj)
	{
		Response res = ResourcesService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/resources/update", method = RequestMethod.POST)
	public Response update(@RequestBody Resources obj)
	{
		Response res = ResourcesService.update(obj);
		
		return res;
	}
	
	

}
