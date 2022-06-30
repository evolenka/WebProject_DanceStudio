package by.jwd.finaltaskweb.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Membership extends Entity {

	private Client client;
	private MembershipType type;
	private LocalDate startDate;
	private LocalDate endDate;
	private int balanceClassQuantity;

	public Membership() {
	}

	public Membership(int id) {
		super(id);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getBalanceClassQuantity() {
		return balanceClassQuantity;
	}

	public void setBalanceClassQuantity(int balanceClassQuantity) {
		this.balanceClassQuantity = balanceClassQuantity;
	}
	
	
	public MembershipType getType() {
		return type;
	}

	public void setType(MembershipType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(balanceClassQuantity, client, endDate, startDate, type);
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
		Membership other = (Membership) obj;
		return balanceClassQuantity == other.balanceClassQuantity && Objects.equals(client, other.client)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nMembership:");
		sb.append("\n").append(super.toString());
		sb.append("start date: ").append(startDate);
		sb.append("end date: ").append(endDate);
		sb.append("balanceClassQuantity: ").append(balanceClassQuantity);
		sb.append(client);
		sb.append(type);
		return sb.toString();
	}
}