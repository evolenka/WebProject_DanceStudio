package by.jwd.finaltaskweb.dao;


import java.util.List;

import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Visit;

public interface VisitDao extends StudioDao<Integer, Visit>{
	
		
	public Visit readByMembershipAndDanceClass (Membership membership, DanceClass danceClass) throws DaoException;//select visits of the client for period (see VisitService)
	
	public List<Visit> readByDanceClass (DanceClass danceClass) throws DaoException;
	
	public List <Visit> readPlannedByMembership(Membership membership) throws DaoException;//select visits planned by client
	
	public boolean updateStatus (Visit visit, Status status) throws DaoException;
	
	public boolean cancelUpdateStatus (Visit visit) throws DaoException;

	public List<Visit> readAllPlanned() throws DaoException;		
}