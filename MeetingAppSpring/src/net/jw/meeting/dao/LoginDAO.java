package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.LoginMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Login;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class LoginDAO extends DAO<Login, QueryObject>{
	
	private static String TABLE_NAME = "login";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<Login> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<Login> list = jdbcTemplate.query(_query, new LoginMapper());
		return list;
		
	}
	
	private String prepareSqlQuery(QueryObject q)
	{
		String _query = "";
		
		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		String condition2 = q.getCondition2();
		
		if(searchCode.equals("loginId"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE userName ='"+condition1+"' AND password = '"+condition2+"' AND `status` = '"+AppConstant.ITEM_STATUS_ACTIVE+"'";
		
		
		return _query;
	}

	@Override
	public void insert(Login obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`userId`, `userName`, `password`,`createdById`, `createdByTime`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
		
		jdbcTemplate.update(_query, obj.getUid(),  obj.getUserName(), obj.getPassword(), obj.getCommon().getCreatedById(), obj.getCommon().getCreatedByTime(), obj.getCommon().getStatus());
	}

	@Override
	public void update(Login obj) {
		
		String _query = "UPDATE " + TABLE_NAME + " SET " 
				+ " `password` 		= ?," 
				+ " `status`		= ?," 
				+ " `updatedById`	= ?"
				+ " WHERE `userId` 	= ?";

		CommonDetails common = new CommonDetails();

		if (obj.getCommon() != null)
			common = obj.getCommon();
		

		jdbcTemplate.update(_query, obj.getPassword(), common.getStatus(), common.getUpdatedById(), obj.getUid());
	}

	@Override
	public void activate(Login obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `userId` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getUid());
		
	}

	@Override
	public void inactivate(Login obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `userId` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getUid());
		
	}


}
