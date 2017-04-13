package by.epam.lesson14.command.impl;

import by.epam.lesson14.bean.DeleteProductItemRequest;
import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.service.RentShopService;
import by.epam.lesson14.service.ServiceFactory;
import by.epam.lesson14.service.exception.ServiceException;

public class DeleteProductItemByIDCommand implements Command {
	@Override
	public Response execute(Request request) {

		DeleteProductItemRequest deleteProductItemRequest = null;

		if (request instanceof DeleteProductItemRequest) {
			deleteProductItemRequest = (DeleteProductItemRequest) request;
		}

		int productItemID = deleteProductItemRequest.getProductItemID();

		Response response = new Response();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		RentShopService rentShopService = serviceFactory.getRentShopService();

		try {
			rentShopService.deleteProductItemByID(productItemID);
			response.setErrorStatus(false);
		} catch (ServiceException e) {
			response.setErrorStatus(true);
			response.setErrorMessage(e.getMessage());
		}

		return response;
	}

}