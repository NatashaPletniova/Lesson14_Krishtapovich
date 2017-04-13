package by.epam.lesson14.bean;

public class DeleteProductItemRequest extends Request {
	private int productItemID;

	public DeleteProductItemRequest() {
	}

	public int getProductItemID() {
		return productItemID;
	}

	public void setProductItemID(int productItemID) {
		this.productItemID = productItemID;
	}

}
