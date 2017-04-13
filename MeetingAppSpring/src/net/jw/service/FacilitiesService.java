package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.FacilitiesDAO;
import net.jw.meeting.model.Facilities ;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.SystemMessage;

public class FacilitiesService {
	
	
	private static ApplicationContext context;
	private static FacilitiesDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (FacilitiesDAO) context.getBean("facilitiesDAO");
		}
	 
	 
	 public static List<Facilities > fetch(QueryObject q)
	 {
		 List<Facilities > list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static Response insert(Facilities  obj)
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
	 
	 public static Response update(Facilities  obj)
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
	 
	 public static Response activate(Facilities obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.activate(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_FACILITY_ACTIVATED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response inactivate(Facilities obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.inactivate(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_FACILITY_INACTIVATED);
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
