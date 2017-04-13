package by.epam.lesson14.start;

import java.util.List;

import by.epam.lesson14.bean.AddProductItemRequest;
import by.epam.lesson14.bean.DeleteProductItemRequest;
import by.epam.lesson14.bean.RentProductItemRequest;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.bean.ShowProductItemAvailabilityRequest;
import by.epam.lesson14.bean.ShowProductRequest;
import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;
import by.epam.lesson14.command.CommandName;
import by.epam.lesson14.controller.Controller;

//import by.epam.lesson14.dao.connectionpool.ConnectionPool;
import by.epam.lesson14.dao.connectionpool.exception.ConnectionPoolException;
import by.epam.lesson14.dao.exception.DaoException;
//import by.epam.lesson14.dao.impl.RentShopDAO;
import by.epam.lesson14.view.RentShopView;

public class Main {
	/**
	 * 
	 * @param args
	 * @throws ConnectionPoolException
	 * @throws DaoException
	 */
	public static void main(String args[]) // throws ConnectionPoolException,
											// DaoException
	{

		int availableQuantity = 1;
		int rentPeriod = 4; // By default the product is rented for 4 days.
		int clientID = 1; // This clientID is hard coded and should exist in
							// database. The work with clients is skipped for
							// now.
		Integer deletedProductItemID = 0;
		Integer rentedProductItemID = 0;
		int productID = 0;

		Controller controller = new Controller();
		// This is to show which bikes are available in database
		ShowProductRequest showProductRequest = new ShowProductRequest();
		showProductRequest.setCommandName(CommandName.SHOW_PRODUCT_BY_NAME);
		showProductRequest.setName("Bike");
		Response response1 = controller.doAction(showProductRequest);
		if (!response1.isErrorStatus()) {
			List<Product> listProduct = response1.getProducts();
			System.out.println("The following bikes types can be rented:");
			RentShopView.ShowProductList(listProduct);

		} else {
			System.out.println(response1.getErrorMessage());

		}
		// This is to show which mountain bikes are available in database.
		showProductRequest.setName("Mountain Bike Socks");
		response1 = controller.doAction(showProductRequest);

		if (!response1.isErrorStatus()) {
			List<Product> listProduct = response1.getProducts();
			System.out.println("The following mountain bikes types can be rented:");
			RentShopView.ShowProductList(listProduct);

			// The simulation of User choice.
			// This productID is to show the next step - selection the
			// particular products are available to rent.
			productID = listProduct.get(0).getProductID();
			availableQuantity = listProduct.get(0).getAvailableQuantity();

		} else {
			System.out.println(response1.getErrorMessage());

		}

		ShowProductItemAvailabilityRequest showProductItemAvailabilityRequest = new ShowProductItemAvailabilityRequest();
		showProductItemAvailabilityRequest.setCommandName(CommandName.SHOW_PRODUCT_ITEM_AVAILABILITY);
		showProductItemAvailabilityRequest.setProductID(productID);
		showProductItemAvailabilityRequest.setAvailableQuantity(availableQuantity);
		showProductItemAvailabilityRequest.setStatus(0); // status 0 in DB means
															// available to rent
		List<ProductItem> listProductItem = null;

		Response response2 = controller.doAction(showProductItemAvailabilityRequest);
		if (!response2.isErrorStatus()) {

			listProductItem = response2.getProductItem();
			System.out.println("The following mountain bikes can be rented:");
			RentShopView.ShowProductItemList(listProductItem);

			if (!listProductItem.isEmpty()) {
				deletedProductItemID = listProductItem.get(0).getProductItemID(); // this
																					// is
																					// to
																					// show
																					// the
																					// deletion
																					// case
				if (listProductItem.size() > 1) {
					rentedProductItemID = listProductItem.get(1).getProductItemID(); // this
																						// is
																						// to
																						// show
																						// the
																						// rent
																						// case
				}
			}

		} else {
			System.out.println(response2.getErrorMessage());

		}

		if (deletedProductItemID != 0) { // This is to track the situation of
											// multiply run.

			// DELETE the one of the available in the previous step product
			// item.
			// Quantity and AvailableQuantity for the corresponding product are
			// decreased.
			DeleteProductItemRequest deleteProductItemRequest = new DeleteProductItemRequest();
			deleteProductItemRequest.setCommandName(CommandName.DELETE_PRODUCTITEM_BY_ID);
			deletedProductItemID = listProductItem.get(0).getProductItemID();
			deleteProductItemRequest.setProductItemID(deletedProductItemID);
			Response response3 = controller.doAction(deleteProductItemRequest);
			if (!response3.isErrorStatus()) {

				System.out.println("The product item with ProductItemID = " + deletedProductItemID + " was deleted.");

			} else {
				System.out.println(response2.getErrorMessage());
			}
		}

		if (rentedProductItemID != 0) { // This is to track the situation of
										// multiply run.

			RentProductItemRequest rentProductItemRequest = new RentProductItemRequest();
			rentProductItemRequest.setCommandName(CommandName.RENT_PRODUCTITEM);
			rentProductItemRequest.setClientID(clientID);
			rentProductItemRequest.setProductItemID(rentedProductItemID);
			rentProductItemRequest.setRentPeriod(rentPeriod);
			Response response4 = controller.doAction(rentProductItemRequest);
			if (!response4.isErrorStatus()) {
				System.out.println("The rent of the product with ProductItemID = " + rentedProductItemID
						+ " was done successfully.");
			} else {
				System.out.println(response4.getErrorMessage());
			}

		}

		AddProductItemRequest addProductItemRequest = new AddProductItemRequest();
		addProductItemRequest.setCommandName(CommandName.ADD_PRODUCTITEM);
		addProductItemRequest.setProductID(2); // Mountain Bike Socks in the
												// database
		addProductItemRequest.setManufacturingYear(2016);
		addProductItemRequest.setItemNote(null);
		addProductItemRequest.setStatus(0);
		addProductItemRequest.setWearRate(10);
		addProductItemRequest.setProductCategoryID(null); // Bikes
		addProductItemRequest.setProductName(null);
		addProductItemRequest.setProductBasePrice(null);

		Response response5 = controller.doAction(addProductItemRequest);

		if (!response5.isErrorStatus()) {

			System.out.println("The new product item was added successfully. ");

		} else {
			System.out.println(response5.getErrorMessage());
		}

		addProductItemRequest.setProductID(22); // There is not such product in
												// the database.
												// New product will be created.
		addProductItemRequest.setManufacturingYear(2016);
		addProductItemRequest.setItemNote("Just added");
		addProductItemRequest.setStatus(0);
		addProductItemRequest.setWearRate(15);
		addProductItemRequest.setProductCategoryID(2); // Clothing
		addProductItemRequest.setProductName("Sport jacket");
		addProductItemRequest.setProductBasePrice(10);
		response5 = controller.doAction(addProductItemRequest);

		if (!response5.isErrorStatus()) {

			System.out.println("The new product item was added successfully. ");

		} else {
			System.out.println(response5.getErrorMessage());
		}

	}

}
