package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.PermissionMatrix;

public class PermissionMatrixMapper implements RowMapper<PermissionMatrix>{

	@Override
	public PermissionMatrix mapRow(ResultSet rs, int row) throws SQLException {
		
		PermissionMatrix obj = new PermissionMatrix();
		try
		{
		obj.setRoleId(rs.getInt("roleId"));
		obj.setPermissionId(rs.getInt("permissionId"));
		
		
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
