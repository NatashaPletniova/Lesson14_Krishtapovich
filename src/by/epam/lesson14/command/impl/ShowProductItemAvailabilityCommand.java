package by.epam.lesson14.command.impl;

import java.util.List;

import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.bean.ShowProductItemAvailabilityRequest;

import by.epam.lesson14.bean.entity.ProductItem;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.service.RentShopService;
import by.epam.lesson14.service.ServiceFactory;
import by.epam.lesson14.service.exception.ServiceException;

public class ShowProductItemAvailabilityCommand implements Command {

	@Override
	public Response execute(Request request) {

		List<ProductItem> productItemList = null;
		ShowProductItemAvailabilityRequest showProductItemAvailabilityRequest = null;

		if (request instanceof ShowProductItemAvailabilityRequest) {
			showProductItemAvailabilityRequest = (ShowProductItemAvailabilityRequest) request;
		}

		int productID = showProductItemAvailabilityRequest.getProductID();
		int status = showProductItemAvailabilityRequest.getStatus();
		int availableQuantity = showProductItemAvailabilityRequest.getAvailableQuantity();

		Response response = new Response();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		RentShopService rentShopService = serviceFactory.getRentShopService();

		try {
			productItemList = rentShopService.showProductItemAvailability(productID, status, availableQuantity);
			response.setErrorStatus(false);
			response.setProductItem(productItemList);
		} catch (ServiceException e) {
			response.setErrorStatus(true);
			response.setErrorMessage(e.getMessage());

		}

		return response;
	}

}
