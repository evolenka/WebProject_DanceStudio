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

import com.mysql.cj.jdbc.CallableStatement;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.MembershipDao;
import by.jwd.finaltaskweb.dao.StudioDaoImpl;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.entity.Type;

public class MembershipDaoImpl extends StudioDaoImpl implements MembershipDao {

	static Logger logger = LogManager.getLogger(MembershipDaoImpl.class);

	private static final String SQL_SELECT_ALL_MEMBERSHIP = "SELECT membership.id, membership.client_id, membership.start_date, membership.end_date, membership.balance_quantity, membership.type_of_membership_id FROM `membership`";
	private static final String SQL_SELECT_ALL_TYPES = "SELECT type_of_membership.id,  type_of_membership.title, type_of_membership.max_class_quantity, type_of_membership.price FROM `type_of_membership`";
	private static final String SQL_SELECT_BY_ID = "SELECT membership.client_id, membership.start_date, membership.end_date, membership.balance_quantity, membership.type_of_membership_id FROM `membership` WHERE membership.id = ?";
	private static final String SQL_SELECT_TYPE_BY_ID = "SELECT title, max_class_quantity, price FROM `type_of_membership` WHERE id = ?";
	private static final String SQL_SELECT_BY_CLIENT = "SELECT membership.id, membership.start_date, membership.end_date, membership.balance_quantity, membership.type_of_membership_id FROM `membership` WHERE membership.client_id = ?";
	private static final String SQL_SELECT_BY_CLIENT_AND_PERIOD = "SELECT membership.id, membership.start_date, membership.end_date, membership.balance_quantity, membership.type_of_membership_id FROM `membership` WHERE membership.client_id = ? AND membership.start_date >= ? AND membership.start_date <= ?";
	private static final String SQL_SELECT_BY_PERIOD = "SELECT membership.id, membership.client_id, membership.start_date, membership.end_date, membership.balance_quantity, membership.type_of_membership_id FROM `membership` WHERE membership.start_date >= ? AND membership.start_date <= ?";
	private static final String SQL_INSERT_MEMBERSHIP = "INSERT INTO `membership` (client_id, start_date, end_date, membership.balance_quantity, type_of_membership_id) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM `membership` WHERE id = ?";
	private static final String SQL_UPDATE_MEMBERSHIP = "UPDATE `membership` SET client_id = ?, start_date = ?, end_date = ?, balance_quantity = ?, type_of_membership_id = ? WHERE id = ?";
	private static final String SQL_DECREASE_BALANCE_QUANTITY = "UPDATE membership SET membership.balance_quantity = membership.balance_quantity-1 WHERE membership.id = ?";
	private static final String SQL_INCREASE_BALANCE_QUANTITY = "UPDATE membership SET membership.balance_quantity = membership.balance_quantity+1 WHERE membership.id = ?";

	@Override
	public List<Membership> readAll() throws DaoException {

		List<Membership> memberships = new ArrayList<>();

		Statement statement = null;

		try {

			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_MEMBERSHIP);

			while (resultSet.next()) {

				Membership membership = new Membership(resultSet.getInt(1));
				membership.setClient(new Client(resultSet.getInt(2)));
				membership.setStartDate(resultSet.getDate(3).toLocalDate());
				membership.setEndDate(resultSet.getDate(4).toLocalDate());
				membership.setBalanceClassQuantity(resultSet.getInt(5));
				membership.setType(new MembershipType(resultSet.getInt(6)));

				memberships.add(membership);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		logger.debug("memberships have been read from db");
		return memberships;
	}

	@Override
	public List<MembershipType> readAllTypes() throws DaoException {

		List<MembershipType> types = new ArrayList<>();
		Statement statement = null;

		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TYPES);
			while (resultSet.next()) {
				MembershipType type = new MembershipType(resultSet.getInt(1));
				type.setType(Type.valueOf(resultSet.getString(2)));
				type.setMaxClassQuantity(resultSet.getInt(3));
				type.setPrice(resultSet.getInt(4));

				types.add(type);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		logger.debug("types of memberships have been read from db");
		return types;
	}

	@Override
	public List<Membership> readValidByClient(Integer clientId) throws DaoException {

		List<Membership> memberships = new ArrayList<>();
		CallableStatement statement = null;

		try {

			final String SQL = "{CALL validMembershipByClient (?)}";
			statement = (CallableStatement) connection.prepareCall(SQL);
			statement.setInt(1, clientId);
			logger.debug("statement {}", statement);
			statement.execute();

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Membership membership = new Membership(resultSet.getInt(1));
				membership.setClient(new Client(clientId));
				membership.setStartDate(resultSet.getDate(2).toLocalDate());
				membership.setEndDate(resultSet.getDate(3).toLocalDate());
				membership.setBalanceClassQuantity(resultSet.getInt(4));
				membership.setType(new MembershipType(resultSet.getInt(5)));

				memberships.add(membership);
			}
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		logger.debug("all valid memberships have been read from db {}", memberships);
		return memberships;
	}

	@Override
	public Membership readEntityById(Integer id) throws DaoException {

		Membership membership = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_ID);
			statement.setInt(1, id);
			logger.debug("statement {}", statement.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				membership = new Membership(id);

				membership.setClient(new Client(resultSet.getInt(1)));
				membership.setStartDate(resultSet.getDate(2).toLocalDate());
				membership.setEndDate(resultSet.getDate(3).toLocalDate());
				membership.setBalanceClassQuantity(resultSet.getInt(4));
				membership.setType(new MembershipType(resultSet.getInt(5)));

				logger.debug("membership has been read by id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return membership;
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

		logger.debug("membership been deleted");
		return true;
	}

	@Override
	public boolean create(Membership membership) throws DaoException {

		PreparedStatement statement = null;

		try {
			// if(!danceClass.getId()==null) {
			statement = connection.prepareStatement(SQL_INSERT_MEMBERSHIP);

			statement.setInt(1, membership.getClient().getId());
			statement.setDate(2, Date.valueOf(membership.getStartDate()));
			statement.setDate(3, Date.valueOf(membership.getEndDate()));
			statement.setInt(4, membership.getBalanceClassQuantity());
			statement.setInt(5, membership.getType().getId());

			statement.executeUpdate();
			logger.debug("membership has been created");
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public boolean update(Membership membership) throws DaoException {

		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(SQL_UPDATE_MEMBERSHIP);

			statement.setInt(1, membership.getClient().getId());
			statement.setDate(2, Date.valueOf(membership.getStartDate()));
			statement.setDate(3, Date.valueOf(membership.getEndDate()));
			statement.setInt(4, membership.getBalanceClassQuantity());
			statement.setInt(5, membership.getType().getId());
			statement.setInt(6, membership.getId());

			logger.debug("statement {}", statement.toString());
			statement.executeUpdate();

			close(statement);
			logger.debug("membership has been updated");

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public List<Membership> readByClientAndPeriod(Integer clientId, LocalDate startDate, LocalDate endDate)
			throws DaoException {

		List<Membership> memberships = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_CLIENT_AND_PERIOD);
			statement.setInt(1, clientId);
			statement.setDate(2, Date.valueOf(startDate));
			statement.setDate(3, Date.valueOf(endDate));

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Membership membership = new Membership();
				membership.setId(resultSet.getInt(1));
				membership.setClient(new Client(clientId));
				membership.setStartDate(resultSet.getDate(2).toLocalDate());
				membership.setEndDate(resultSet.getDate(3).toLocalDate());
				membership.setBalanceClassQuantity(resultSet.getInt(4));
				membership.setType(new MembershipType(resultSet.getInt(5)));

				memberships.add(membership);

				logger.debug("memberships have been read by client and period {}", membership);
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return memberships;
	}

	@Override
	public List<Membership> readByClient(Integer clientId) throws DaoException {

		List<Membership> memberships = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_CLIENT);
			statement.setInt(1, clientId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Membership membership = new Membership();
				membership.setId(resultSet.getInt(1));
				membership.setClient(new Client(clientId));
				membership.setStartDate(resultSet.getDate(2).toLocalDate());
				membership.setEndDate(resultSet.getDate(3).toLocalDate());
				membership.setBalanceClassQuantity(resultSet.getInt(4));
				membership.setType(new MembershipType(resultSet.getInt(5)));

				memberships.add(membership);

				logger.debug("memberships have been read by client {}", membership);
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return memberships;
	}

	@Override
	public List<Membership> readByPeriod(LocalDate startDate, LocalDate endDate) throws DaoException {

		List<Membership> memberships = new ArrayList<>();

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_BY_PERIOD);
			statement.setDate(1, Date.valueOf(startDate));
			statement.setDate(2, Date.valueOf(endDate));

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Membership membership = new Membership();

				membership.setId(resultSet.getInt(1));
				membership.setClient(new Client(resultSet.getInt(2)));
				membership.setStartDate(resultSet.getDate(3).toLocalDate());
				membership.setEndDate(resultSet.getDate(4).toLocalDate());
				membership.setBalanceClassQuantity(resultSet.getInt(5));
				membership.setType(new MembershipType(resultSet.getInt(6)));

				memberships.add(membership);

				logger.debug("memberships have been read by period");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return memberships;
	}

//	@Override
//	public boolean deleteType(Integer id) throws DaoException {
//		PreparedStatement statement = null;
//
//		try {
//			statement = connection.prepareStatement(SQL_DELETE_TYPE_BY_ID);
//			statement.setInt(1, id);
//			statement.executeUpdate();
//		} catch (SQLException e) {
//			throw new DaoException();
//		} finally {
//			close(statement);
//		}
//
//		logger.debug("type of membership been deleted");
//		return true;
//	}
//
//	@Override
//	public boolean createType(MembershipType type) throws DaoException {
//
//		PreparedStatement statement = null;
//
//		try {
//			// if(!danceClass.getId()==null) {
//			statement = connection.prepareStatement(SQL_INSERT_TYPE);
//
//			statement.setString(1, type.getTitle());
//			statement.setInt(2, type.getMaxClassQuantity());
//			statement.setDouble(3, type.getPrice());
//
//			statement.executeUpdate();
//			logger.debug("type of membership has been created");
//		} catch (SQLException e) {
//			throw new DaoException();
//		} finally {
//			close(statement);
//		}
//		return true;
//	}
//
//	@Override
//	public boolean updateType(MembershipType type) throws DaoException {
//
//		PreparedStatement statement = null;
//
//		try {
//
//			statement = connection.prepareStatement(SQL_UPDATE_TYPE);
//
//			statement.setString(1, type.getTitle());
//			statement.setInt(2, type.getMaxClassQuantity());
//			statement.setDouble(3, type.getPrice());
//			statement.setInt(4, type.getId());
//
//			statement.executeUpdate();
//			logger.debug("type of membership has been updated");
//
//		} catch (SQLException e) {
//			throw new DaoException();
//
//		} finally {
//			close(statement);
//		}
//		return true;
//	}

	@Override
	public MembershipType readTypeById(Integer id) throws DaoException {
		MembershipType type = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_SELECT_TYPE_BY_ID);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				type = new MembershipType(id);

				type.setType(Type.valueOf(resultSet.getString(1)));
				type.setMaxClassQuantity(resultSet.getInt(2));
				type.setPrice(resultSet.getDouble(3));

				logger.debug("membership type has been read by id");
			}

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return type;
	}

	@Override
	public boolean decreasebalanceClassQuantity(Integer membershipId) throws DaoException {
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_DECREASE_BALANCE_QUANTITY);
			statement.setInt(1, membershipId);

			statement.executeUpdate();
			close(statement);
			connection.commit();

			logger.debug("balance quantity in membership has been decreased");

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}

	@Override
	public boolean increasebalanceClassQuantity(Integer membershipId) throws DaoException {

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SQL_INCREASE_BALANCE_QUANTITY);
			statement.setInt(1, membershipId);

			statement.executeUpdate();
			close(statement);
			connection.commit();

			logger.debug("balance quantity in membership has been increased");

		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			close(statement);
		}
		return true;
	}
}