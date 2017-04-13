package net.jw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.jw.meeting.model.Booking;
import net.jw.meeting.model.BookingFacilities;
import net.jw.meeting.model.BookingUsers;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.meeting.model.Role;
import net.jw.meeting.model.Users;
import net.jw.service.BookingService;
import net.jw.service.MailService;
import net.jw.service.RoleService;
import net.jw.service.UsersService;
import net.jw.system.AppConstant;
import net.jw.system.MailTemplate;

@Controller
public class BookingController {
	
	@ResponseBody
	@RequestMapping(value="/booking/fetch", method = RequestMethod.POST)
	public List<Booking> fetch(@RequestBody QueryObject q)
	{
		List<Booking> list = BookingService.fetch(q);
		
		return list;
	}
	
	//@CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value="/booking/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Booking obj)
	{
		Response res = BookingService.insert(obj);
		
		QueryObject uq = new QueryObject();
		uq.setSearchCode("searchbyUid");
		uq.setCondition1(String.valueOf(obj.getCommon().getCreatedById()));
		List<Users> usersList = UsersService.fetch(uq);
		
		if(usersList.size()>0)
		{
			BookingUsers bookingUsers = new BookingUsers();
			bookingUsers.setbUserName(usersList.get(0).getuName());
			bookingUsers.setbUserEmail(usersList.get(0).getEmail());
			bookingUsers.setbUserMobile(usersList.get(0).getMobile());
			
			obj.setBookingUser(bookingUsers);
			
			if(res.isSuccess())
			{
				JWMail mail = new JWMail();
				mail.setTo(usersList.get(0).getEmail());
				mail.setFrom(AppConstant.SYSTEM_EMAIL);
				mail.setSubject(MailTemplate.getRelaventBookingSubject(obj.getMeetingRoom().isApproval()));
				mail.setMessage(MailTemplate.getRelaventBookingTemplate(obj.getBookingUser().getbUserName(),obj.getTitle(), obj.getMeetingRoom().getmRoomName(), obj.getStart(), obj.getEnd(), obj.getMeetingRoom().isApproval()));
				mail.setSenderId(AppConstant.SYS_ADMIN_ID);
				mail.setRecieverId(obj.getCommon().getCreatedById());
				mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
				mail.setCommon(obj.getCommon());
				MailService.sendMail(mail);
				
				if(obj.getFacilityList().size()>0)
					sendBookingFacilityEmails(obj);
			}
		}
		
		return res;
	}
	
	private void sendBookingFacilityEmails(Booking obj)
	{
		List<BookingFacilities> list = obj.getFacilityList();
		
		for(int i=0; i<list.size(); i++)
		{
			BookingFacilities facility = list.get(i);
			JWMail mail = new JWMail();
			mail.setTo(facility.getFacilities().getEmail());
			mail.setFrom(AppConstant.SYSTEM_EMAIL);
			mail.setSubject(AppConstant.FACILITY_REQUEST+" - "+facility.getFacilities().getfName());
			mail.setMessage(MailTemplate.bookingFacilityTemplate(obj, facility));
			mail.setSenderId(AppConstant.SYS_ADMIN_ID);
			mail.setRecieverId(facility.getFacilities().getOwner());
			mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
			MailService.sendMail(mail);
			
			
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/booking/update", method = RequestMethod.POST)
	public Response update(@RequestBody Booking obj)
	{
		Response res = BookingService.update(obj);
		
		QueryObject uq = new QueryObject();
		uq.setSearchCode("searchbyUid");
		uq.setCondition1(String.valueOf(obj.getCommon().getCreatedById()));
		List<Users> usersList = UsersService.fetch(uq);
		
		if(usersList.size()>0)
		{
			BookingUsers bookingUsers = new BookingUsers();
			bookingUsers.setbUserEmail(usersList.get(0).getEmail());
			bookingUsers.setbUserName(usersList.get(0).getuName());
			
			obj.setBookingUser(bookingUsers);
			
			if(res.isSuccess())
			{
				JWMail mail = new JWMail();
				mail.setTo(usersList.get(0).getEmail());
				mail.setFrom(AppConstant.SYSTEM_EMAIL);
				mail.setSubject(MailTemplate.getRelaventBookingSubject(obj.getMeetingRoom().isApproval()));
				mail.setMessage(MailTemplate.getRelaventBookingTemplate(obj.getBookingUser().getbUserName(),obj.getTitle(), obj.getMeetingRoom().getmRoomName(), obj.getStart(), obj.getEnd(), obj.getMeetingRoom().isApproval()));
				mail.setSenderId(AppConstant.SYS_ADMIN_ID);
				mail.setRecieverId(obj.getCommon().getCreatedById());
				mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
				mail.setCommon(obj.getCommon());
				MailService.sendMail(mail);
			}
		}
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/booking/create", method = RequestMethod.POST)
	public Response create(@RequestBody Booking obj)
	{
		Response res = BookingService.create(obj);
		
		QueryObject uq = new QueryObject();
		uq.setSearchCode("searchbyUid");
		uq.setCondition1(String.valueOf(obj.getCommon().getCreatedById()));
		List<Users> usersList = UsersService.fetch(uq);
		
		if(usersList.size()>0)
		{
			BookingUsers bookingUsers = new BookingUsers();
			bookingUsers.setbUserEmail(usersList.get(0).getEmail());
			bookingUsers.setbUserName(usersList.get(0).getuName());
			bookingUsers.setbUserMobile(usersList.get(0).getMobile());
			
			obj.setBookingUser(bookingUsers);
			if(res.isSuccess())
			{
				JWMail mail = new JWMail();
				mail.setTo(usersList.get(0).getEmail());
				mail.setFrom(AppConstant.SYSTEM_EMAIL);
				mail.setSubject(MailTemplate.getRelaventBookingSubject(obj.getMeetingRoom().isApproval()));
				mail.setMessage(MailTemplate.getRelaventBookingTemplate(obj.getBookingUser().getbUserName(),obj.getTitle(), obj.getMeetingRoom().getmRoomName(), obj.getStart(), obj.getEnd(), obj.getMeetingRoom().isApproval()));
				mail.setSenderId(AppConstant.SYS_ADMIN_ID);
				mail.setRecieverId(obj.getCommon().getCreatedById());
				mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
				mail.setCommon(obj.getCommon());
				MailService.sendMail(mail);
				
				
				if(obj.getFacilityList().size()>0)
					sendBookingFacilityEmails(obj);
			}
		}
		
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/booking/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody Booking obj)
	{
		Response res = BookingService.activate(obj);
		QueryObject uq = new QueryObject();
		uq.setSearchCode("searchbyUid");
		uq.setCondition1(String.valueOf(obj.getCommon().getCreatedById()));
		List<Users> usersList = UsersService.fetch(uq);
		
		if(usersList.size()>0)
		{
			BookingUsers bookingUsers = new BookingUsers();
			bookingUsers.setbUserEmail(usersList.get(0).getEmail());
			obj.setBookingUser(bookingUsers);
		
			if(res.isSuccess())
			{
				JWMail mail = new JWMail();
				mail.setTo(usersList.get(0).getEmail());
				mail.setFrom(AppConstant.SYSTEM_EMAIL);
				mail.setSubject(MailTemplate.MAIL_SUBJECT_BOOKING_CONFIRMED);
				mail.setMessage(MailTemplate.bookingApprovedTemplate(obj.getBookingUser().getbUserName(),obj.getTitle(), obj.getMeetingRoom().getmRoomName(), obj.getStart(), obj.getEnd()));
				mail.setSenderId(AppConstant.SYS_ADMIN_ID);
				mail.setRecieverId(obj.getCommon().getCreatedById());
				mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
				mail.setCommon(obj.getCommon());
				MailService.sendMail(mail);
			}
		}
		
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/booking/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody Booking obj)
	{
		Response res = BookingService.inactivate(obj);
		
		QueryObject uq = new QueryObject();
		uq.setSearchCode("searchbyUid");
		uq.setCondition1(String.valueOf(obj.getCommon().getCreatedById()));
		List<Users> usersList = UsersService.fetch(uq);
		
		if(usersList.size()>0)
		{
			BookingUsers bookingUsers = new BookingUsers();
			bookingUsers.setbUserEmail(usersList.get(0).getEmail());
			obj.setBookingUser(bookingUsers);
			if(res.isSuccess())
			{
				JWMail mail = new JWMail();
				mail.setTo(usersList.get(0).getEmail());
				mail.setFrom(AppConstant.SYSTEM_EMAIL);
				mail.setSubject(MailTemplate.MAIL_SUBJECT_BOOKING_REJECTED);
				mail.setMessage(MailTemplate.bookingRejectedTemplate(obj.getBookingUser().getbUserName(),obj.getTitle(), obj.getMeetingRoom().getmRoomName(), obj.getStart(), obj.getEnd()));
				mail.setSenderId(AppConstant.SYS_ADMIN_ID);
				mail.setRecieverId(obj.getCommon().getCreatedById());
				mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
				mail.setCommon(obj.getCommon());
				MailService.sendMail(mail);
			}
		}
		
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/booking/delete", method = RequestMethod.POST)
	public Response delete(@RequestBody Booking  obj)
	{
		Response res = BookingService.delete(obj);
		
		return res;
	}
	

}
