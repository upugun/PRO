package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


import net.jw.mapper.PermissionMatrixMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.PermissionMatrix;
import net.jw.system.AppConstant;

public class PermissionMatrixDAO extends DAO <PermissionMatrix, QueryObject>{
	
	private static String TABLE_NAME = "permission_matrix";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<PermissionMatrix> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<PermissionMatrix> list = jdbcTemplate.query(_query, new PermissionMatrixMapper());
		
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
	public void insert(PermissionMatrix obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`roleId`, `permissionId`, `status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?,?);";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
		{
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}
		
		jdbcTemplate.update(_query,obj.getRoleId(), obj.getPermissionId(), common.getStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(PermissionMatrix obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `roleId` 		= ?,"
				+ " `permissionId` 	= ?,"
				+ " `status` 		= ?,"
				+ " `updatedById`	= ?"
				+ " WHERE `roleId` 	= ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getRoleId(),obj.getPermissionId(), common.getStatus(), common.getUpdatedById(), obj.getRoleId());
		
	}

	@Override
	public void activate(PermissionMatrix obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `roleId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getRoleId());
		
	}

	@Override
	public void inactivate(PermissionMatrix obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `roleId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getRoleId());
		
	}
	
	public void delete(PermissionMatrix obj) {
		String _query = "DELETE FROM " +TABLE_NAME+" WHERE `roleId` = ? AND `permissionId` = ?";
		
		
		jdbcTemplate.update(_query, obj.getRoleId(), obj.getPermissionId());
		
	}


}
