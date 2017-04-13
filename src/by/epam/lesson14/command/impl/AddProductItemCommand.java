package by.epam.lesson14.command.impl;

import by.epam.lesson14.bean.AddProductItemRequest;
import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.service.RentShopService;
import by.epam.lesson14.service.ServiceFactory;
import by.epam.lesson14.service.exception.ServiceException;

public class AddProductItemCommand implements Command {

	@Override
	public Response execute(Request request) {

		AddProductItemRequest addProductItemRequest = null;

		if (request instanceof AddProductItemRequest) {
			addProductItemRequest = (AddProductItemRequest) request;
		}

		int productID = addProductItemRequest.getProductID();
		int manufacturingYear = addProductItemRequest.getManufacturingYear();
		String itemNote = addProductItemRequest.getItemNote();
		int status = addProductItemRequest.getStatus();
		int wearRate = addProductItemRequest.getWearRate();
		Integer productCategoryID = addProductItemRequest.getProductCategoryID();
		String productName = addProductItemRequest.getProductName();
		Integer productBasePrice = addProductItemRequest.getProductBasePrice();

		Response response = new Response();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		RentShopService rentShopService = serviceFactory.getRentShopService();

		try {
			rentShopService.addProductItem(productID, manufacturingYear, itemNote, status, wearRate, productCategoryID,
					productName, productBasePrice);
			;
			response.setErrorStatus(false);
		} catch (ServiceException e) {
			response.setErrorStatus(true);
			response.setErrorMessage(e.getMessage());
		}

		return response;
	}

}
