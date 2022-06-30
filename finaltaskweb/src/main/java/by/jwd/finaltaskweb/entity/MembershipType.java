package by.jwd.finaltaskweb.entity;

import java.util.Objects;

public class MembershipType extends Entity {

	private Type title;
	private int maxClassQuantity;
	private double price;

	public MembershipType() {

	}

	public MembershipType(int id){
		super(id);
	}

	public int getMaxClassQuantity() {
		return maxClassQuantity;
	}

	public void setMaxClassQuantity(int maxClassQuantity) {
		this.maxClassQuantity = maxClassQuantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Type getTitle() {
		return title;
	}

	public void setTitle(Type title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxClassQuantity, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MembershipType other = (MembershipType) obj;
		return maxClassQuantity == other.maxClassQuantity
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("\nMembershipType:");
		sb.append("\n").append(super.toString());
		sb.append("title: ").append(title);
		sb.append("max quantity of classes: ").append(maxClassQuantity);
		sb.append("price: ").append(price);
		return sb.toString();
	}
}