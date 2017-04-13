package by.epam.lesson14.bean;

public class ShowProductItemAvailabilityRequest extends Request {

	private int productID;
	private int status;
	private int availableQuantity;

	public ShowProductItemAvailabilityRequest() {

	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

}
