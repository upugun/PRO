package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.LocationDAO;
import net.jw.meeting.model.Location;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.SystemMessage;

public class LocationService {

	private static ApplicationContext context;
	private static LocationDAO dao;

	static {
		context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
		dao = (LocationDAO) context.getBean("locationDAO");
	}

	public static List<Location> fetch(QueryObject q) {
		List<Location> list = dao.fetch(q);

		return list;
	}

	public static Response insert(Location obj) {
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

	public static Response update(Location obj) {
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
	
	 
	 
	 public static Response activate(Location obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.activate(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_LOCATIION_ACTIVATED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response inactivate(Location obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.inactivate(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage. MSG_LOCATIION_INACTIVATED);
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
