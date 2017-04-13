package by.epam.lesson14.command.impl;

import java.util.List;
import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.bean.ShowProductRequest;
import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.service.RentShopService;
import by.epam.lesson14.service.ServiceFactory;
import by.epam.lesson14.service.exception.ServiceException;

public class ShowProductByNameCommand implements Command {

	@Override
	public Response execute(Request request) {

		List<Product> productList = null;
		ShowProductRequest showProductRequest = null;

		if (request instanceof ShowProductRequest) {
			showProductRequest = (ShowProductRequest) request;
		}

		String name = showProductRequest.getName();

		Response response = new Response();
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		RentShopService rentShopService = serviceFactory.getRentShopService();

		try {
			productList = rentShopService.showProductByName(name);
			response.setErrorStatus(false);
			response.setProducts(productList);
		} catch (ServiceException e) {
			response.setErrorStatus(true);
			response.setErrorMessage(e.getMessage());

		}

		return response;
	}

}
