package by.jwd.finaltaskweb.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.service.MembershipService;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.StudioServiceImpl;

public class MembershipServiceImpl extends StudioServiceImpl implements MembershipService {

	private static Logger logger = LogManager.getLogger(MembershipServiceImpl.class);

	private DaoFactory factory = DaoFactory.getInstance();

	@Override
	public boolean create(Membership membership) throws ServiceException {
		try {
			factory.getMembershipDao(transaction).create(membership);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean update(Membership membership) throws ServiceException {
		try {
			factory.getMembershipDao(transaction).update(membership);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {
		try {
			factory.getMembershipDao(transaction).delete(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public List<Membership> readAll() throws ServiceException {

		List<Membership> memberships = new ArrayList<>();
		try {
			memberships = factory.getMembershipDao(transaction).readAll();
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return memberships;
	}

	@Override
	public Membership readEntityById(Integer id) throws ServiceException {
		Membership membership = new Membership();
		try {
			membership = factory.getMembershipDao(transaction).readEntityById(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return membership;
	}

	@Override
	public List<Membership> readByClientAndPeriod(Integer clientId, LocalDate startDate, LocalDate endDate)
			throws ServiceException {
		List<Membership> memberships = new ArrayList<>();

		try {
			memberships = factory.getMembershipDao(transaction).readByClientAndPeriod(clientId, startDate, endDate);
			for (Membership membership: memberships) {
				MembershipType type = factory.getMembershipDao(transaction).readTypeById(membership.getType().getId());
				membership.setType(type);
			}
			
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("all memberships of the client for given period {}", memberships);
		return memberships;
	}

	@Override
	public List<Membership> readValidByClient(Integer clientId) throws ServiceException {

		List<Membership> memberships = new ArrayList<>();
		try {
			List<Membership> membershipsTemp  = factory.getMembershipDao(transaction).readValidByClient(clientId);
			for (Membership membership: membershipsTemp) {
				MembershipType type = factory.getMembershipDao(transaction).readTypeById(membership.getType().getId());
				membership.setType(type);
				memberships.add(membership);
			}
			transaction.close();
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return memberships;
	}

	@Override
	public List<Membership> readByPeriod(LocalDate startDate, LocalDate endDate) throws ServiceException {
		List<Membership> memberships = new ArrayList<>();
		try {
			List<Membership> membershipsTemp = factory.getMembershipDao(transaction).readByPeriod(startDate, endDate);
			for (Membership membership: membershipsTemp) {
			MembershipType type = factory.getMembershipDao(transaction).readTypeById(membership.getType().getId());
			membership.setType(type);
			memberships.add(membership);
			transaction.close();
			}
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return memberships;
	}

	@Override
	public double countSumForPeriod(LocalDate startDate, LocalDate endDate) throws ServiceException {
		double sum = 0;
		List<Membership> memberships = readByPeriod(startDate, endDate);
		for (Membership membership : memberships) {
			sum += membership.getType().getPrice();
		}
		logger.debug("sum {}", sum);
		return sum;
	}

	@Override
	public int countQuantityForPeriod(LocalDate startDate, LocalDate endDate) throws ServiceException {
		int quantity = 0;
		List<Membership> memberships = readByPeriod(startDate, endDate);
		quantity = memberships.size();
		
		logger.debug("quantuty {}", quantity);
		return quantity;
	}

	@Override
	public List<MembershipType> readAllTypes() throws ServiceException {
		List<MembershipType> memberships = null;
		try {
			memberships = factory.getMembershipDao(transaction).readAllTypes();
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return memberships;
	}

//	@Override
//	public boolean deleteType(Integer id) throws ServiceException {
//		try {
//			factory.getMembershipDao(transaction).deleteType(id);
//			transaction.close();
//
//		} catch (DaoException e) {
//			throw new ServiceException();
//		}
//		return false;
//	}
//
//	@Override
//	public boolean createType(MembershipType type) throws ServiceException {
//		try {
//			factory.getMembershipDao(transaction).createType(type);
//			transaction.close();
//
//		} catch (DaoException e) {
//			throw new ServiceException();
//		}
//		return false;
//	}
//
//	@Override
//	public boolean updateType(MembershipType type) throws ServiceException {
//		try {
//			factory.getMembershipDao(transaction).updateType(type);
//			transaction.close();
//
//		} catch (DaoException e) {
//			throw new ServiceException();
//		}
//		return false;
//	}

	@Override
	public MembershipType readTypeById(Integer id) throws ServiceException {

		MembershipType type = new MembershipType();
		try {
			type = factory.getMembershipDao(transaction).readTypeById(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return type;
	}
}