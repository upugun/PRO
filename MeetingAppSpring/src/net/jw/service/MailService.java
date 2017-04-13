package net.jw.service;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.mail.JWMailer;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.Response;
import net.jw.system.AppConstant;

public class MailService {
	
	
	private static ApplicationContext context;
	private static JWMailer mail;
	
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/spring-mail.xml");
			mail 	= (JWMailer) context.getBean("mailer");
		}
	 
	 
	 public static Response sendMail(JWMail obj)
	 {
		 Response res = new Response();
		 res.setSuccess(true);
		 try
		 {
			 String msg = prepareFooterNote(obj.getMessage());
			 obj.setMessage(msg);
			 mail.sendMail(obj);
			 MailDBService.insert(obj);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(e.getMessage());
			 e.printStackTrace();
		 }
		 
		 return res;
		 
	 }
	 
	 private static String prepareFooterNote(String inmsg)
	 {
		 String msg = inmsg+"\n"
		 		+ "\n"
				+ AppConstant.EMAIL_FOOTER_NOTE;
		 
		 return msg;
	 }
	 

}
