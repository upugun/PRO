package net.jw.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.jw.meeting.model.BookingResources;



public class BookingResourcesMapper implements RowMapper<BookingResources>{

	@Override
	public BookingResources mapRow(ResultSet rs, int row) throws SQLException {
		
		BookingResources obj = new BookingResources();
		obj.setBookingId(rs.getInt("bookingId"));
		obj.setResourceId(rs.getInt("resourceId"));
		obj.setStatus(rs.getString("status"));
		
		
	
		
		
		
		return obj;
	}

}
