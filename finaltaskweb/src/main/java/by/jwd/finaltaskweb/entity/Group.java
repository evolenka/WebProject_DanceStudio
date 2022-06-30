package by.jwd.finaltaskweb.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group extends Entity {

	
	private Teacher teacher;
	private String title;
	private Level level;
	private List<Schedule> schedule = new ArrayList<>();

	public Group() {
	}

	public Group(int id) {
		super(id);
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(level, schedule, teacher, title);
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
		Group other = (Group) obj;
		return level == other.level && Objects.equals(schedule, other.schedule)
				&& Objects.equals(teacher, other.teacher) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nGroup:");
		sb.append(super.toString());
		sb.append("\ntitle: ").append(title);
		sb.append("\nteacher: ").append(teacher);
		sb.append("\nlevel: ").append(level);
		sb.append(schedule.toString());
		return sb.toString();
	}
}