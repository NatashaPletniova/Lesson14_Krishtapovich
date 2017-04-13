package by.epam.lesson14.command.impl;

import by.epam.lesson14.bean.RentProductItemRequest;
import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.service.RentShopService;
import by.epam.lesson14.service.ServiceFactory;
import by.epam.lesson14.service.exception.ServiceException;

public class RentProductItemCommand implements Command {

		@Override
		public Response execute(Request request) {

			int spResult;
			RentProductItemRequest rentProductItemRequest = null;

			if (request instanceof RentProductItemRequest) {
				rentProductItemRequest = (RentProductItemRequest) request;
			}

			int productItemID   = rentProductItemRequest.getProductItemID();
			int rentPeriod = rentProductItemRequest.getRentPeriod();
			int clientID = rentProductItemRequest.getClientID();

			Response response = new Response();
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			RentShopService rentShopService = serviceFactory.getRentShopService();

			try {
				spResult = rentShopService.rentProductItem(productItemID, rentPeriod, clientID);
				response.setErrorStatus(false);
				response.setErrorNumber(spResult);
			} catch (ServiceException e) {
				response.setErrorStatus(true);
				response.setErrorMessage(e.getMessage());
		
			}

			return response;
		}

}
