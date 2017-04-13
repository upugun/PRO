package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


import net.jw.mapper.ResourcesMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Resources;
import net.jw.system.AppConstant;

public class ResourcesDAO extends DAO<Resources, QueryObject>{
	
	private static String TABLE_NAME = "resources";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<Resources> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Resources> list = jdbcTemplate.query(_query, new ResourcesMapper());
		
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
			
		_query = _query + " ORDER BY rid";
		
		return _query;
	}

	@Override
	public void insert(Resources obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`rName`, `rType`, `owner`, `detail`, `status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
		{
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}
		
		jdbcTemplate.update(_query, obj.getrName(), obj.getrType(), obj.getOwner(), obj.getDetail(), common.getStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(Resources obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `rName` 	= ?,"
				+ " `rType` 		= ?,"
				+ " `owner`			= ?,"
				+ " `detail`		= ?,"
				+ " `status`		= ?,"
				+ " `updatedById`	= ?"
				+ " WHERE `rid` 	= ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getrName(), obj.getrType(), obj.getOwner(), obj.getDetail(), common.getStatus(), common.getUpdatedById(), obj.getRid());
		
	}

	@Override
	public void activate(Resources obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `rid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getRid());
		
	}

	@Override
	public void inactivate(Resources obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `rid` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getRid());
		
	}


}
