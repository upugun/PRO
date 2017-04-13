package net.jw.meeting.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.jw.mapper.FacilitiesMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Facilities;
import net.jw.system.AppConstant;

public class FacilitiesDAO extends DAO<Facilities, QueryObject> {

	private static String TABLE_NAME = "facilities";
	private static String USER_TABLE = "users";

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<Facilities> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);

		List<Facilities> list = jdbcTemplate.query(_query, new FacilitiesMapper());

		return list;

	}

	private String prepareSqlQuery(QueryObject q) {
		String _query = "SELECT * FROM " + TABLE_NAME + " as f" + " INNER JOIN(SELECT * FROM " + USER_TABLE
				+ ") as u on u.uid = f.owner";

		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		// String condition2 = q.getCondition2();

		if (searchCode.equals("searchOnlyActive"))
			_query = _query+" WHERE f.`status` = '"+ AppConstant.ITEM_STATUS_ACTIVE + "'";
		else if (searchCode.equals("searchByStatus"))
			_query =  _query+" WHERE f.`status` = '" + condition1 + "'";
		else
			_query = "SELECT * FROM " + TABLE_NAME + "";

		_query = _query + " ORDER BY fid";

		return _query;
	}

	@Override
	public void insert(Facilities obj) {

		String _query = "INSERT INTO " + TABLE_NAME
				+ " (`fName`, `owner`, `ext`, `email`, `details`,`countable`,`status`, `createdById`, `createdByTime`) VALUES (?,?,?,?,?,?,?,?,?);";

		CommonDetails common = new CommonDetails();

		if (obj.getCommon() != null) {
			common = obj.getCommon();
			common.setCreatedByTime(getCurrentDate());
		}

		jdbcTemplate.update(_query, obj.getfName(),obj.getAdmin().getUid(), obj.getExt(), obj.getEmail(), obj.getDetails(),
				obj.getCountable(), common.getStatus(), common.getCreatedById(), common.getCreatedByTime());

	}

	@Override
	public void update(Facilities obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + " `fName` 	= ?," + " `owner` 		= ?,"
				+ " `ext`			= ?," + " `email`		    = ?," + " `details`		= ?,"
				+ " `countable`		= ?," + " `status`		= ?," + " `updatedById`	= ?" + " WHERE `fid` 	= ?";

		CommonDetails common = new CommonDetails();

		if (obj.getCommon() != null)
			common = obj.getCommon();

		jdbcTemplate.update(_query, obj.getfName(),obj.getAdmin().getUid(), obj.getExt(), obj.getEmail(), obj.getDetails(),
				obj.getCountable(), common.getStatus(), common.getUpdatedById(), obj.getFid());

	}

	@Override
	public void activate(Facilities obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `fid` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getFid());

	}

	@Override
	public void inactivate(Facilities obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `fid` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getFid());

	}

}
