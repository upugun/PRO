package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.BookingFacilities;
import net.jw.meeting.model.Facilities;



public class BookingFacilitiesMapper implements RowMapper<BookingFacilities>{

	@Override
	public BookingFacilities mapRow(ResultSet rs, int row) throws SQLException {
		
		BookingFacilities obj = new BookingFacilities();
		obj.setBookingId(rs.getInt("bookingId"));
		obj.setFacilityId(rs.getInt("facilityId"));
		obj.setCount(rs.getInt("count"));
		obj.setStatus(rs.getString("status"));
		
		Facilities facilities = new Facilities();
		facilities.setFid(rs.getInt("fid"));
		facilities.setfName( rs.getString("fname"));
		facilities.setOwner(rs.getInt("owner"));
		facilities.setDetails(rs.getString("details"));
		facilities.setCountable(rs.getBoolean("countable"));
		
		  obj.setFacilities(facilities);
		
	
		
		
		
		return obj;
	}

}
