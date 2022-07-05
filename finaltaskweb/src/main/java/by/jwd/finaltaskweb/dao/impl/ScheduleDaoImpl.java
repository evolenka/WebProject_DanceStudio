package by.jwd.finaltaskweb.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.ScheduleDao;
import by.jwd.finaltaskweb.dao.StudioDaoImpl;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public class ScheduleDaoImpl extends StudioDaoImpl implements ScheduleDao {

	static Logger logger = LogManager.getLogger(ScheduleDaoImpl.class);

	private static final String SQL_SELECT_ALL_SCHEDULE = "SELECT schedule.id, schedule.weekday, schedule.time, schedule.duration, schedule.group_id FROM `schedule`";

	private static final String SQL_SELECT_BY_ID = "SELECT schedule.weekday, schedule.time, schedule.duration, schedule.group_id FROM `schedule` WHERE schedule.id = ?";

	private static final String SQL_SELECT_BY_GROUP = "SELECT schedule.id, schedule.weekday, schedule.time, schedule.duration FROM `schedule` WHERE schedule.group_id = ?";

	private static final String SQL_SELECT_BY_WEEKDAY = "SELECT schedule.id, schedule.time, schedule.duration, schedule.group_id FROM `schedule` WHERE schedule.group_id IS NOT NULL AND schedule.weekday = ?";

	private static final String SQL_INSERT_SCHEDULE = "INSERT INTO schedule (weekday, time, duration, group_id) VALUES (?, ?, ?, ?)";

	private static final String SQL_DELETE_BY_ID = "DELETE FROM schedule WHERE id = ?";

	private static final String SQL_UPDATE_SCHEDULE = "UPDATE schedule SET schedule.weekday = ?, schedule.time = ?, schedule.duration =?, schedule.group_id = ? WHERE id = ?";

	@Override
	public List<Schedule> readAll() throws DaoException {

		List<Schedule> schedules = new ArrayList<>();

		Statement statement = null;

		try {

			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SCHEDULE);

			while (resultSet.next()) {

				Schedule schedule = new Schedule(resultSet.getInt(1));
				schedule.setWeekDay(WeekDay.valueOf(resultSet.getString(2)));
				schedule.setTime(resultSet.getTime(3).toLocalTime());
				schedule.setDuration(resultSet.getInt(4));
				schedule.setGroup(new Group(resultSet.getInt(5)));

				schedules.add(schedule);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		logger.debug("schedules have been read from db");
		return schedules;
	}

	@Override
	public Schedule readEntityById(Integer id) throws DaoException {

		Schedule schedule = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_ID);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				schedule = new Schedule(id);

				schedule.setWeekDay(WeekDay.valueOf(resultSet.getString(1)));
				schedule.setTime(resultSet.getTime(2).toLocalTime());
				schedule.setDuration(resultSet.getInt(3));
				schedule.setGroup(new Group(resultSet.getInt(4)));

				logger.debug("schedule has been read by id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return schedule;
	}

	@Override
	public List<Schedule> readByGroup(Integer groupId) throws DaoException {

		List<Schedule> schedules = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_GROUP);

			statement.setInt(1, groupId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Schedule schedule = new Schedule();

				schedule.setId(resultSet.getInt(1));
				schedule.setWeekDay(WeekDay.valueOf(resultSet.getString(2)));
				schedule.setTime(resultSet.getTime(3).toLocalTime());
				schedule.setDuration(resultSet.getInt(4));
				schedule.setGroup(new Group(groupId));

				schedules.add(schedule);

				logger.debug("schedule has been read by group");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return schedules;
	}

	@Override
	public List<Schedule> readByWeekDay(WeekDay weekday) throws DaoException {

		List<Schedule> schedules = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_WEEKDAY);

			statement.setString(1, weekday.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Schedule schedule = new Schedule();

				schedule.setId(resultSet.getInt(1));
				schedule.setWeekDay(weekday);
				schedule.setTime(resultSet.getTime(2).toLocalTime());
				schedule.setDuration(resultSet.getInt(3));
				schedule.setGroup(new Group(resultSet.getInt(4)));

				schedules.add(schedule);

				logger.debug("schedule has been read by weekday");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return schedules;
	}

	@Override
	public boolean delete(Integer id) throws DaoException {

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_DELETE_BY_ID);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DaoException();
			}
		}

		logger.debug("schedule has been deleted");
		return true;
	}

	@Override
	public boolean create(Schedule schedule) throws DaoException {

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_INSERT_SCHEDULE);

			statement.setString(1, schedule.getWeekDay().toString());
			statement.setTime(2, Time.valueOf(schedule.getTime()));
			statement.setInt(3, schedule.getDuration());
			statement.setInt(4, schedule.getGroup().getId());

			logger.debug("statement {}", statement.toString());
			statement.executeUpdate();
			logger.debug("schedule has been created");

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public boolean update(Schedule schedule) throws DaoException {

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_UPDATE_SCHEDULE);
			statement.setString(1, schedule.getWeekDay().toString());
			statement.setTime(2, Time.valueOf(schedule.getTime()));
			statement.setInt(3, schedule.getDuration());
			if (schedule.getGroup().getId() == 0) {
				statement.setNull(4, Types.TINYINT);
			 } else {
				statement.setInt(4, schedule.getGroup().getId());
			}
			statement.setInt(5, schedule.getId());
			logger.debug("statement {}", statement.toString());
			statement.executeUpdate();

			logger.debug("schedule has been updated");

		} catch (SQLException e) {

			throw new DaoException();

		} finally {
			close(statement);
		}
		return true;
	}
}