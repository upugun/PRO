package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Booking;
import net.jw.meeting.model.BookingUsers;

public class BookingMapper implements RowMapper<Booking>{

	@Override
	public Booking mapRow(ResultSet rs, int row) throws SQLException {
		
		Booking obj = new Booking();
		obj.setBid(rs.getInt("bid"));
		obj.setMeetingRoomId(rs.getInt("meetingRoomId"));
		obj.setTitle(rs.getString("title"));
		obj.setStart(rs.getString("start"));
		obj.setEnd(rs.getString("end"));
		obj.setAllDay(rs.getBoolean("allDay"));
		obj.setRemarks(rs.getString("remarks"));
		obj.setRepeating(rs.getBoolean("repeating"));
		obj.setBookingStatus(rs.getString("bookingStatus"));
		
		obj.setStartTime(prepareTimeToDisplay(obj.getStart()));
		obj.setEndTime(prepareTimeToDisplay(obj.getEnd()));
		
	   
		
		CommonDetails common = new CommonDetails();
		common.setStatus(rs.getString("status"));
		common.setCreatedById(rs.getInt("createdById"));
		common.setCreatedByTime(rs.getString("createdByTime"));
		common.setUpdatedById(rs.getInt("updatedById"));
		common.setUpdatedByTime(rs.getString("updatedByTime"));
		
		obj.setCommon(common);
		
         BookingUsers bookingUser = new BookingUsers();
         bookingUser.setbUserId(rs.getInt("uid"));
         bookingUser.setbUserName(rs.getString("uName"));
         bookingUser.setbUserMobile(rs.getString("mobile"));
         bookingUser.setbUserEmail(rs.getString("email"));
         
         obj.setBookingUser(bookingUser);
        
		
		return obj;
	}
	
	private String prepareTimeToDisplay(String inputDate)
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
