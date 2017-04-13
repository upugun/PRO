package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.BookingDAO;
import net.jw.meeting.model.Booking;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.AppConstant;
import net.jw.system.SystemMessage;

public class BookingService {
	
	
	private static ApplicationContext context;
	private static BookingDAO dao;
	
	 static {
			context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
			dao 	= (BookingDAO) context.getBean("bookingDAO");
		}
	 
	 
	 public static List<Booking> fetch(QueryObject q)
	 {
		 List<Booking> list = dao.fetch(q);
		 
		 return list;
	 }
	 
	 public static Response insert(Booking obj)
	 {
		 Response res = new Response();
		 
		 try
		 {
			 if(obj.getMeetingRoom().isApproval())
			 {
				 obj.setBookingStatus(AppConstant.BOOKING_STATUS_PENDING);
			 }
			 
			 else
			 {
				 obj.setBookingStatus(AppConstant.BOOKING_STATUS_APPROVED);
			 }
			 
			 QueryObject q = new QueryObject();
			 q.setSearchCode("searchAlreadyBooked");
			 q.setCondition1(String.valueOf(obj.getMeetingRoom().getmId()));
			 q.setCondition2(obj.getStart());
			 q.setCondition3(obj.getEnd());
			 q.setCondition4(String.valueOf(obj.getBid()));
			 
			 List<Booking> list = dao.fetch(q);	 
			 
			 if(list.size()>0)
			 {
				 res.setSuccess(false);
				 res.setMessage(SystemMessage.MSG_BOOKING_TIME_NOT_AVAILABLE);
			 }
			 else
			 {
				 dao.insert(obj);
				 res.setSuccess(true);
				 res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
			 }
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 public static Response update(Booking obj)
	 {
		 Response res = new Response();
		 try
		 {
			 QueryObject q = new QueryObject();
			 q.setSearchCode("searchAlreadyBooked");
			 q.setCondition1(String.valueOf(obj.getMeetingRoom().getmId()));
			 q.setCondition2(obj.getStart());
			 q.setCondition3(obj.getEnd());
			 q.setCondition4(String.valueOf(obj.getBid()));
			 
			 List<Booking> list = dao.fetch(q);	 
			 
			 if(list.size()>0)
			 {
				 res.setSuccess(false);
				 res.setMessage(SystemMessage.MSG_BOOKING_TIME_NOT_AVAILABLE);
			 }
			 else
			 {
				 dao.update(obj);
				 res.setSuccess(true);
				 res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
				 
			 }
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 
		 return res;
	 }
	 
	 public static Response create(Booking obj)
	 {
		 Response res = new Response();
		 
		 try
		 {
			 if(obj.getMeetingRoom().isApproval())
			 {
				 obj.setBookingStatus(AppConstant.BOOKING_STATUS_PENDING);
			 }
			 
			 else
			 {
				 obj.setBookingStatus(AppConstant.BOOKING_STATUS_APPROVED);
			 }
			 
			 QueryObject q = new QueryObject();
			 q.setSearchCode("searchAlreadyBooked");
			 q.setCondition1(String.valueOf(obj.getMeetingRoom().getmId()));
			 q.setCondition2(obj.getStart());
			 q.setCondition3(obj.getEnd());
			 q.setCondition4(String.valueOf(obj.getBid()));
			 
			 List<Booking> list = dao.fetch(q);	 
			 
			 if(list.size()>0)
			 {
				 res.setSuccess(false);
				 res.setMessage(SystemMessage.MSG_BOOKING_TIME_NOT_AVAILABLE);
			 }
			 else
			 {
				 dao.create(obj);
				 res.setSuccess(true);
				 res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
			 }
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response activate(Booking obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.activate(obj);
			 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_BOOKING_APPROVED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 
	 public static Response inactivate(Booking obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.inactivate(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_BOOKING_REJECTED);
		 }
		 catch(Exception e)
		 {
			 res.setSuccess(false);
			 res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			 SystemMessage.pringMessage(e.getMessage().toString());
		 }
		 return res;
	 }
	 
	 public static Response delete(Booking obj)
	 {
		 Response res = new Response();
		 try
		 {
			 dao.delete(obj);
		 
			 res.setSuccess(true);
			 res.setMessage(SystemMessage.MSG_BOOKING_DELETE);
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
