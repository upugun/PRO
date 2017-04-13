package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.JWMailerDAO;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;

public class MailDBService {
	
	
	private static ApplicationContext context;
	private static JWMailerDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (JWMailerDAO) context.getBean("jwMailerDAO");
		}
	 
	 
	 public static List<JWMail> fetch(QueryObject q)
	 {
		 List<JWMail> list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static Response insert(JWMail obj)
	 {
		 Response res = new Response();
		 
		 dao.insert(obj);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 
	 public static Response setReadStatus(int mailId, int readStatus )
	 {
		 Response res = new Response();
		 
		 dao.setReadStatus(mailId, readStatus);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 
	 public static Response delete(JWMail obj)
	 {
		 Response res = new Response();
		 
		 dao.delete(obj);
		 
		 /**TODO  : build logic correctly**/
		 res.setSuccess(true);
		 
		 return res;
	 }
	 

}
