package by.jwd.finaltaskweb.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.GroupDao;
import by.jwd.finaltaskweb.dao.StudioDaoImpl;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Teacher;

public class GroupDaoImpl extends StudioDaoImpl implements GroupDao {

	static Logger logger = LogManager.getLogger(GroupDaoImpl.class);

	private static final String SQL_SELECT_ALL_GROUP = "SELECT group.id, group.title, group.teacher_id, group.level FROM `group`";

	private static final String SQL_SELECT_BY_ID = "SELECT group.title, group.teacher_id, group.level FROM `group` WHERE group.id = ?";
	private static final String SQL_SELECT_BY_LEVEL = "SELECT group.id, group.title, group.teacher_id FROM `group` WHERE level = ?";
	private static final String SQL_SELECT_BY_TEACHER_ID = "SELECT group.id, group.title, group.level FROM `group` WHERE teacher_id = ?";
	private static final String SQL_SELECT_BY_TITLE = "SELECT group.id,  group.teacher_id, group.level FROM `group` WHERE title = ?";

	private static final String SQL_INSERT_GROUP = "INSERT INTO `group` (title, teacher_id, level) VALUES (?, ?, ?)";

	private static final String SQL_DELETE_BY_ID = "DELETE FROM `group` WHERE id = ?";

	private static final String SQL_UPDATE_GROUP = "UPDATE `group` SET title = ?, teacher_id = ?, level = ? WHERE id = ?";

	@Override
	public List<Group> readAll() throws DaoException {

		List<Group> groups = new ArrayList<>();

		Statement statement = null;

		try {

			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GROUP);

			while (resultSet.next()) {

				Group group = new Group(resultSet.getInt(1));

				group.setTitle(resultSet.getString(2));
				group.setTeacher(new Teacher(resultSet.getInt(3)));
				group.setLevel(Level.valueOf(resultSet.getString(4)));
				groups.add(group);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		logger.debug("groups have been read from db");
		return groups;
	}

	@Override
	public Group readEntityById(Integer id) throws DaoException {

		Group group = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_ID);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				group = new Group(id);

				group.setTitle(resultSet.getString(1));
				group.setTeacher(new Teacher(resultSet.getInt(2)));
				group.setLevel(Level.valueOf(resultSet.getString(3)));

				logger.debug("group has been read by id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return group;
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
		logger.debug("group has been deleted");
		return true;
	}

	@Override
	public boolean create(Group group) throws DaoException {

		PreparedStatement statement = null;

		try {
			// if (group.getId()!=null) {
			statement = connection.prepareStatement(SQL_INSERT_GROUP);
			statement.setString(1, group.getTitle());
			statement.setInt(2, group.getTeacher().getId());
			statement.setString(3, group.getLevel().toString());
			statement.executeUpdate();
			logger.debug("group has been created");
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public boolean update(Group group) throws DaoException {

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_UPDATE_GROUP);
			statement.setString(1, group.getTitle());
			statement.setInt(2, group.getTeacher().getId());
			statement.setString(3, group.getLevel().toString());
			statement.setInt(4, group.getId());
			logger.debug("statement {}", statement.toString());
			statement.executeUpdate();
			close(statement);
			logger.debug("group has been updated");

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public List<Group> readByLevel(Level level) throws DaoException {

		List<Group> groups = new ArrayList<>();

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_SELECT_BY_LEVEL);
			statement.setString(1, level.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Group group = new Group(resultSet.getInt(1));
				group.setTitle(resultSet.getString(2));
				group.setTeacher(new Teacher(resultSet.getInt(3)));
				group.setLevel(level);
				groups.add(group);
				logger.debug("group has been read by level");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return groups;
	}

	@Override
	public List<Group> readByTeacherId(Integer id) throws DaoException {

		List<Group> groups = new ArrayList<>();

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_SELECT_BY_TEACHER_ID);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Group group = new Group(resultSet.getInt(1));
				group.setTitle(resultSet.getString(2));
				group.setTeacher(new Teacher(id));
				group.setLevel(Level.valueOf(resultSet.getString(3)));
				groups.add(group);
				logger.debug("group has been read by teacher_id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return groups;
	}

	@Override
	public Group readByTitle(String title) throws DaoException {

		Group group = null;

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_SELECT_BY_TITLE);
			statement.setString(1, title);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				group = new Group(resultSet.getInt(1));
				group.setTitle(title);
				group.setTeacher(new Teacher(resultSet.getInt(2)));
				group.setLevel(Level.valueOf(resultSet.getString(3)));

				logger.debug("group has been read by title {}", group);
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return group;
	}

	@Override
	public Integer createGroup(Group group) throws DaoException {

		PreparedStatement statement = null;
		Integer groupId = null;

		try {
			statement = connection.prepareStatement(SQL_INSERT_GROUP, Statement.RETURN_GENERATED_KEYS);
		
			statement.setString(1, group.getTitle());
			statement.setInt(2, group.getTeacher().getId());
			statement.setString(3, group.getLevel().toString());
			logger.debug(statement.toString());
		
			statement.executeUpdate();
			logger.debug("group has been created");

			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				groupId = resultSet.getInt(1);
				logger.debug(groupId);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return groupId;
	}
}