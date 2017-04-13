package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Location;

public class LocationMapper implements RowMapper<Location>{

	@Override
	public Location mapRow(ResultSet rs, int row) throws SQLException {
		
		Location obj = new Location();
		obj.setLid(rs.getInt("lid"));
		obj.setLocationName(rs.getString("locationName"));
		obj.setBuilding(rs.getString("building"));
		obj.setFloor(rs.getInt("floor"));
		obj.setAddress(rs.getString("address"));
		
		CommonDetails common = new CommonDetails();
		common.setStatus(rs.getString("status"));
		common.setCreatedById(rs.getInt("createdById"));
		common.setCreatedByTime(rs.getString("createdByTime"));
		common.setUpdatedById(rs.getInt("updatedById"));
		common.setUpdatedByTime(rs.getString("updatedByTime"));
		
		obj.setCommon(common);
		
		return obj;
	}

}
