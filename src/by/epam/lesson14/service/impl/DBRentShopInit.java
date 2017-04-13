package by.epam.lesson14.service.impl;

import by.epam.lesson14.dao.DAOFactory;
import by.epam.lesson14.dao.SourceInit;
import by.epam.lesson14.dao.exception.DaoException;
import by.epam.lesson14.service.DBInitService;
import by.epam.lesson14.service.exception.ServiceException;

public class DBRentShopInit implements DBInitService {
	@Override
	public void initConnectionPoolData() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		SourceInit initDAO = factory.getSourceInitDAO();

		try {
			initDAO.init();
		} catch (DaoException e) {
			throw new ServiceException("Failed initialization.", e);
		}
	}

	@Override
	public void destroyConnectionPoolData() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		SourceInit initDAO = factory.getSourceInitDAO();

		try {
			initDAO.destroy();
		} catch (DaoException e) {
			throw new ServiceException("Failed destroying.", e);
		}
	}
	
}
