package by.jwd.finaltaskweb.service.builder;

import java.time.LocalDate;

import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;


public class BuilderMembership {
	
	public Membership buildMembership (Client client, MembershipType type,	LocalDate startDate) {
			
		Membership membership= new Membership();
		
		membership.setClient(client);
		membership.setType(type);
		membership.setStartDate(startDate);
		membership.setEndDate(startDate.plusMonths(1));
		membership.setBalanceClassQuantity(type.getMaxClassQuantity());
		
		return membership;
	}
}