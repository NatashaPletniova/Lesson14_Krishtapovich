package by.epam.lesson14.bean;

public class AddProductItemRequest extends Request {
	private int productID;
	private int manufacturingYear;
	private String itemNote;
	private int status;
	private int wearRate;
	private Integer productCategoryID;
	private String productName;
	private Integer productBasePrice;

	public AddProductItemRequest() {

	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(int manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}

	public String getItemNote() {
		return itemNote;
	}

	public void setItemNote(String itemNote) {
		this.itemNote = itemNote;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWearRate() {
		return wearRate;
	}

	public void setWearRate(int wearRate) {
		this.wearRate = wearRate;
	}

	public Integer getProductCategoryID() {
		return productCategoryID;
	}

	public void setProductCategoryID(Integer productCategoryID) {
		this.productCategoryID = productCategoryID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductBasePrice() {
		return productBasePrice;
	}

	public void setProductBasePrice(Integer productBasePrice) {
		this.productBasePrice = productBasePrice;
	}


}
