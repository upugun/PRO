package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Resources;

public class ResourcesMapper implements RowMapper<Resources>{

	@Override
	public Resources mapRow(ResultSet rs, int row) throws SQLException {
		
		Resources obj = new Resources();
		obj.setRid(rs.getInt("rid"));
		obj.setrName(rs.getString("rName"));
		obj.setrType(rs.getString("rType"));
		obj.setOwner(rs.getString("owner"));
		obj.setDetail(rs.getString("detail"));
		
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
