package by.jwd.finaltaskweb.dao;

import java.util.List;

import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;


public interface GroupDao extends StudioDao<Integer, Group> {

	public List<Group> readByLevel(Level level) throws DaoException;//used to search group by level (see GroupService)

	public List<Group> readByTeacherId(Integer id) throws DaoException;//used to read all groups by teacher id (see GroupService)

	public Group readByTitle(String title) throws DaoException;
	
	public Integer createGroup (Group group) throws DaoException;
	
}