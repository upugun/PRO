package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.LoginDAO;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.Login;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.SystemMessage;

public class LoginService {
	
	
	private static ApplicationContext context;
	private static LoginDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (LoginDAO) context.getBean("loginDAO");
		}
	 
	 
	 public static List<Login> fetch(QueryObject q)
	 {
		 List<Login> list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static void registerUser(Login obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.insert(obj);
			 
			 JWMail mail = new JWMail();
			 
			 res = MailService.sendMail(mail);
			 res.setMessage(SystemMessage.MSG_USER_REGISTERED);
		 }	 
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
	 }

}
