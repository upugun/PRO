package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.MeetingRoomDAO;
import net.jw.meeting.model.Booking;
import net.jw.meeting.model.MeetingRoom ;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.SystemMessage;

public class MeetingRoomService {
	
	
	private static ApplicationContext context;
	private static MeetingRoomDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (MeetingRoomDAO) context.getBean("meetingRoomDAO");
		}
	 
	 
	 public static List<MeetingRoom > fetch(QueryObject q)
	 {
		 List<MeetingRoom > list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static Response insert(MeetingRoom  obj)
	 {
		 Response res = new Response();
		 
		 try
		 {
			 dao.insert(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 
		 
		 return res;
	 }
	 
	 public static Response update(MeetingRoom  obj)
	 {
		 Response res = new Response();
		 
		 try
		 {
			 dao.update(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 
		 return res;
	 }
	 
	 
	 
	 public static Response activate(MeetingRoom obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.activate(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_MROOM_ACTIVATED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response inactivate(MeetingRoom obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.inactivate(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_MROOM_INACTIVATED);
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
