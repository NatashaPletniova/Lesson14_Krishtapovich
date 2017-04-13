package by.epam.lesson14.dao;

import by.epam.lesson14.dao.impl.DBInitDAO;
import by.epam.lesson14.dao.impl.RentShopDAO;

public class DAOFactory {

	private static final DAOFactory INSTANCE = new DAOFactory();

	private RentShop rentShopDAO = new RentShopDAO();
	private SourceInit initDAO = new DBInitDAO();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public RentShop getRentShopDAO() {
		return rentShopDAO;
	}

	public SourceInit getSourceInitDAO() {
		return initDAO;
	}

}
