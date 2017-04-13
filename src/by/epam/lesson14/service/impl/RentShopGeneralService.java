package by.epam.lesson14.service.impl;

import java.util.List;

import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;
import by.epam.lesson14.dao.DAOFactory;
import by.epam.lesson14.dao.RentShop;
import by.epam.lesson14.dao.exception.DaoException;
import by.epam.lesson14.service.RentShopService;
import by.epam.lesson14.service.exception.ServiceException;

public class RentShopGeneralService implements RentShopService {

	@Override
	public void addProductItem(int productID, int manufacturingYear, String itemNote, int status, int wearRate,
			Integer productCategoryID, String productName, Integer productBasePrice) throws ServiceException {
		if ((productID <= 0) || (manufacturingYear <= 0) || (status < 0) || (wearRate < 0)) {
			throw new ServiceException("Invalid name parameter");
		}
		DAOFactory factory = DAOFactory.getInstance();
		RentShop productDao = factory.getRentShopDAO();

		try {
			productDao.addProductItem(productID, manufacturingYear, itemNote, status, wearRate, productCategoryID,
					productName, productBasePrice);
			;
		} catch (DaoException e) {
			throw new ServiceException("Failed data obtaining.", e);
		}

	}

	@Override
	public List<Product> showProductByName(String name) throws ServiceException {
		if (name == null || name.isEmpty()) {
			throw new ServiceException("Invalid name parameter");
		}
		DAOFactory factory = DAOFactory.getInstance();
		RentShop productDao = factory.getRentShopDAO();
		List<Product> productList = null;

		try {
			productList = productDao.showProductByName(name);
		} catch (DaoException e) {
			throw new ServiceException("Failed data obtaining.", e);
		}

		return productList;

	}

	@Override
	public List<ProductItem> showProductItemAvailability(int productID, int status, int availableQuantity)
			throws ServiceException {

		if ((productID <= 0) || (status < 0) || (availableQuantity < 0)) {
			throw new ServiceException("Invalid code parameter");
		}

		DAOFactory factory = DAOFactory.getInstance();
		RentShop productDao = factory.getRentShopDAO();
		List<ProductItem> productItemList = null;

		try {
			productItemList = productDao.showProductItemAvailability(productID, status, availableQuantity);
		} catch (DaoException e) {
			throw new ServiceException("Failed data obtaining.", e);
		}

		return productItemList;

	}

	@Override
	public void deleteProductItemByID(int productItemID) throws ServiceException {

		if ((productItemID <= 0)) {
			throw new ServiceException("Invalid code parameter");
		}

		DAOFactory factory = DAOFactory.getInstance();
		RentShop productDao = factory.getRentShopDAO();

		try {
			productDao.deleteProductItemByID(productItemID);
		} catch (DaoException e) {
			throw new ServiceException("Failed data deletion based on ID of patticular product item.", e);
		}

	}

	@Override
	public int rentProductItem(int productItemID, int rentPeriod, int clientID) throws ServiceException {
		int spResult = 0;

		if ((productItemID <= 0) || (rentPeriod <= 0) || (clientID <= 0)) {
			throw new ServiceException("Invalid parameters");
		}
		DAOFactory factory = DAOFactory.getInstance();
		RentShop productDao = factory.getRentShopDAO();

		try {
			spResult = productDao.rentProductItem(productItemID, rentPeriod, clientID);
		} catch (DaoException e) {
			throw new ServiceException("The rental is failed.", e);
		}
		return spResult;
	}

}
