package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.MailDBService;

@Controller
public class JWMailDBController {
	
	
	@ResponseBody
	@RequestMapping(value="/mail/fetch", method = RequestMethod.POST)
	public List<JWMail> fetch(@RequestBody QueryObject q)
	{
		List<JWMail> list = MailDBService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/mail/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody JWMail obj)
	{
		Response res = MailDBService.insert(obj);
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/mail/set-read-status", method = RequestMethod.POST)
	public Response setReadStatus(@RequestBody JWMail obj)
	{
		Response res = MailDBService.setReadStatus(obj.getMailId(), obj.getReadStatus());
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/mail/delete", method = RequestMethod.POST)
	public Response delete(@RequestBody JWMail obj)
	{
		Response res = MailDBService.delete(obj);
		
		return res;
	}
	

}
