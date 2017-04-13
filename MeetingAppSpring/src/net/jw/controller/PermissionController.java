package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Permission ;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.meeting.model.Role;
import net.jw.service.PermissionService;
import net.jw.service.RoleService;

@Controller
public class PermissionController {
	
	@ResponseBody
	@RequestMapping(value="/permission/main-options", method = RequestMethod.POST)
	public List<Permission > getMainOptions(@RequestBody QueryObject q)
	{
		List<Permission > list = PermissionService.getMainOptions(q);
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/main-options-nav", method = RequestMethod.POST)
	public List<Permission > getMainOptionsForNavigation(@RequestBody QueryObject q)
	{
		List<Permission > list = PermissionService.getMainOptionsForNavigation(q);
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/fetch", method = RequestMethod.POST)
	public List<Permission > fetch(@RequestBody QueryObject q)
	{
		List<Permission > list = PermissionService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/permission/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Permission  obj)
	{
		Response res = PermissionService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/update", method = RequestMethod.POST)
	public Response update(@RequestBody Permission  obj)
	{
		Response res = PermissionService.update(obj);
		
		return res;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/permission/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody Permission obj)
	{
		Response res = PermissionService.activate(obj);
	
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/permission/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody Permission obj)
	{
		Response res = PermissionService.inactivate(obj);
		

		return res;
	}
	

}
