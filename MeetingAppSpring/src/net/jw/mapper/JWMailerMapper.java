package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.JWMail;

public class JWMailerMapper implements RowMapper<JWMail>{

	@Override
	public JWMail mapRow(ResultSet rs, int row) throws SQLException {
		JWMail obj = new JWMail();
		
		try
		{
			obj.setMailId(rs.getInt("mailId"));
			obj.setFrom(rs.getString("from"));
			obj.setTo(rs.getString("to"));
			obj.setCc(rs.getString("cc"));
			obj.setBcc(rs.getString("bcc"));
			obj.setSubject(rs.getString("subject"));
			obj.setMessage(rs.getString("message"));
			obj.setSenderName(rs.getString("senderName"));
			obj.setSenderId(rs.getInt("senderId"));
			obj.setRecieverName(rs.getString("recieverName"));
			obj.setRecieverId(rs.getInt("recieverId"));
			obj.setReadStatus(rs.getInt("readStatus"));
			
			CommonDetails common = new CommonDetails();
			common.setCreatedById(rs.getInt("createdById"));
			common.setCreatedByTime(rs.getString("createdByTime"));
			
			obj.setCommon(common);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		
		
		
		return obj;
	}

}
