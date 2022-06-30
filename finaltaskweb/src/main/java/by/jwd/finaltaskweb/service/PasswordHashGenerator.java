package by.jwd.finaltaskweb.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class PasswordHashGenerator {

	private static final PasswordHashGenerator instance = new PasswordHashGenerator();

	private static final int ITERATIONS = 65536;
	private static final int LENGTH = 128;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

	private PasswordHashGenerator() {
	}

	public static PasswordHashGenerator getInstance() {
		return instance;
	}

	public static String generate(String password, String login) throws ServiceException {
		
		byte[] salt = login.getBytes();
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, LENGTH);
		SecretKeyFactory factory;
		byte[] hash;
		try {
			factory = SecretKeyFactory.getInstance(ALGORITHM);
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new ServiceException();
		}
		return Base64.getEncoder().encodeToString(hash);
	}
}