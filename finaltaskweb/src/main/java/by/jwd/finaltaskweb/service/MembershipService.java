package by.jwd.finaltaskweb.service;

import java.time.LocalDate;
import java.util.List;

import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;

public interface MembershipService extends StudioService <Integer, Membership>{
	
	public List<Membership> readByClientAndPeriod(Integer clientId, LocalDate startDate, LocalDate endDate) throws ServiceException;
	
	public List<Membership> readByPeriod(LocalDate startDate, LocalDate endDate) throws ServiceException;

	public List<Membership> readValidByClient(Integer clientId) throws ServiceException;
		
	public double countSumForPeriod(LocalDate startDate, LocalDate endDate) throws ServiceException;
	
	public int countQuantityForPeriod (LocalDate startDate, LocalDate endDate) throws ServiceException;
	
	public List<MembershipType> readAllTypes() throws ServiceException;
	
	public MembershipType readTypeById (Integer id) throws ServiceException;

	//public boolean deleteType(Integer id) throws ServiceException;

	//public boolean createType(MembershipType type) throws ServiceException;

	//public boolean updateType(MembershipType type) throws ServiceException;
}
