package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.BookingResourcesMapper;
import net.jw.meeting.model.BookingResources;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class BookingResourcesDAO extends DAO<BookingResources, QueryObject> {

	private static String TABLE_NAME = "booking_resources";

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<BookingResources> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);

		List<BookingResources> list = jdbcTemplate.query(_query, new BookingResourcesMapper());

		return list;

	}

	public List<BookingResources> fetch(int booking, int resId) {
		QueryObject q = new QueryObject();
		q.setSearchCode("searchByMeetingRom&Resource");
		q.setCondition1(String.valueOf(booking));
		q.setCondition2(String.valueOf(resId));
		String _query = prepareSqlQuery(q);

		List<BookingResources> list = jdbcTemplate.query(_query, new BookingResourcesMapper());

		return list;

	}

	private String prepareSqlQuery(QueryObject q) {
		String _query = "";

		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		// String condition2 = q.getCondition2();

		if (searchCode.equals("searchOnlyActive"))
			_query = "SELECT * FROM " + TABLE_NAME + " WHERE `status` = '" + AppConstant.ITEM_STATUS_ACTIVE
					+ "' AND `bookingid` = " + condition1 + "";
		else if (searchCode.equals("searchByStatus"))
			_query = "SELECT * FROM " + TABLE_NAME + " WHERE `status` = '" + condition1 + "'";
		else if (searchCode.equals("searchByBookingId"))
			_query = "SELECT * FROM " + TABLE_NAME + " WHERE `bookingId` = '" + condition1 + "'";
		else
			_query = "SELECT * FROM " + TABLE_NAME + "";

		_query = _query + " ORDER BY bookingid";

		return _query;
	}

	@Override
	public void insert(BookingResources obj) {

		String _query = "INSERT INTO " + TABLE_NAME + " (`bookingId`, `resourceId`, `status`) VALUES (?, ?, ?);";

		jdbcTemplate.update(_query, obj.getBookingId(), obj.getResourceId(), obj.getStatus());

	}

	@Override
	public void update(BookingResources obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + " `bookingId` 	= ?," + " `resourceId` 		= ?,"
				+ " `status`			= ?,";

		jdbcTemplate.update(_query, obj.getBookingId(), obj.getResourceId(), obj.getStatus());

	}

	@Override
	public void activate(BookingResources obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `bookingid` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getBookingId());

	}

	@Override
	public void inactivate(BookingResources obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `bookingid` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getBookingId());

	}

	public void delete(BookingResources obj) {
		String _query = "DELETE FROM " + TABLE_NAME + " WHERE `bookingid` 		= ? AND `resourceId` =?";

		jdbcTemplate.update(_query, obj.getBookingId(), obj.getResourceId());

	}

	public void delete(int bookingId) {
		String _query = "DELETE FROM " + TABLE_NAME + " WHERE `bookingid` 		= ?";

		jdbcTemplate.update(_query, bookingId);

	}

}
