package by.epam.lesson14.command.impl;

import by.epam.lesson14.service.DBInitService;
import by.epam.lesson14.service.ServiceFactory;
import by.epam.lesson14.service.exception.ServiceException;

public class DestroyConnectionPoolCommand {

	public void execute() {
		ServiceFactory factory = ServiceFactory.getInstance();
		DBInitService dbInitService = factory.getDBInitService();

		try {
			dbInitService.destroyConnectionPoolData();

		} catch (ServiceException e) {

		}
	}

}
