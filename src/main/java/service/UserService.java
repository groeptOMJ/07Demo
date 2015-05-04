package service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import bean.Score;

/**
 * Demo Service for DAO or Access to other service
 *
 * @author Stijn Heylen
 *
 */
public class UserService {

	static final Logger logger = Logger.getLogger(UserService.class);

	private String url, user, password;

	private Map<String, Score> USERS = new HashMap<String, Score>();

	public UserService(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		// dummy input - should come from DB
		USERS.put("U1", new Score("U1"));
	}

	/**
	 * Gets the user Score object or initiates one when new. TODO: get/send this to DB or other service
	 *
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Score getData(String user) throws Exception {

		Score s = USERS.get(user);
		// create new user when unexisting
		if (s == null) {
			USERS.put(user, new Score(user));
			s = USERS.get(user);
		}
		return s;

	}
}
