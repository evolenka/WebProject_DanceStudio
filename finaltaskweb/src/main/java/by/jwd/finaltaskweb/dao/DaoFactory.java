package by.jwd.finaltaskweb.dao;



import by.jwd.finaltaskweb.dao.impl.DanceClassDaoImpl;
import by.jwd.finaltaskweb.dao.impl.GroupDaoImpl;
import by.jwd.finaltaskweb.dao.impl.MembershipDaoImpl;
import by.jwd.finaltaskweb.dao.impl.ScheduleDaoImpl;
import by.jwd.finaltaskweb.dao.impl.UserDaoImpl;
import by.jwd.finaltaskweb.dao.impl.VisitDaoImpl;


public class DaoFactory {

	private static final DaoFactory instance = new DaoFactory();

	private StudioDaoImpl userDao = new UserDaoImpl();
	private StudioDaoImpl groupDao = new GroupDaoImpl();
	private StudioDaoImpl membershipDao = new MembershipDaoImpl();
	private StudioDaoImpl scheduleDao = new ScheduleDaoImpl();
	private StudioDaoImpl danceClassDao = new DanceClassDaoImpl();
	private StudioDaoImpl visitDao = new VisitDaoImpl();
	
	
	private DaoFactory() {
	}

	public static DaoFactory getInstance() {
		return instance;
	}

	public UserDao getUserDao(Transaction transaction) throws DaoException {
		userDao.setConnection(transaction.getConnection());
		return (UserDao) userDao;
	}

	public GroupDao getGroupDao(Transaction transaction) throws DaoException {
		groupDao.setConnection(transaction.getConnection());
		return (GroupDao) groupDao;
	}

	public MembershipDao getMembershipDao(Transaction transaction) throws DaoException {
		membershipDao.setConnection(transaction.getConnection());
		return (MembershipDao) membershipDao;
	}

	public ScheduleDao getScheduleDao(Transaction transaction) throws DaoException {
		scheduleDao.setConnection(transaction.getConnection());
		return (ScheduleDao) scheduleDao;
	}

	public DanceClassDao getDanceClassDao(Transaction transaction) throws DaoException {
		danceClassDao.setConnection(transaction.getConnection());
		return (DanceClassDao) danceClassDao;
	}

	public VisitDao getVisitDao(Transaction transaction) throws DaoException {
		visitDao.setConnection(transaction.getConnection());
		return (VisitDao) visitDao;
	}
}