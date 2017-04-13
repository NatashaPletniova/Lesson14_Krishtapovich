package by.epam.lesson14.dao.impl;

import by.epam.lesson14.dao.SourceInit;
import by.epam.lesson14.dao.connectionpool.ConnectionPool;
import by.epam.lesson14.dao.connectionpool.exception.ConnectionPoolException;
import by.epam.lesson14.dao.exception.DaoException;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;



/**
 * This class is the implementation of the
 * {@link com.epam.library.dao.SourceInit} for working with database.
 *
 */
public class DBInitDAO implements SourceInit {
	//private final static Logger Logger = LogManager.getLogger(DBInitDAO.class.getName());

	@Override
	public void init() throws DaoException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e) {
			//Logger.error(e);
			throw new DaoException("Failed connection pool initialization.", e);
		}

	}

	@Override
	public void destroy() throws DaoException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.dispose();
		} catch (ConnectionPoolException e) {
			//Logger.error(e);
			throw new DaoException("Failed connection pool destroying", e);
		}

	}

}
