package net.jw.system;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.stringtemplate.v4.ST;

import net.jw.meeting.model.Booking;
import net.jw.meeting.model.BookingFacilities;

public class MailTemplate {
	
	public static String MAIL_SUBJECT_USER_REGISTRATION 	= "Registration Successfull";
	public static String MAIL_SUBJECT_USER_ACTIVATED    	= "Profile Activated";
	public static String MAIL_SUBJECT_BOOKING_CREATED    	= "Booking Created - Waiting for approval";
	public static String MAIL_SUBJECT_BOOKING_CONFIRMED    	= "Booking Confirmed";
	public static String MAIL_SUBJECT_BOOKING_REJECTED    	= "Booking Rejected";
	public static String MAIL_SUBJECT_PASSWORD_RESET     	=  "Password Reset";
	public static String MAIL_SUBJECT_FACILITY_REQUEST     	=  "Request for facilities";
	
	public static String userRegistrationTemplate(String userName)
	{
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Thank You for registering. Please visit below to upate your profile."
				+ "\n"
				+ AppConstant.SYSTEM_URL+AppConstant.URL_USER_PROFILE
				+ "\n"
				+ "\n"
				+ "Cheers!");
		hello.add("name", userName);
		String output = hello.render();
		
		return output;
	}
	
	
	public static String userPasswordResetTemplate(String userName, String password)
	{
		
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Your password has been successfully reset."
				+ "\n"
				+ "Your new password is <password>"
				+ "\n"
				+ "\n"
				+ "once u logged in, visit below link to change your password!"
				+ "\n"
				+ AppConstant.SYSTEM_URL+AppConstant.URL_USER_PROFILE
				+ "\n"
				+ "\n"
				+ "Cheers!");
		hello.add("name", userName);
		hello.add("password", password);
		String output = hello.render();
		
		return output;
	}
	
	public static String userActivateTemplate(String userName)
	{
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Your user profile has been activated."
				+ "\n"
				+"Click here to view the profile:"
				+ AppConstant.SYSTEM_URL+AppConstant.URL_USER_PROFILE
				+ "\n"
				+ "\n"
				+ "Cheers!");
		hello.add("name", userName);
		String output = hello.render();
		
		return output;
	}
	
	public static String bookingFacilityTemplate(Booking obj, BookingFacilities facility)
	{
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Following facility needs to be arranged."
				+ "\n"
				+ "\n"
				+ "Date\t\t: <date>"
				+ "\n"
				+ "Venue\t\t: <meetingroom>"
				+ "\n"
				+ "Facility\t: <facility>"
				+ "\n"
				+ "No. of items: <count>"
				+ "\n"
				+ "Booking No\t: <bookingNo>"
				+ "\n"
				+ "\n"
				+ "For more details please contact "
				+ "\n"
				+ "<bUserName>"
				+ "\n"
				+ "Email\t: <email> "
				+ "\n"
				+ "Telephone no\t: <tp>"
				+ "\n"
				+ "\n"
				//+"Click here to view further details:"
				//+ AppConstant.SYSTEM_URL+AppConstant.URL_BOOKING
				+ "\n"
				+ "\n"
				+ "Cheers!");
		hello.add("name", facility.getFacilities().getAdmin().getuName());
		hello.add("facility", facility.getFacilities().getfName());
		hello.add("count", facility.getCount());
		hello.add("date", obj.getStart());
		hello.add("bookingNo", facility.getBookingId() );
		hello.add("meetingroom", obj.getMeetingRoom().getmRoomName());
		hello.add("bUserName", obj.getBookingUser().getbUserName());
		hello.add("email", obj.getBookingUser().getbUserEmail());
		hello.add("tp", obj.getBookingUser().getbUserMobile());
		String output = hello.render();
		
		return output;
	}
	
	
	
	
	public static String getRelaventBookingSubject(boolean isApprove)
	{
		if(isApprove)
			return MailTemplate.MAIL_SUBJECT_BOOKING_CREATED;
		else
			return MailTemplate.MAIL_SUBJECT_BOOKING_CONFIRMED;
	}
	
	public static String getRelaventBookingTemplate(String userName, String title,String meetingRoomName, String startDateTime, String endDateTime, boolean approval)
	{
		if(approval)
			return bookingCreatedTemplate(userName, title, meetingRoomName, startDateTime, endDateTime);
		else
			return bookingApprovedTemplate(userName, title, meetingRoomName, startDateTime, endDateTime);
	}
	
	public static String bookingCreatedTemplate(String userName, String title,String meetingRoomName, String startDateTime, String endDateTime)
	{
		String date 	= prepareDateToDisplay(startDateTime);
		String timeFrom = prepareTimeToDisplay(startDateTime);
		String timeTo   = prepareTimeToDisplay(endDateTime);
		
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Your booking at <meetingRoomName> is sent for approval."
				+ "\n"
				+ "\n"
				+ "Title : <title>"
				+ "\n"
				+ "Date : <date>"
				+ "\n"
				+ "Time : <timeFrom> - <timeTo>"
				+ "\n"
				+"Click here to view the booking:"
				+ "\n"
				+ AppConstant.SYSTEM_URL
				);
		hello.add("title", title);
		hello.add("name", userName);
		hello.add("meetingRoomName", meetingRoomName);
		hello.add("date", date);
		hello.add("timeFrom", timeFrom);
		hello.add("timeTo", timeTo);
		String output = hello.render();
		
		return output;
	}
	
	public static String bookingApprovedTemplate(String userName, String title,String meetingRoomName, String startDateTime, String endDateTime)
	{
		String date 	= prepareDateToDisplay(startDateTime);
		String timeFrom = prepareTimeToDisplay(startDateTime);
		String timeTo   = prepareTimeToDisplay(endDateTime);
		
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Your booking is confirmed at <meetingRoomName>"
				+ "\n"
				+ "\n"
				+ "Title : <title>"
				+ "\n"
				+ "Date : <date>"
				+ "\n"
				+ "Time : <timeFrom> - <timeTo>"
				+ "\n"
				+"Click here to view the booking:"
				+ "\n"
				+ AppConstant.SYSTEM_URL
				);
		hello.add("title", title);
		hello.add("name", userName);
		hello.add("meetingRoomName", meetingRoomName);
		hello.add("date", date);
		hello.add("timeFrom", timeFrom);
		hello.add("timeTo", timeTo);
		String output = hello.render();
		
		return output;
	}
	
	public static String bookingRejectedTemplate(String userName, String title,String meetingRoomName, String startDateTime, String endDateTime)
	{
		String date 	= prepareDateToDisplay(startDateTime);
		String timeFrom = prepareTimeToDisplay(startDateTime);
		String timeTo   = prepareTimeToDisplay(endDateTime);
		
		ST hello = new ST("Hello <name>, "
				+ "\n"
				+ "\n"
				+ "Your booking is rejected at <meetingRoomName>"
				+ "\n"
				+ "\n"
				+ "Title : <title>"
				+ "\n"
				+ "Date : <date>"
				+ "\n"
				+ "Time : <timeFrom> - <timeTo>"
				+ "\n"
				);
		hello.add("title", title);
		hello.add("name", userName);
		hello.add("meetingRoomName", meetingRoomName);
		hello.add("date", date);
		hello.add("timeFrom", timeFrom);
		hello.add("timeTo", timeTo);
		String output = hello.render();
		
		return output;
	}
	
	private static String prepareDateToDisplay(String inputDate)
	{
		String formattedDate = "";	
		try
		 {
		 DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = originalFormat.parse(inputDate);
		 formattedDate = targetFormat.format(date);  // 20120821
		 }
		 catch (Exception e) {
			System.out.println("************* error : BookingMapper.prepareDateToDisplay()");
		}
		return formattedDate;
	}
	
	private static String prepareTimeToDisplay(String inputDate)
	{
		String formattedDate = "";	
		try
		 {
		 DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 DateFormat targetFormat = new SimpleDateFormat("HH:mm");
		 Date date = originalFormat.parse(inputDate);
		 formattedDate = targetFormat.format(date);  // 20120821
		 }
		 catch (Exception e) {
			System.out.println("************* error : BookingMapper.prepareDateToDisplay()");
		}
		return formattedDate;
	}

}
