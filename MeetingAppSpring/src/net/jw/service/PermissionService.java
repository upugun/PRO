package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.PermissionDAO;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.Permission;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.SystemMessage;

public class PermissionService {

	private static ApplicationContext context;
	private static PermissionDAO dao;

	static {
		context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
		dao = (PermissionDAO) context.getBean("permissionDAO");
	}

	public static List<Permission> fetch(QueryObject q) {
		List<Permission> list = dao.fetch(q);

		return list;
	}

	public static List<Permission> getMainOptions(QueryObject q) {
		List<Permission> list = dao.getMainOptions(q);

		return list;
	}

	public static List<Permission> getMainOptionsForNavigation(QueryObject q) {
		List<Permission> list = dao.getMainOptionsForNavigation(q);

		return list;
	}

	public static Response insert(Permission obj) {
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

	public static Response update(Permission obj) {
		Response res = new Response();
		try {
			dao.update(obj);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);

			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}
		return res;
	}
	
	
	 
	 public static Response activate(Permission obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.activate(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_OPTION_ACTIVATED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response inactivate(Permission obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.inactivate(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_OPTION_INACTIVATED);
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
