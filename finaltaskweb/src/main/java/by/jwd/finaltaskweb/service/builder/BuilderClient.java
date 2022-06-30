package by.jwd.finaltaskweb.service.builder;

import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Role;

public class BuilderClient {

	public Client buildClient(String login, String password, String surname, String name, String patronymic,
			String email, String phone) {

		Client client = new Client();
		client.setLogin(login);
		client.setPassword(password);
		client.setSurname(surname);
		client.setName(name);
		client.setPatronymic(patronymic);
		client.setEmail(email);
		client.setPhone(phone);
		client.setRole(Role.CLIENT);

		return client;
	}
}