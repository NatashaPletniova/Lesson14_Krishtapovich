package by.epam.lesson14.dao.connectionpool;

import java.util.ResourceBundle;

/**
 * This class is designed to access the bundle file with information required
 * for connecting to the data storage.
 * 
 *
 */
public class DBResourseManager {

	private final static DBResourseManager instance = new DBResourseManager();

	private ResourceBundle bundle = ResourceBundle.getBundle("by.epam.lesson14.dao.connectionpool.bd");

	public static DBResourseManager getInstance() {
		return instance;

	}

	public String getValue(String key) {
		return bundle.getString(key);
	}
}
