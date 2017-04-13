package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.LocationMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Location;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class LocationDAO extends DAO<Location, QueryObject>{
	
	private static String TABLE_NAME = "locations";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<Location> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Location> list = jdbcTemplate.query(_query, new LocationMapper());
		
		return list;
		
	}
	
	private String prepareSqlQuery(QueryObject q)
	{
		String _query = "";
		
		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		//String condition2 = q.getCondition2();
		
		if(searchCode.equals("searchOnlyActive"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"'";
		else if(searchCode.equals("searchByStatus"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+condition1+"'";
		else 
			_query = "SELECT * FROM " +TABLE_NAME+ "";
			
		_query = _query + " ORDER BY lid";
		
		return _query;
	}

	@Override
	public void insert(Location obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`locationName`, `building`, `floor`, `address`, `status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
		{
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}
		
		jdbcTemplate.update(_query, obj.getLocationName(), obj.getBuilding(), obj.getFloor(), obj.getAddress(), common.getStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(Location obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `locationName` 	= ?,"
				+ " `building` 		= ?,"
				+ " `floor`			= ?,"
				+ " `address`		= ?,"
				+ " `status`		= ?,"
				+ " `updatedById`	= ?"
				+ " WHERE `lid` 	= ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getLocationName(), obj.getBuilding(), obj.getFloor(), obj.getAddress(), common.getStatus(), common.getUpdatedById(), obj.getLid());
		
	}

	@Override
	public void activate(Location obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `lid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getLid());
		
	}

	@Override
	public void inactivate(Location obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `lid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getLid());
		
	}


}
