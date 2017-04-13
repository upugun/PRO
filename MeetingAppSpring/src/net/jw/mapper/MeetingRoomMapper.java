package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Location;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.Users;


public class MeetingRoomMapper implements RowMapper<MeetingRoom>{

	@Override
	public MeetingRoom mapRow(ResultSet rs, int row) throws SQLException {
		
		MeetingRoom obj = new MeetingRoom();
		obj.setmId(rs.getInt("mId"));
		obj.setmRoomName(rs.getString("mRoomName"));
		obj.setmLocationId(rs.getInt("mLocationId"));
		obj.setSeatingCapacity(rs.getInt("seatingCapacity"));
		obj.setAdminId(rs.getInt("adminId"));
		obj.setTp(rs.getInt("tp"));
		obj.setNotes(rs.getString("notes"));
		obj.setApproval(rs.getBoolean("approval"));
		
		CommonDetails common = new CommonDetails();
		common.setStatus(rs.getString("status"));
		common.setCreatedById(rs.getInt("createdById"));
		common.setCreatedByTime(rs.getString("createdByTime"));
		common.setUpdatedById(rs.getInt("updatedById"));
		common.setUpdatedByTime(rs.getString("updatedByTime"));
		
		obj.setCommon(common);
		
		Location location = new Location();
		location.setLid(rs.getInt("lid"));
		location.setLocationName(rs.getString("locationName"));
		location.setBuilding(rs.getString("building"));
		location.setFloor(rs.getInt("floor"));
		location.setAddress(rs.getString("address"));
		
		obj.setLocation(location);
		
		
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
