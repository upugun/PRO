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

import net.jw.mapper.UsersMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.Login;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Users;
import net.jw.system.AppConstant;

public class UsersDAO extends DAO<Users, QueryObject> {

	private static String TABLE_NAME = "users";
	private static String LOGIN_TABLE_NAME = "login";
	private static String ROLE_TABLE = "role";

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<Users> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);

		List<Users> list = jdbcTemplate.query(_query, new UsersMapper());

		return list;

	}
	
	
	public List<Users> reset(QueryObject q) {
		String _query = prepareSqlQuery(q);

		List<Users> list = jdbcTemplate.query(_query, new UsersMapper());

		return list;

	}

	private String prepareSqlQuery(QueryObject q) {
		String _query = "SELECT * FROM " + TABLE_NAME + " as u inner join(SELECT * FROM " + LOGIN_TABLE_NAME
				+ ") as l on l.userId = u.uid" + " INNER JOIN(SELECT * FROM " + ROLE_TABLE
				+ ") as r on r.roleId = u.uRoll";

		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();

		if (searchCode.equals("searchOnlyActive"))
			_query = _query + " WHERE u.`status` = '" + AppConstant.ITEM_STATUS_ACTIVE + "'";
		else if (searchCode.equals("searchbyUid"))
			_query = _query + " WHERE u.`uid` = '" + condition1 + "'";
		else if (searchCode.equals("searchByStatus"))
			_query = _query + " WHERE u.`status` = '" + condition1 + "'";
		else if (searchCode.equals("searchByEmail"))
			_query = _query + " WHERE u.`email` = '" + condition1 + "'";
		else if (searchCode.equals("uid"))
			_query = _query + " WHERE u.`uid` = '" + condition1 + "'";

		_query = _query + " ORDER BY u.uid";
		return _query;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insert(final Users obj) {

		final String _query = "INSERT INTO " + TABLE_NAME
				+ " (`uName`, `uDep`, `uBuild`, `uFloor`,`uRoll`, `mobile`, `email`, `details`, `status`, `createdById`, `createdByTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		// CommonDetails common = new CommonDetails();

		if (obj.getCommon() != null) {
			obj.getCommon().setCreatedByTime(getCurrentDate());
		}

		// jdbcTemplate.update(_query, obj.getuName(), obj.getuDep(),
		// obj.getuBuild(), obj.getuFloor(),
		// obj.getuRoll(),obj.getMobile(),obj.getEmail(),
		// obj.getDetails(), common.getStatus(), common.getCreatedById(),
		// common.getCreatedByTime());

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(_query.toString(), Statement.RETURN_GENERATED_KEYS);
				int x = 0;
				ps.setString(++x, obj.getuName());
				ps.setString(++x, obj.getuDep());
				ps.setString(++x, obj.getuBuild());
				ps.setString(++x, obj.getuFloor());
				ps.setInt(++x, obj.getRole().getRoleId());
				ps.setString(++x, obj.getMobile());
				ps.setString(++x, obj.getEmail());
				ps.setString(++x, obj.getDetails());
				ps.setString(++x, obj.getCommon().getStatus());
				ps.setInt(++x, obj.getCommon().getCreatedById());
				ps.setString(++x, obj.getCommon().getCreatedByTime());
				return ps;
			}
		}, holder);

		int key = holder.getKey().intValue();

		Login login = new Login();

		if (obj.getLogin() != null) {
			login = obj.getLogin();
			login.setUid(key);
			login.setCommon(obj.getCommon());
		}

		LoginDAO loginDAO = new LoginDAO();
		loginDAO.setDataSource(this.dataSource);

		loginDAO.insert(login);

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void update(Users obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + " `uName` 		= ?," + " `uDep` 			= ?,"
				+ " `uBuild`		= ?," + " `uFloor` 		= ?," + " `uRoll` 		= ?," + " `mobile`		= ?,"
				+ " `email`			= ?," + " `details`		= ?," + " `status`		= ?," + " `updatedById`	= ?"
				+ " WHERE `uid` 	= ?";

		CommonDetails common = new CommonDetails();

		if (obj.getCommon() != null)
			common = obj.getCommon();

		jdbcTemplate.update(_query, obj.getuName(), obj.getuDep(), obj.getuBuild(), obj.getuFloor(),
				obj.getRole().getRoleId(), obj.getMobile(), obj.getEmail(), obj.getDetails(), common.getStatus(),
				common.getUpdatedById(), obj.getUid());

		LoginDAO loginDAO = new LoginDAO();
		loginDAO.setDataSource(this.dataSource);

		Login login = new Login();

		if (obj.getLogin() != null) {
			login = obj.getLogin();
			login.setUid(obj.getUid());
			login.setCommon(common);
		}

		loginDAO.update(login);

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void activate(Users obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `uid` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getUid());
		
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.setDataSource(this.dataSource);
		loginDAO.activate(obj.getLogin());
	}
	
	public void resetPassword(int uid, String pass) {

		String _query = "UPDATE " + LOGIN_TABLE_NAME + " SET " + "`password`			= ?" + "WHERE `userId` 		= ?";

		jdbcTemplate.update(_query, pass, uid);

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void inactivate(Users obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `uid` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getUid());
		
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.setDataSource(this.dataSource);
		loginDAO.inactivate(obj.getLogin());

	}

}
