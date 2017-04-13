package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.RoleDAO;
import net.jw.meeting.model.Role;
import net.jw.system.SystemMessage;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;

public class RoleService {

	private static ApplicationContext context;
	private static RoleDAO dao;

	static {
		context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
		dao = (RoleDAO) context.getBean("roleDAO");
	}

	public static List<Role> fetch(QueryObject q) {
		List<Role> list = dao.fetch(q);

		return list;
	}

	public static Response insert(Role obj) {
		Response res = new Response();
		try {
			dao.insert(obj);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}

		return res;
	}

	public static Response update(Role obj) {
		Response res = new Response();
		try {
			dao.update(obj);
			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}
		return res;
	}
	
	 
	 public static Response activate(Role obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.activate(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_ROLE_ACTIVATED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response inactivate(Role obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.inactivate(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_ROLE_INACTIVATED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 


}
