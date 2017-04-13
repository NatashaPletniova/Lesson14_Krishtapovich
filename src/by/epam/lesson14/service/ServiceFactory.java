package by.epam.lesson14.service;

import by.epam.lesson14.service.impl.DBRentShopInit;
import by.epam.lesson14.service.impl.RentShopGeneralService;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();

	private RentShopService rentShopService = new RentShopGeneralService();
	private DBInitService dbInitService = new DBRentShopInit();

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public RentShopService getRentShopService() {
		return rentShopService;
	}

	public DBInitService getDBInitService() {
		return dbInitService;
	}

}
