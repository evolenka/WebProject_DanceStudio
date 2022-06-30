package by.jwd.finaltaskweb.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DanceClass extends Entity{

	private Schedule schedule;
	private LocalDate date;
	private boolean isActive;
	private List<Visit> visits;

	public DanceClass() {
	}

	public DanceClass(int id) {
		super (id);
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(visits, date, schedule);
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
		DanceClass other = (DanceClass) obj;
		return Objects.equals(visits, other.visits) && Objects.equals(date, other.date)
				&& Objects.equals(schedule, other.schedule);
		
	}
		
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nDanceClass:");
		sb.append("\n").append(super.toString());
		sb.append(schedule);
		sb.append("\ndate: ").append(date);
		sb.append(visits);
		return sb.toString();
	}
}