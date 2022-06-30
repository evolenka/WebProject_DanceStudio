package by.jwd.finaltaskweb.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher extends User {

	private String danceStyle;
	private String portfolio;
	private List<Group> groups = new ArrayList<>();

	public Teacher() {
		setRole(Role.TEACHER);
	}

	public Teacher(int id) {
		super(id);
		setRole(Role.TEACHER);
	}

	public String getDanceStyle() {
		return danceStyle;
	}

	public void setDanceStyle(String danceStyle) {
		this.danceStyle = danceStyle;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(danceStyle, groups, portfolio);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		return Objects.equals(danceStyle, other.danceStyle) && Objects.equals(groups, other.groups)
				&& Objects.equals(portfolio, other.portfolio);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nTeacher:");
		sb.append(super.toString());
		sb.append("\ndancestyle: ").append(danceStyle);
		sb.append("\nportfolio: ").append(portfolio);
		sb.append(groups);
		return sb.toString();
	}
}