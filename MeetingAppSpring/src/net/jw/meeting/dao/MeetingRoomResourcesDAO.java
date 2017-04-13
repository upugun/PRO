package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.MeetingRoomResourcesMapper;
import net.jw.meeting.model.MeetingRoomResources;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class MeetingRoomResourcesDAO extends DAO<MeetingRoomResources, QueryObject>{
	
	private static String TABLE_NAME = "meetingroom_resources";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<MeetingRoomResources> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<MeetingRoomResources> list = jdbcTemplate.query(_query, new MeetingRoomResourcesMapper());
		
		return list;
		
	}
	
	public List<MeetingRoomResources> fetch(int mId, int resId) {
		QueryObject q = new QueryObject();
		q.setSearchCode("searchByMeetingRom&Resource");
		q.setCondition1(String.valueOf(mId));
		q.setCondition2(String.valueOf(resId));
		String _query = prepareSqlQuery(q);
		
		List<MeetingRoomResources> list = jdbcTemplate.query(_query, new MeetingRoomResourcesMapper());
		
		return list;
		
	}
	
	private String prepareSqlQuery(QueryObject q)
	{
		String _query = "";
		
		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		//String condition2 = q.getCondition2();
		
		if(searchCode.equals("searchOnlyActive"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"' AND `mrid` = "+condition1+"";
		else if(searchCode.equals("searchByStatus"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+condition1+"'";
		else 
			_query = "SELECT * FROM " +TABLE_NAME+ "";
			
		_query = _query + " ORDER BY mrid";
		
		return _query;
	}

	@Override
	public void insert(MeetingRoomResources obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`mrid`, `resourceId`, `status`) VALUES (?, ?, ?);";
		
		
		jdbcTemplate.update(_query, obj. getMrid(), obj.getResourceId(), obj.getStatus());

	}

	@Override
	public void update(MeetingRoomResources obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `mrid` 	= ?,"
				+ " `resourceId` 		= ?,"
				+ " `status`			= ?,";
	
		jdbcTemplate.update(_query, obj.getMrid(), obj.getResourceId(), obj.getStatus());
		
	}

	@Override
	public void activate(MeetingRoomResources obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `mrid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getMrid());
		
	}

	@Override
	public void inactivate(MeetingRoomResources obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `mrid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getMrid());
		
	}
	
	public void delete(MeetingRoomResources obj) {
		String _query = "DELETE FROM " +TABLE_NAME 
				+ " WHERE `mrid` 		= ? AND `resourceId` =?";
		
		
		jdbcTemplate.update(_query, obj.getMrid(), obj.getResourceId());
		
	}
	
	public void delete(int meetingRoomId) {
		String _query = "DELETE FROM " +TABLE_NAME 
				+ " WHERE `mrid` 		= ?";
		
		
		jdbcTemplate.update(_query, meetingRoomId);
		
	}


}
