package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.BookingFacilitiesMapper;
import net.jw.meeting.model.BookingFacilities;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class BookingFacilitiesDAO extends DAO<BookingFacilities, QueryObject> {

	private static String TABLE_NAME = "booking_facilities";
	private static String FACILITY_TABLE_NAME = "facilities";

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<BookingFacilities> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);

		List<BookingFacilities> list = jdbcTemplate.query(_query, new BookingFacilitiesMapper());

		return list;

	}

	public List<BookingFacilities> fetch(int booking, int resId) {
		QueryObject q = new QueryObject();
		q.setSearchCode("searchByMeetingRom&Resource");
		q.setCondition1(String.valueOf(booking));
		q.setCondition2(String.valueOf(resId));
		String _query = prepareSqlQuery(q);

		List<BookingFacilities> list = jdbcTemplate.query(_query, new BookingFacilitiesMapper());

		return list;

	}

	private String prepareSqlQuery(QueryObject q) {
		String _query = "SELECT * FROM " + TABLE_NAME + " as b" + " INNER JOIN(SELECT * FROM " + FACILITY_TABLE_NAME
				+ ") as f on f.fid = b.facilityId";;

		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		// String condition2 = q.getCondition2();

		if (searchCode.equals("searchOnlyActive"))
			_query = _query+" WHERE b.`status` = '"+ AppConstant.ITEM_STATUS_ACTIVE
					+ "' AND `mrid` = " + condition1 + "";
		else if (searchCode.equals("searchByStatus"))
			_query = _query+" WHERE b.`status` = '" + condition1 + "'";
		else if (searchCode.equals("searchByBookingId"))
			_query =  _query+ " WHERE b. `bookingId` = '" + condition1 + "'";
		else
			_query = "SELECT * FROM " + TABLE_NAME + "";

		_query = _query + " ORDER BY bookingId";

		return _query;
	}

	@Override
	public void insert(BookingFacilities obj) {

		String _query = "INSERT INTO " + TABLE_NAME + " (`bookingId`, `facilityId`, `count`,  `status`) VALUES (?, ?, ? , ?);";

		jdbcTemplate.update(_query, obj.getBookingId(), obj.getFacilityId(), obj.getCount(),obj.getStatus());

	}

	@Override
	public void update(BookingFacilities obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + " `bookingId` 	= ?," + " `facilityId` 		= ?,"  + " `count` 		= ?,"
				+ " `status`			= ?,";

		jdbcTemplate.update(_query, obj.getBookingId(), obj.getFacilityId(), obj.getCount(), obj.getStatus());

	}

	@Override
	public void activate(BookingFacilities obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `bookingId` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getBookingId());

	}

	@Override
	public void inactivate(BookingFacilities obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `bookingId` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getBookingId());

	}

	public void delete(BookingFacilities obj) {
		String _query = "DELETE FROM " + TABLE_NAME + " WHERE `bookingId` 		= ? AND `resourceId` =?";

		jdbcTemplate.update(_query, obj.getBookingId(), obj.getFacilityId());

	}

	public void delete(int bookingId) {
		String _query = "DELETE FROM " + TABLE_NAME + " WHERE `bookingId` 		= ?";

		jdbcTemplate.update(_query, bookingId);

	}

}
