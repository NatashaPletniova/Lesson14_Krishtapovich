package by.epam.lesson14.service;

import by.epam.lesson14.service.exception.ServiceException;

public interface DBInitService {
	void initConnectionPoolData() throws ServiceException;

	void destroyConnectionPoolData() throws ServiceException;

}
