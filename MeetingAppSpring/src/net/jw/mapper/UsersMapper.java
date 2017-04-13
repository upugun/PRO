package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Login;
import net.jw.meeting.model.Role;
import net.jw.meeting.model.Users;

public class UsersMapper implements RowMapper<Users>{

	@Override
	public Users mapRow(ResultSet rs, int row) throws SQLException {
		
		Users obj = new Users();
		obj.setUid(rs.getInt("uid"));
		obj.setuName(rs.getString("uName"));
		obj.setuDep(rs.getString("uDep"));
		obj.setuBuild(rs.getString("uBuild"));
		obj.setuFloor(rs.getString("uFloor"));
		obj.setuRoll(rs.getInt("uRoll"));
		obj.setEmail(rs.getString("email"));
		obj.setMobile(rs.getString("mobile"));
		obj.setDetails(rs.getString("details"));
		
		CommonDetails common = new CommonDetails();
		common.setStatus(rs.getString("status"));
		common.setCreatedById(rs.getInt("createdById"));
		common.setCreatedByTime(rs.getString("createdByTime"));
		common.setUpdatedById(rs.getInt("updatedById"));
		common.setUpdatedByTime(rs.getString("updatedByTime"));
		
		obj.setCommon(common);
		
		Role role = new Role();
		role.setRoleId(rs.getInt("roleId"));
		role.setRoleName(rs.getString("roleName"));
		
		obj.setRole(role);
		
		Login login = new Login();
		login.setUid(rs.getInt("userId"));
		login.setUserName(rs.getString("userName"));
		login.setPassword(rs.getString("password"));
		
		obj.setLogin(login);
		
		
		return obj;
	}

}
