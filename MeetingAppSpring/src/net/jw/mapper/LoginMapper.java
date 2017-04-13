package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Login;

public class LoginMapper implements RowMapper<Login>{

	@Override
	public Login mapRow(ResultSet rs, int row) throws SQLException {
		
		Login obj = new Login();
		
		try
		{
			obj.setUid(rs.getInt("userId"));
			obj.setUserName(rs.getString("userName"));
			obj.setPassword(rs.getString("password"));
			
			CommonDetails common = new CommonDetails();
			common.setStatus(rs.getString("status"));
			common.setCreatedById(rs.getInt("createdById"));
			common.setCreatedByTime(rs.getString("createdByTime"));
			common.setUpdatedById(rs.getInt("updatedById"));
			common.setUpdatedByTime(rs.getString("updatedByTime"));
			
			obj.setCommon(common);
		}
		catch(Exception e)
		{
			System.out.println("LoginMapper.mapRow()" + e.getMessage());
		}
		
		return obj;
	}

}
