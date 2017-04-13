package net.jw.meeting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Statement;

import net.jw.mapper.BookingMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.Booking;
import net.jw.meeting.model.BookingFacilities;
import net.jw.meeting.model.BookingResources;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class BookingDAO extends DAO<Booking, QueryObject>{
	
	private static String TABLE_NAME = "booking";
	private static String USER_TABLE = "users";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<Booking> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Booking> list = jdbcTemplate.query(_query, new BookingMapper());
		
		MeetingRoomDAO roomDAO = new MeetingRoomDAO();
		roomDAO.setDataSource(this.dataSource);
		
		BookingResourcesDAO resDAO = new BookingResourcesDAO();
		resDAO.setDataSource(this.dataSource);
		
		BookingFacilitiesDAO facilitiesDAO = new BookingFacilitiesDAO();
		facilitiesDAO.setDataSource(this.dataSource);
		
		QueryObject mq = new QueryObject();
		mq.setSearchCode("searchById");
		
		QueryObject rq = new QueryObject();
		rq.setSearchCode("searchByBookingId");
		
		QueryObject fq = new QueryObject();
		fq.setSearchCode("searchByBookingId");
		
		for(int i=0; i<list.size(); i++)
		{
			mq.setCondition1(String.valueOf(list.get(i).getMeetingRoomId()));
			
			List<MeetingRoom> meeetingList = roomDAO.fetch(mq);
			
			if(meeetingList.size()>0)
				list.get(i).setMeetingRoom(meeetingList.get(0));
			
			rq.setCondition1(String.valueOf(list.get(i).getBid()));
			fq.setCondition1(String.valueOf(list.get(i).getBid()));
			
			List<BookingResources> resList = resDAO.fetch(rq);
			list.get(i).setResourcesList(resList);
			
			List<BookingFacilities> facList= facilitiesDAO.fetch(fq);
			list.get(i).setFacilityList(facList);
		}
		
		return list;
		
	}
	
	
	private String prepareSqlQuery(QueryObject q)
	{
		String _query = "SELECT * FROM " +TABLE_NAME+ " as b INNER JOIN(SELECT uid,uName,mobile,email FROM "+USER_TABLE+") as u on u.uid = b.createdById";
		
		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		String condition2 = q.getCondition2();
		String condition3 = q.getCondition3();
		String condition4 = q.getCondition4();
		
		if(searchCode.equals("searchOnlyActive"))
			_query = _query + " WHERE b.`status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"'";
		else if(searchCode.equals("searchById"))
		_query = _query + " WHERE b.`bid` = '"+condition1+"'";
		else if (searchCode.equals("searchByStatus"))
			_query = _query + " WHERE b.`bookingStatus` = '" + condition1 + "'";
		else if(searchCode.equals("searchByStatusAndmeetingID"))
			_query = _query +" WHERE b.`bookingStatus` = '"+condition1+"' AND `meetingRoomId`='"+condition2+"'";
		else if(searchCode.equals("searchAlreadyBooked"))
			_query = _query +" WHERE b.`bookingStatus` <> '"+AppConstant.BOOKING_STATUS_REJECTED+"' AND b.`bid`<> '"+condition4+"' AND b.`meetingRoomId`='"+condition1+"' AND (b.`start`< '"+condition3+"' and b.`end`>'"+condition2+"') ";
		else if(searchCode.equals("searchBycreatedById"))
			_query = _query + " WHERE b.`createdById` = '"+condition2+"' AND b.`bookingStatus` = '" + condition1 + "'";	
		_query = _query + " ORDER BY b.bid desc";
		//System.out.println("BookingDAO.prepareSqlQuery()" + _query);
		return _query;
	}
	
	public void create(Booking obj)
	{
		QueryObject q = new QueryObject();
		q.setSearchCode("searchById");
		q.setCondition1(String.valueOf(obj.getBid()));
		
		if(fetch(q).size()>0)
			update(obj);
		else
			insert(obj);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insert(final Booking obj) {
		
		final String _query = "INSERT INTO " +TABLE_NAME+ " (`meetingRoomId`, `title`, `start`, `end`,`allDay`, `remarks`, `repeating`, `bookingStatus`,`status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?);";
		
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		if(obj.getCommon()!=null)
		{
			obj.getCommon().setCreatedByTime(getCurrentDate());
		}
		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(_query.toString(), Statement.RETURN_GENERATED_KEYS);
				int x = 0;
                ps.setInt(++x, obj.getMeetingRoom().getmId());
                ps.setString(++x, obj.getTitle());
                ps.setString(++x, obj.getStart());
                ps.setString(++x, obj.getEnd());
                ps.setBoolean(++x, obj.isAllDay());
                ps.setString(++x, obj.getRemarks());
                ps.setBoolean(++x, obj.isRepeating());
                ps.setString(++x, obj.getBookingStatus());
                ps.setString(++x, obj.getCommon().getStatus());
                ps.setInt(++x, obj.getCommon().getCreatedById());
                ps.setString(++x, obj.getCommon().getCreatedByTime());
                return ps;
			}
		},holder);
		
		int key = holder.getKey().intValue();
		
		BookingResourcesDAO resDAO = new BookingResourcesDAO();
		resDAO.setDataSource(this.dataSource);
		
		BookingFacilitiesDAO facDAO = new BookingFacilitiesDAO();
		facDAO.setDataSource(this.dataSource);
		
		for(int i=0; i<obj.getResourcesList().size(); i++)
		{
			BookingResources res = obj.getResourcesList().get(i);
			res.setBookingId(key);
			
			resDAO.insert(res);
		}
		
		for(int j=0; j<obj.getFacilityList().size(); j++)
		{
			BookingFacilities fac = obj.getFacilityList().get(j);
			fac.setBookingId(key);
			
			facDAO.insert(fac);
		}
			

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void update(Booking obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `meetingRoomId` 	= ?,"
				+ " `title` 		= ?,"
				+ " `start`			= ?,"
				+ " `end`		= ?,"
				+ " `allDay` 	= ?,"
				+ " `remarks` 		= ?,"
				+ " `repeating`			= ?,"
				+ " `bookingStatus`		= ?,"
				+ " `status`		= ?,"
				+ " `updatedById`	= ?"
				+ " WHERE `bid` 	= ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getMeetingRoom().getmId(), obj.getTitle(), obj.getStart(), obj.getEnd(),obj.isAllDay(), obj.getRemarks(), obj.isRepeating(), obj.getBookingStatus(), common.getStatus(), common.getUpdatedById(), obj.getBid());
		
		BookingResourcesDAO resDAO = new BookingResourcesDAO();
		resDAO.setDataSource(this.dataSource);
		resDAO.delete(obj.getBid());
		
		BookingFacilitiesDAO facDAO = new BookingFacilitiesDAO();
		facDAO.setDataSource(this.dataSource);
		facDAO.delete(obj.getBid());
		
		for(int i=0; i<obj.getResourcesList().size(); i++)
		{
			BookingResources res = obj.getResourcesList().get(i);
			res.setBookingId(obj.getBid());
			
			resDAO.insert(res);
		}
		
		for(int j=0; j<obj.getFacilityList().size(); j++)
		{
			BookingFacilities fac = obj.getFacilityList().get(j);
			fac.setBookingId(obj.getBid());
			
			facDAO.insert(fac);
		}
		
		
	}

	@Override
	public void activate(Booking obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`bookingStatus`			= ?"
				+ "WHERE `bid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.BOOKING_STATUS_APPROVED, obj.getBid());
		
	}

	@Override
	public void inactivate(Booking obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`bookingStatus`			= ?"
				+ "WHERE `bid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.BOOKING_STATUS_REJECTED, obj.getBid());
		
	}


	public void delete(Booking obj) {
		String _query = "DELETE FROM " + TABLE_NAME + " WHERE `bid` 		= ? ";

		jdbcTemplate.update(_query, obj.getBid());

	}
}
