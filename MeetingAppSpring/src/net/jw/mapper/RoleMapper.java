package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Role;

public class RoleMapper implements RowMapper<Role>{

	@Override
	public Role mapRow(ResultSet rs, int row) throws SQLException {
		
		Role obj = new Role();
		obj.setRoleId(rs.getInt("roleId"));
		obj.setRoleName(rs.getString("roleName"));
		
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
