package by.jwd.finaltaskweb.service.builder;

import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.Teacher;

public class BuilderTeacher {
	
	public Teacher buildTeacher (String login, String password, String surname, String name, String style,
			String portfolio) {

		Teacher teacher = new Teacher();
		teacher.setLogin(login);
		teacher.setPassword(password);
		teacher.setSurname(surname);
		teacher.setName(name);
		teacher.setDanceStyle(style);
		teacher.setPortfolio(portfolio);
		teacher.setRole(Role.TEACHER);

		return teacher;
	}
}