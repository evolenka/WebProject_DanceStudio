package by.jwd.finaltaskweb.entity;

import java.time.LocalTime;
import java.util.Objects;

public class Schedule extends Entity{
	
	private WeekDay weekday;
	private LocalTime time;
	private Group group;
	private int duration;
	
	public Schedule() {
	}
	
	public Schedule(int id) {
		super(id);
	}
	
	public WeekDay getWeekDay() {
		return weekday;
	}
	public void setWeekDay(WeekDay weekday) {
		this.weekday = weekday;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(duration, group, time, weekday);
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
		Schedule other = (Schedule) obj;
		return duration == other.duration && Objects.equals(group, other.group) && Objects.equals(time, other.time)
				&& weekday == other.weekday;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nSchedule:");
		sb.append("\n").append(super.toString());
		sb.append("\nweekday: ").append(weekday);
		sb.append("\ntime: ").append(time);
		sb.append("\ngroup id: ").append(group.getId());
		sb.append("\ngroup title: ").append(group.getTitle());
		sb.append("\ngroup level: ").append(group.getLevel());
		sb.append("\ngroup teacher: ").append(group.getTeacher());
		sb.append("\nduration: ").append(duration);
		return sb.toString();
	}
}