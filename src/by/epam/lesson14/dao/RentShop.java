package by.epam.lesson14.dao;

import java.util.List;

import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;
import by.epam.lesson14.dao.exception.DaoException;

public interface RentShop {

	void addProductItem(int productID, int manufacturingYear, String itemNote, int status, int wearRate,
			Integer productCategoryID, String productName, Integer productBasePrice) throws DaoException;

	void deleteProductItemByID(int productItemID) throws DaoException;

	List<Product> showProductByName(String name) throws DaoException;

	List<ProductItem> showProductItemAvailability(int productID, int status, int availableQuantity) throws DaoException;

	int rentProductItem(int productItemID, int rentPeriod, int clientID) throws DaoException;

}
