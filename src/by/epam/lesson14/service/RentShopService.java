package by.epam.lesson14.service;

import java.util.List;
import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;
import by.epam.lesson14.service.exception.ServiceException;

public interface RentShopService {
	void addProductItem(int productID, int manufacturingYear, String itemNote, int status, int wearRate,
			Integer productCategoryID, String productName, Integer productBasePrice) throws ServiceException;

	void deleteProductItemByID(int productItemID) throws ServiceException;

	List<Product> showProductByName(String name) throws ServiceException;

	List<ProductItem> showProductItemAvailability(int productID, int status, int availableQuantity)
			throws ServiceException;

	int rentProductItem(int productItemID, int rentPeriod, int clientID) throws ServiceException;
}
