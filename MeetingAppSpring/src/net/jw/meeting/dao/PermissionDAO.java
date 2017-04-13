package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.PermissionMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Permission;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class PermissionDAO extends DAO<Permission, QueryObject>{
	
	private static String TABLE_NAME 				= "permission";
	private static String TABLE_PERMISSION_MATRIX 	= "permission_matrix";
	private static String TABLE_USER 				= "users";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<Permission> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Permission> list = jdbcTemplate.query(_query, new PermissionMapper());
		
		return list;
		
	}
	
	public List<Permission> getMainOptionsForNavigation(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Permission> list = jdbcTemplate.query(_query, new PermissionMapper());
		for(int i=0; i<list.size(); i++)
		{
			Permission obj = list.get(i);
			if(obj.getOptionType().equals(AppConstant.PERMISSION_TYPE_MAIN))
			{
				QueryObject sq = new QueryObject();
				sq.setSearchCode("searchSubObjectsByParentIdAndRole");
				sq.setCondition1(q.getCondition1());// user id
				sq.setCondition2(String.valueOf(obj.getoId()));
				
				List<Permission> subList = fetch(sq);
				
				obj.setSuboptions(subList);
			}
		}
		
		return list;
		
	}
	
	public List<Permission> getMainOptions(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Permission> list = jdbcTemplate.query(_query, new PermissionMapper());
		for(int i=0; i<list.size(); i++)
		{
			Permission obj = list.get(i);
			if(obj.getOptionType().equals(AppConstant.PERMISSION_TYPE_MAIN))
			{
				QueryObject sq = new QueryObject();
				sq.setSearchCode("searchSubObjectsByParentId");
				sq.setCondition1(String.valueOf(obj.getoId()));
				
				List<Permission> subList = fetch(sq);
				
				obj.setSuboptions(subList);
			}
		}
		
		return list;
		
	}
	
	private String prepareSqlQuery(QueryObject q)
	{
		String _query = "";
		
		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		String condition2 = q.getCondition2();
		
		if(searchCode.equals("searchOnlyActive"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"'";
		else if(searchCode.equals("searchByStatus"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+condition1+"'";
		else if(searchCode.equals("searchMain"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"' AND `optionType` = '"+AppConstant.PERMISSION_TYPE_MAIN+"'";
		else if(searchCode.equals("searchMainByRole"))
			_query = "SELECT * FROM " +TABLE_NAME+ " as p INNER JOIN(SELECT * FROM "+TABLE_PERMISSION_MATRIX+" as pt INNER JOIN(SELECT uRoll, uid FROM "+TABLE_USER+" WHERE uid ='"+condition1+"') as u on u.uRoll=pt.roleId) as pm on pm.permissionId=p.oId  WHERE p.`status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"' AND p.`optionType` = '"+AppConstant.PERMISSION_TYPE_MAIN+"'";
		else if(searchCode.equals("searchSubObjectsByParentId"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"' AND `parentID` ='"+condition1+"' AND `optionType` = '"+AppConstant.PERMISSION_TYPE_SUB+"'";
		else if(searchCode.equals("searchSubObjectsByParentIdAndRole"))
			_query = "SELECT * FROM " +TABLE_NAME+ " as p INNER JOIN(SELECT * FROM "+TABLE_PERMISSION_MATRIX+" as pt INNER JOIN(SELECT uRoll, uid FROM "+TABLE_USER+" WHERE uid ='"+condition1+"') as u on u.uRoll=pt.roleId) as pm on pm.permissionId=p.oId  WHERE p.`status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"' AND `parentID` ='"+condition2+"' AND `optionType` = '"+AppConstant.PERMISSION_TYPE_SUB+"'";
		else if(searchCode.equals("searchByStatus"))
			_query = _query +" WHERE p.`status` = '"+condition1+"'";
		
		else 
			_query = "SELECT * FROM " +TABLE_NAME+ "";
			
		_query = _query + " ORDER BY optionOrder";
		
		return _query;
	}

	@Override
	public void insert(Permission obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`optionName`, `optionType`, `optionIcon`,`optionOrder`,`optionLink`, `parentId`, `status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?, ?, ?, ?,?,?);";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
		{
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}
		
		jdbcTemplate.update(_query, obj.getOptionName(), obj.getOptionType(), obj.getOptionIcon(),obj.getOptionOrder(),obj.getOptionLink(), obj.getParentID(), common.getStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(Permission obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `optionName` 	     = ?,"
				+ " `optionType` 		 = ?,"
				+ " `optionIcon` 		 = ?,"
				+ " `optionOrder` 		 = ?,"
				+ " `optionLink` 		 = ?,"
				+ " `parentID`			 = ?,"
				+ " `status`		      = ?,"
				+ " `updatedById`	       = ?"
				+ " WHERE `oId` 	     = ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getOptionName(),obj.getOptionType(),obj.getOptionIcon(),obj.getOptionOrder(),obj.getOptionLink(),obj.getParentID(),common.getStatus(), common.getUpdatedById(), obj.getoId());
		
	}

	@Override
	public void activate(Permission obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `oId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getoId());
		
	}

	@Override
	public void inactivate(Permission obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `oId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getoId());
		
	}


}
