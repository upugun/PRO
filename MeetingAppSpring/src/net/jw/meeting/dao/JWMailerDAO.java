package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.JWMailerMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.QueryObject;
import net.jw.system.AppConstant;

public class JWMailerDAO extends DAO<JWMail, QueryObject>{
	
	private static String TABLE_NAME = "mail";
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;  
	  
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	} 

	@Override
	public List<JWMail> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);
		
		List<JWMail> list = jdbcTemplate.query(_query, new JWMailerMapper());
		
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
		else if(searchCode.equals("searchByRecieverIdAndReadStatus"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `recieverId` = '"+condition1+"' AND `readStatus`= "+condition2+"";
		else if(searchCode.equals("searchByRecieverId"))
			_query = "SELECT * FROM " +TABLE_NAME+ " WHERE `recieverId` = '"+condition1+"'";
		else 
			_query = "SELECT * FROM " +TABLE_NAME+ "";
			
		_query = _query + " ORDER BY mailId desc";
		return _query;
	}

	@Override
	public void insert(JWMail obj) {
		
		String _query = "INSERT INTO " +TABLE_NAME+ " (`from`, `to`, `cc`, `bcc`, `subject`, `message`,`senderName`, `senderId`, `recieverName`,"
				+ "`recieverId`,`readStatus`,`createdById`,`createdByTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
		{
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}
		
		jdbcTemplate.update(_query, obj.getFrom(),  obj.getTo(), obj.getCc(), obj.getBcc(),obj.getSubject(),obj.getMessage(),obj.getSenderName(),obj.getSenderId(), obj.getRecieverName(), obj.getRecieverId(), obj.getReadStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(JWMail obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ " `from` 			= ?,"
				+ " `to` 			= ?,"
				+ " `cc`			= ?,"
				+ " `bcc`			= ?,"
				+ " `status`		= ?,"
				+ " `subject`		= ?,"
				+ " `message`		= ?,"
				+ " `senderName`	= ?,"
				+ " `senderId`		= ?,"
				+ " `recieverName`	= ?,"
				+ " `recieverId`	= ?,"
				+ " `readStatus`	= ?"
				+ " WHERE `mailId` 	= ?";
		
		CommonDetails common = new CommonDetails();
		
		if(obj.getCommon()!=null)
			common = obj.getCommon();
		
		jdbcTemplate.update(_query, obj.getFrom(), obj.getTo(), obj.getCc(), obj.getBcc(),obj.getSubject(),obj.getMessage(),obj.getSenderName(),obj.getSenderId(),obj.getRecieverName(),obj.getRecieverId(), obj.getReadStatus(), common.getStatus(), common.getUpdatedById());
		
	}

	@Override
	public void activate(JWMail obj) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `mailId` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getMailId());
		
	}

	@Override
	public void inactivate(JWMail obj) {
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`status`			= ?"
				+ "WHERE `id` 		= ?";
		
		
		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getMailId());
		
	}
	
	public void setReadStatus(int mailId, int readStatus) {
		
		String _query = "UPDATE " +TABLE_NAME+ " SET "
				+ "`readStatus`			= ?"
				+ " WHERE `mailId` 		= ?";
		
		
		jdbcTemplate.update(_query, readStatus,mailId);
		
	}
	
	public void delete(JWMail obj) {
		String _query = "DELETE FROM " +TABLE_NAME+ " WHERE `mailId` =?";
		
		
		jdbcTemplate.update(_query,obj.getMailId());
		
	}


}
