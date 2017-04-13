package by.epam.lesson14.dao.connectionpool;

/**
 * Class stores constants that are used to connect to the data storage.
 * 
 * @author Natallia Krishtapovich
 *
 */
public final class DBParameter {
	/**
	 * Driver for connecting to the data storage.
	 */
	public static final String DB_DRIVER = "db.driver";
	/**
	 * URL for connecting to the data storage.
	 */
	public static final String DB_URL = "db.url";
	/**
	 * User's name.
	 */
	public static final String DB_USER = "db.user";
	/**
	 * Password to access the data storage.
	 */
	public static final String DB_PASSWORD = "db.password";
	/**
	 * ConnectionPool's size.
	 */
	public static final String DB_POOL_SIZE = "db.poolsize";
}
