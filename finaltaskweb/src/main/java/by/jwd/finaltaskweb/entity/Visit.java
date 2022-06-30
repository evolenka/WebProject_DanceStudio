package by.jwd.finaltaskweb.entity;

import java.util.Objects;

public class Visit extends Entity {

	private Membership membership;
	private DanceClass danceClass;
	private Status status;

	public Visit() {
	}

	public Visit(int id) {
		super(id);
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public DanceClass getDanceClass() {
		return danceClass;
	}

	public void setDanceClass(DanceClass danceClass) {
		this.danceClass = danceClass;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(membership, danceClass, status);
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
		Visit other = (Visit) obj;
		return Objects.equals(membership, other.membership) && Objects.equals(danceClass, other.danceClass)
				&& status == other.status;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nVisit:");
		sb.append(super.toString());
		sb.append(membership);
		sb.append(danceClass);
		sb.append("\nStatus: ").append(status);
		return sb.toString();
	}
}