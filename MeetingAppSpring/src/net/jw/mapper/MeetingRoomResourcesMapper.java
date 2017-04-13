package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import net.jw.meeting.model.MeetingRoomResources;

public class MeetingRoomResourcesMapper implements RowMapper<MeetingRoomResources>{

	@Override
	public MeetingRoomResources mapRow(ResultSet rs, int row) throws SQLException {
		
		MeetingRoomResources obj = new MeetingRoomResources();
		obj.setMrid(rs.getInt("mrid"));
		obj.setResourceId(rs.getInt("resourceId"));
		obj.setStatus(rs.getString("status"));
			
		
		return obj;
	}

}
