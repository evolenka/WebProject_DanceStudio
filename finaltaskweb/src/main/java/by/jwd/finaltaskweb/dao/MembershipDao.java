package by.jwd.finaltaskweb.dao;

import java.time.LocalDate;
import java.util.List;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;

public interface MembershipDao extends StudioDao<Integer, Membership> {

	public List<Membership> readByClientAndPeriod(Integer clientId, LocalDate startDate, LocalDate endDate) throws DaoException;//select all memberships of the given client for period
	
	public List<Membership> readByPeriod(LocalDate startDate, LocalDate endDate) throws DaoException;//select all memberships for period

	public List<Membership> readValidByClient(Integer clientId) throws DaoException;//select all valid memberships of the given client
	
	public boolean decreasebalanceClassQuantity (Integer membershipId) throws DaoException;
	
	public boolean increasebalanceClassQuantity (Integer membershipId)throws DaoException;
	
	public List<Membership> readByClient(Integer clientId) throws DaoException;

	public List<MembershipType> readAllTypes() throws DaoException;
	
	public MembershipType readTypeById(Integer id) throws DaoException;
}