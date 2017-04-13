package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.PermissionMatrix;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.PermissionMatrixService;

@Controller
public class PermissionMatrixController {
	
	@ResponseBody
	@RequestMapping(value="/permission-matrix/fetch", method = RequestMethod.POST)
	public List<PermissionMatrix> fetch(@RequestBody QueryObject q)
	{
		List<PermissionMatrix> list = PermissionMatrixService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/permission-matrix/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody PermissionMatrix obj)
	{
		Response res = PermissionMatrixService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/permission-matrix/delete", method = RequestMethod.POST)
	public Response update(@RequestBody PermissionMatrix obj)
	{
		Response res = PermissionMatrixService.delete(obj);
		
		return res;
	}
	
	

}
