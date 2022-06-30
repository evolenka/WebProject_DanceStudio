package by.jwd.finaltaskweb.service.builder;

import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Visit;

public class BuilderVisit {

	public Visit buildVisit(Membership membership, DanceClass danceClass) {

		Visit visit = new Visit();
		visit.setDanceClass(danceClass);
		visit.setMembership(membership);
		visit.setStatus(Status.PLANNED);
		return visit;
	}
}
