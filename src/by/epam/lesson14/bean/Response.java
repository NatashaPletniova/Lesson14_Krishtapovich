package by.epam.lesson14.bean;

import java.util.List;

import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;

public class Response {
	private boolean errorStatus;
	private int errorNumber;
	private String errorMessage;
	private String simpleMessage;
	private List<Product> products;
	private List<ProductItem> productItem;

	public List<ProductItem> getProductItem() {
		return productItem;
	}

	public void setProductItem(List<ProductItem> productItem) {
		this.productItem = productItem;
	}

	public boolean isErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(boolean errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSimpleMessage() {
		return simpleMessage;
	}

	public void setSimpleMessage(String simpleMessage) {
		this.simpleMessage = simpleMessage;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

}
