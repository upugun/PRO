package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.ResourcesDAO;
import net.jw.meeting.model.Resources;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;

public class ResourcesService {
	
	
	private static ApplicationContext context;
	private static ResourcesDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (ResourcesDAO) context.getBean("resourcesDAO");
		}
	 
	 
	 public static List<Resources> fetch(QueryObject q)
	 {
		 List<Resources> list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static Response insert(Resources obj)
	 {
		 Response res = new Response();
		 
		 dao.insert(obj);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 
	 public static Response update(Resources obj)
	 {
		 Response res = new Response();
		 
		 dao.update(obj);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 

}
