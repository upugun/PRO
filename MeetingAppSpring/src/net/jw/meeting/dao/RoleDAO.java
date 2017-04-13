package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


import net.jw.mapper.RoleMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Role;
import net.jw.system.AppConstant;

public class RoleDAO extends DAO <Role, QueryObject>{
	
	private static String TABLE_NAME = "role";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<Role> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Role> list = jdbcTemplate.query(_query, new RoleMapper());
		
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
			
		_query = _query + " ORDER BY roleId";
		
		return _query;
	}

	@Override
	public void insert(Role obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`roleName`, `status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?);";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
		{
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}
		
		jdbcTemplate.update(_query, obj.getRoleName(), common.getStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(Role obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `roleName` 		= ?,"
				+ " `status` 		= ?,"
				+ " `updatedById`	= ?"
				+ " WHERE `roleId` 	= ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getRoleName(), common.getStatus(), common.getUpdatedById(), obj.getRoleId());
		
	}

	@Override
	public void activate(Role obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `roleId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getRoleId());
		
	}

	@Override
	public void inactivate(Role obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `roleId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getRoleId());
		
	}


}
