package by.jwd.finaltaskweb.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DanceClassDao;
import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.StudioDaoImpl;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Schedule;

public class DanceClassDaoImpl extends StudioDaoImpl implements DanceClassDao {

	static Logger logger = LogManager.getLogger(DanceClassDaoImpl.class);

	private static final String SQL_SELECT_ALL_DANCECLASSES = "SELECT danceclass.id, danceclass.schedule_id, danceclass.date, danceclass.is_active FROM `danceclass`";

	private static final String SQL_SELECT_BY_ID = "SELECT danceclass.schedule_id, danceclass.date, danceclass.is_active FROM `danceclass` WHERE danceclass.id = ?";

	private static final String SQL_SELECT_ACTIVE_BY_SCHEDULE = "SELECT danceclass.id, danceclass.date, danceclass.is_active FROM `danceclass` WHERE danceclass.schedule_id= ? AND danceclass.is_active = TRUE AND danceclass.date >= CURRENT_DATE()";
 
	private static final String SQL_SELECT_BY_SCHEDULE = "SELECT danceclass.id, danceclass.date, danceclass.is_active FROM `danceclass` WHERE danceclass.schedule_id= ?";

	private static final String SQL_SELECT_ACTIVE_BY_DATE = "SELECT danceclass.id, danceclass.schedule_id, danceclass.is_active FROM `danceclass` WHERE danceclass.date = ? AND danceclass.is_active = TRUE";

	private static final String SQL_SELECT_BY_DATE_AND_SCHEDULE = "SELECT danceclass.id, danceclass.is_active FROM `danceclass` WHERE danceclass.date = ? AND danceclass.schedule_id = ?";

	private static final String SQL_SELECT_BY_PERIOD = "SELECT danceclass.id, danceclass.schedule_id, danceclass.date, danceclass.is_active FROM `danceclass` WHERE danceclass.date >= ? AND danceclass.date <= ?";

	private static final String SQL_INSERT_DANCECLASS = "INSERT INTO danceclass (schedule_id, date, is_active) VALUES (?, ?, TRUE)";

	private static final String SQL_DELETE_BY_ID = "DELETE FROM danceclass WHERE id = ?";

	private static final String SQL_UPDATE_DANCECLASS = "UPDATE danceclass SET schedule_id = ?, date = ? is_active = ? WHERE id = ?";

	private static final String SQL_UPDATE_IS_ACTIVE = "UPDATE danceclass SET  is_active = FALSE WHERE id = ?";

	@Override
	public List<DanceClass> readAll() throws DaoException {

		List<DanceClass> danceClasses = new ArrayList<>();

		Statement statement = null;

		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_DANCECLASSES);

			while (resultSet.next()) {

				DanceClass danceClass = new DanceClass(resultSet.getInt(1));
				danceClass.setSchedule(new Schedule(resultSet.getInt(2)));
				danceClass.setDate(resultSet.getDate(3).toLocalDate());
				danceClass.setActive(resultSet.getBoolean(4));

				danceClasses.add(danceClass);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		logger.debug("dance classes  have been read from db");
		return danceClasses;
	}

	@Override
	public DanceClass readEntityById(Integer id) throws DaoException {
		DanceClass danceClass = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_ID);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				danceClass = new DanceClass(id);
				danceClass.setSchedule(new Schedule(resultSet.getInt(1)));
				danceClass.setDate(resultSet.getDate(2).toLocalDate());
				danceClass.setActive(resultSet.getBoolean(3));

				logger.debug("dance class has been read by id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return danceClass;
	}

	@Override
	public List<DanceClass> readActiveBySchedule(Schedule schedule) throws DaoException {
		List<DanceClass> danceClasses = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_ACTIVE_BY_SCHEDULE);
			statement.setInt(1, schedule.getId());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				DanceClass danceClass = new DanceClass();
				danceClass.setId(resultSet.getInt(1));
				danceClass.setSchedule(new Schedule(schedule.getId()));
				danceClass.setDate(resultSet.getDate(2).toLocalDate());
				danceClass.setActive(resultSet.getBoolean(3));
				danceClasses.add(danceClass);
				logger.debug("active dance classes have been read by schedule_id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return danceClasses;
	}

	@Override
	public List<DanceClass> readBySchedule(Schedule schedule) throws DaoException {

		List<DanceClass> danceClasses = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_SCHEDULE);
			statement.setInt(1, schedule.getId());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				DanceClass danceClass = new DanceClass();
				danceClass.setId(resultSet.getInt(1));
				danceClass.setSchedule(new Schedule(schedule.getId()));
				danceClass.setDate(resultSet.getDate(2).toLocalDate());
				danceClass.setActive(resultSet.getBoolean(3));
				danceClasses.add(danceClass);
				logger.debug("dance classes have been read by schedule_id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return danceClasses;
	}

	@Override
	public List<DanceClass> readActiveByDate(LocalDate date) throws DaoException {

		List<DanceClass> danceClasses = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_ACTIVE_BY_DATE);
			statement.setDate(1, Date.valueOf(date));

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				DanceClass danceClass = new DanceClass();
				danceClass.setId(resultSet.getInt(1));
				danceClass.setSchedule(new Schedule(resultSet.getInt(2)));
				danceClass.setDate(date);
				danceClass.setActive(resultSet.getBoolean(3));
				danceClasses.add(danceClass);
				logger.debug("active dance classes have been read by date");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return danceClasses;
	}

	@Override
	public DanceClass readByDateAndSchedule(LocalDate date, Schedule schedule) throws DaoException {

		DanceClass danceClass = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_DATE_AND_SCHEDULE);
			statement.setDate(1, Date.valueOf(date));
			statement.setInt(2, schedule.getId());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				danceClass = new DanceClass();
				danceClass.setId(resultSet.getInt(1));
				danceClass.setSchedule(new Schedule(schedule.getId()));
				danceClass.setDate(date);
				danceClass.setActive(resultSet.getBoolean(2));

				logger.debug("dance class have been read by date and schedule");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return danceClass;
	}

	@Override
	public List<DanceClass> readByPeriod(LocalDate startDate, LocalDate endDate) throws DaoException {

		List<DanceClass> danceClasses = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_PERIOD);
			statement.setDate(1, Date.valueOf(startDate));
			statement.setDate(2, Date.valueOf(endDate));

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				DanceClass danceClass = new DanceClass();
				danceClass.setId(resultSet.getInt(1));
				danceClass.setSchedule(new Schedule(resultSet.getInt(2)));
				danceClass.setDate(resultSet.getDate(3).toLocalDate());
				danceClass.setActive(resultSet.getBoolean(4));
				danceClasses.add(danceClass);
				logger.debug("dance classes for period have been read");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return danceClasses;
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
		}

		logger.debug("dance class has been deleted");
		return true;
	}

	@Override
	public boolean create(DanceClass danceClass) throws DaoException {

		PreparedStatement statement = null;

		try {
			// if(!danceClass.getId()==null) {
			statement = connection.prepareStatement(SQL_INSERT_DANCECLASS);
			statement.setInt(1, danceClass.getSchedule().getId());
			statement.setDate(2, Date.valueOf(danceClass.getDate()));

			statement.executeUpdate();
			logger.debug("dance class has been created");
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public boolean update(DanceClass danceClass) throws DaoException {

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_UPDATE_DANCECLASS);
			statement.setInt(1, danceClass.getSchedule().getId());
			statement.setDate(2, Date.valueOf(danceClass.getDate()));
			statement.setBoolean(3, danceClass.isActive());
			statement.executeUpdate();
			close(statement);
			logger.debug("dance class has been updated");

		} catch (SQLException e) {
			throw new DaoException();

		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public boolean changeForNoActive(Integer danceClassId) throws DaoException {
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_UPDATE_IS_ACTIVE);
			statement.setInt(1, danceClassId);

			statement.executeUpdate();
			close(statement);
			logger.debug("dance class has been updated for no active");

		} catch (SQLException e) {
			throw new DaoException();

		} finally {
			close(statement);
		}
		return true;
	}

}