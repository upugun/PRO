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

import net.jw.mapper.MeetingRoomMapper;
import net.jw.meeting.model.CommonDetails;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.MeetingRoom;
import net.jw.meeting.model.MeetingRoomResources;
import net.jw.system.AppConstant;

public class MeetingRoomDAO extends DAO<MeetingRoom, QueryObject> {

	private static String TABLE_NAME = "meetingroom";
	private static String LOCATION_TABLE = "locations";
	private static String USER_TABLE = "users";

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<MeetingRoom> fetch(QueryObject q) {
		String _query = prepareSqlQuery(q);

		List<MeetingRoom> list = jdbcTemplate.query(_query, new MeetingRoomMapper());

		MeetingRoomResourcesDAO resDAO = new MeetingRoomResourcesDAO();
		resDAO.setDataSource(this.dataSource);

		for (int i = 0; i < list.size(); i++) {
			QueryObject qt = new QueryObject();
			qt.setSearchCode("searchOnlyActive");
			qt.setCondition1(String.valueOf(list.get(i).getmId()));
			List<MeetingRoomResources> resList = resDAO.fetch(qt);

			list.get(i).setResourceList(resList);
		}

		return list;

	}

	private String prepareSqlQuery(QueryObject q) {
		String _query = "SELECT * FROM " + TABLE_NAME + " as m" + " INNER JOIN(SELECT * FROM " + LOCATION_TABLE
				+ ") as l on l.lid = m.mLocationId" + " INNER JOIN(SELECT * FROM " + USER_TABLE
				+ ") as u on u.uid = m.adminId";

		String searchCode = q.getSearchCode();
		String condition1 = q.getCondition1();
		// String condition2 = q.getCondition2();

		if (searchCode.equals("searchOnlyActive"))
			_query = _query + " WHERE m.`status` = '" + AppConstant.ITEM_STATUS_ACTIVE + "'";
		else if (searchCode.equals("searchByStatus"))
			_query = _query + " WHERE m.`status` = '" + condition1 + "'";
		else if (searchCode.equals("searchById"))
			_query = _query + " WHERE m.`mId` = '" + condition1 + "'";

		_query = _query + " ORDER BY m.mId";
		return _query;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insert(final MeetingRoom obj) {

		final String _query = "INSERT INTO " + TABLE_NAME
				+ " (`mRoomName`,`mLocationId`, `seatingCapacity`, `adminId`, `tp`, `notes`,`approval`,`status`, `createdById`, `createdByTime`) VALUES (?,?,?,?,?,?,?,?,?,?);";

		// CommonDetails common = new CommonDetails();
		//
		if (obj.getCommon() != null) {
			// common = obj.getCommon();
			obj.getCommon().setCreatedByTime(getCurrentDate());
		}

		// jdbcTemplate.update(_query,
		// obj.getmRoomName(),obj.getLocation().getLid(),
		// obj.getSeatingCapacity(),
		// obj.getAdmin().getUid(),obj.getTp(),obj.getNotes(),
		// common.getStatus(), common.getCreatedById(),
		// common.getCreatedByTime());

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(_query.toString(), Statement.RETURN_GENERATED_KEYS);
				int x = 0;
				ps.setString(++x, obj.getmRoomName());
				ps.setInt(++x, obj.getLocation().getLid());
				ps.setInt(++x, obj.getSeatingCapacity());
				ps.setInt(++x, obj.getAdmin().getUid());
				ps.setInt(++x, obj.getTp());
				ps.setString(++x, obj.getNotes());
				ps.setBoolean(++x, obj.isApproval());
				ps.setString(++x, obj.getCommon().getStatus());
				ps.setInt(++x, obj.getCommon().getCreatedById());
				ps.setString(++x, obj.getCommon().getCreatedByTime());
				return ps;
			}
		}, holder);

		int key = holder.getKey().intValue();

		MeetingRoomResourcesDAO resDAO = new MeetingRoomResourcesDAO();
		resDAO.setDataSource(this.dataSource);

		for (int i = 0; i < obj.getResourceList().size(); i++) {
			MeetingRoomResources res = obj.getResourceList().get(i);
			res.setMrid(key);

			resDAO.insert(res);
		}

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void update(MeetingRoom obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + " `mRoomName` 	= ?," + " `mLocationId` 	= ?,"
				+ " `seatingCapacity`= ?," + " `adminId`		= ?," + " `tp`		    = ?,"
				+ " `notes`			= ?," + " `approval`			= ?," + " `status`		= ?," + " `updatedById`	= ?"
				+ " WHERE `mId` 	= ?";

		CommonDetails common = new CommonDetails();

		if (obj.getCommon() != null)
			common = obj.getCommon();

		jdbcTemplate.update(_query, obj.getmRoomName(), obj.getLocation().getLid(), obj.getSeatingCapacity(),
				obj.getAdmin().getUid(), obj.getTp(), obj.getNotes(), obj.isApproval(), common.getStatus(),
				common.getUpdatedById(), obj.getmId());

		MeetingRoomResourcesDAO resDAO = new MeetingRoomResourcesDAO();
		resDAO.setDataSource(this.dataSource);
		resDAO.delete(obj.getmId());

		for (int i = 0; i < obj.getResourceList().size(); i++) {
			MeetingRoomResources res = obj.getResourceList().get(i);
			res.setMrid(obj.getmId());

			resDAO.insert(res);
		}

	}

	@Override
	public void activate(MeetingRoom obj) {

		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `mId` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_ACTIVE, obj.getmId());

	}

	@Override
	public void inactivate(MeetingRoom obj) {
		String _query = "UPDATE " + TABLE_NAME + " SET " + "`status`			= ?" + "WHERE `mId` 		= ?";

		jdbcTemplate.update(_query, AppConstant.ITEM_STATUS_INACTIVE, obj.getmId());

	}

}
