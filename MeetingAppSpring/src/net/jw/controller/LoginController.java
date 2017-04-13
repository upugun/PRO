package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Login;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.LoginService;

@Controller
public class LoginController {
	
	@ResponseBody
	@RequestMapping(value="/meeting/login/fetch", method = RequestMethod.POST)
	public List<Login> fetch(@RequestBody QueryObject q)
	{
		List<Login> list = LoginService.fetch(q);
		
		System.out.println("LoginController.fetch()" + list.size());
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/meeting/login", method = RequestMethod.POST)
	public Response login(@RequestBody QueryObject q)
	{
		List<Login> list = LoginService.fetch(q);
		
		Response obj = new Response();
		
		if(list.size()>0)
		{
			obj.setSuccess(true);
			obj.setMessage(String.valueOf(list.get(0).getUid()));
		}
		else
			obj.setSuccess(false);
		
		return obj;
	}
	
	
	

}
