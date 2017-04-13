package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Permission;

public class PermissionMapper implements RowMapper<Permission>{

	@Override
	public Permission mapRow(ResultSet rs, int row) throws SQLException {
		
		Permission obj = new Permission();
		try
		{
		obj.setoId(rs.getInt("oId"));
		obj.setOptionName(rs.getString("optionName"));
		obj.setOptionType(rs.getString("optionType"));
		obj.setOptionIcon(rs.getString("optionIcon"));
		obj.setOptionLink(rs.getString("optionLink"));
		obj.setParentID(rs.getString("parentID"));
		obj.setOptionOrder(rs.getInt("optionOrder"));
		
		
		
		
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
			e.printStackTrace();
		}
		
		
		return obj;
	}

}
