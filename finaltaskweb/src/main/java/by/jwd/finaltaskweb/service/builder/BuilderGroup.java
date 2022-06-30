package by.jwd.finaltaskweb.service.builder;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Teacher;

public class BuilderGroup {
	private static Logger logger = LogManager.getLogger(BuilderGroup.class);

	public Group buildGroup(String title, Teacher teacher, Level level) {

		Group group = new Group();
		group.setTitle(title);
		group.setTeacher(teacher);
		group.setLevel(level);
			
		logger.debug("group has been created {}", group);
		return group;
	}
}