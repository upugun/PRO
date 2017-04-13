package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Facilities;
import net.jw.meeting.model.Users;

public class FacilitiesMapper implements RowMapper<Facilities>{

	@Override
	public Facilities mapRow(ResultSet rs, int row) throws SQLException {
		
		Facilities obj = new Facilities();
		obj.setFid(rs.getInt("fid"));
		obj.setfName(rs.getString("fName"));
		obj.setOwner(rs.getInt("owner"));
		obj.setExt(rs.getInt("ext"));
		obj.setEmail(rs.getString("email"));
		obj.setDetails(rs.getString("details"));
		obj.setCountable(rs.getBoolean("countable"));
		
		
		CommonDetails common = new CommonDetails();
		common.setStatus(rs.getString("status"));
		common.setCreatedById(rs.getInt("createdById"));
		common.setCreatedByTime(rs.getString("createdByTime"));
		common.setUpdatedById(rs.getInt("updatedById"));
		common.setUpdatedByTime(rs.getString("updatedByTime"));
		
		obj.setCommon(common);
		

		Users admin = new Users();
		admin.setUid(rs.getInt("uid"));
		admin.setuName(rs.getString("uName"));
		admin.setuDep(rs.getString("uDep"));
		admin.setuBuild(rs.getString("uBuild"));
		admin.setuFloor(rs.getString("uFloor"));
		admin.setuRoll(rs.getInt("uRoll"));
		admin.setEmail(rs.getString("email"));
		admin.setMobile(rs.getString("mobile"));
		admin.setDetails(rs.getString("details"));
		
		obj.setAdmin(admin);
		
	
		
		return obj;
	}

}
