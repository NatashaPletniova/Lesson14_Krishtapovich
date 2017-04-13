package by.epam.lesson14.bean;

public class RentProductItemRequest extends Request {
	private int productItemID;
	private int rentPeriod;

	public RentProductItemRequest() {
	}

	public int getProductItemID() {
		return productItemID;
	}

	public void setProductItemID(int productItemID) {
		this.productItemID = productItemID;
	}

	public int getRentPeriod() {
		return rentPeriod;
	}

	public void setRentPeriod(int rentPeriod) {
		this.rentPeriod = rentPeriod;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	private int clientID;

}
