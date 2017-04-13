package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.MeetingRoomResourcesDAO;
import net.jw.meeting.model.MeetingRoomResources;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;

public class MeetingRoomResourcesService {
	
	
	private static ApplicationContext context;
	private static MeetingRoomResourcesDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (MeetingRoomResourcesDAO) context.getBean("meetingRoomResourcesDAO");
		}
	 
	 
	 public static List<MeetingRoomResources> fetch(QueryObject q)
	 {
		 List<MeetingRoomResources> list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static Response insert(MeetingRoomResources obj)
	 {
		 Response res = new Response();
		 
		 dao.insert(obj);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 
	 public static Response update(MeetingRoomResources obj)
	 {
		 Response res = new Response();
		 
		 dao.update(obj);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 

}
