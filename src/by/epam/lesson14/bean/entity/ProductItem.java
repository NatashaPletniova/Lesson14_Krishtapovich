package by.epam.lesson14.bean.entity;

import java.math.BigDecimal;

public class ProductItem extends Product {

	private int productItemID;
	private int manufacturingYear;
	private String itemNote;
	private int status;
	private int wearRate;

	public ProductItem() {

	}

	public ProductItem(int productID, int productCategoryID, String name, BigDecimal productBasePrice, int quantity,
			int availableQuantity, int productItemID, int manufacturingYear, String itemNote, int status,
			int wearRate) {

		super(productID, productCategoryID, name, productBasePrice, quantity, availableQuantity);

		this.productItemID = productItemID;
		this.manufacturingYear = manufacturingYear;
		this.itemNote = itemNote;
		this.status = status;
		this.wearRate = wearRate;

	}

	public void showProductItem() {

		System.out.println("ProductItemID: " + this.productItemID);
		System.out.println("ProductID: " + this.getProductID());
		System.out.println("Name: " + this.getName());
		System.out.println("AvailableQuantity: " + this.getAvailableQuantity());
		System.out.println("ProductBasePrice: " + this.getProductBasePrice());
		System.out.println("ManufacturingYear: " + this.manufacturingYear);
		System.out.println("ItemNote : " + this.itemNote);
		System.out.println("WearRate: " + this.wearRate);
		System.out.println("Status: " + this.status);

		System.out.println();

	}

	public int getProductItemID() {
		return productItemID;
	}

	public void setProductItemID(int productItemID) {
		this.productItemID = productItemID;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((itemNote == null) ? 0 : itemNote.hashCode());
		result = prime * result + manufacturingYear;
		result = prime * result + productItemID;
		result = prime * result + status;
		result = prime * result + wearRate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductItem other = (ProductItem) obj;
		if (itemNote == null) {
			if (other.itemNote != null)
				return false;
		} else if (!itemNote.equals(other.itemNote))
			return false;
		if (manufacturingYear != other.manufacturingYear)
			return false;
		if (productItemID != other.productItemID)
			return false;
		if (status != other.status)
			return false;
		if (wearRate != other.wearRate)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductItem [productItemID=" + productItemID + ", manufacturingYear=" + manufacturingYear
				+ ", itemNote=" + itemNote + ", status=" + status + ", wearRate=" + wearRate + ", getProductID()="
				+ getProductID() + ", getProductCategoryID()=" + getProductCategoryID() + ", getName()=" + getName()
				+ ", getProductBasePrice()=" + getProductBasePrice() + ", getQuantity()=" + getQuantity()
				+ ", getAvailableQuantity()=" + getAvailableQuantity() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}

}
