package by.epam.lesson14.bean.entity;

import java.math.BigDecimal;

public class Product {

	private int productID;
	private int productCategoryID;
	private String name;
	private BigDecimal productBasePrice;
	private int quantity;
	private int availableQuantity;

	public Product() {
	}

	public Product(int productID, int productCategoryID, String name, BigDecimal productBasePrice, int quantity,
			int availableQuantity) {
		this.productID = productID;
		this.productCategoryID = productCategoryID;
		this.name = name;
		this.productBasePrice = productBasePrice;
		this.quantity = quantity;
		this.availableQuantity = availableQuantity;

	}

	public void showProduct() {

		System.out.println("ProductID: " + this.productID);
		System.out.println("ProductCategoryID : " + this.productCategoryID);
		System.out.println("Name: " + this.name);
		System.out.println("ProductBasePrice: " + this.productBasePrice);
		System.out.println("Quantity: " + this.quantity);
		System.out.println("AvailableQuantity: " + this.availableQuantity);

		System.out.println();

	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getProductCategoryID() {
		return productCategoryID;
	}

	public void setProductCategoryID(int productCategoryID) {
		this.productCategoryID = productCategoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getProductBasePrice() {
		return productBasePrice;
	}

	public void setProductBasePrice(BigDecimal productBasePrice) {
		this.productBasePrice = productBasePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productBasePrice == null) ? 0 : productBasePrice.hashCode());
		result = prime * result + availableQuantity;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + productCategoryID;
		result = prime * result + productID;
		result = prime * result + quantity;
		return result;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productCategoryID=" + productCategoryID + ", name=" + name
				+ ", ProductBasePrice=" + productBasePrice + ", quantity=" + quantity + ", availableQuantity="
				+ availableQuantity + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productBasePrice == null) {
			if (other.productBasePrice != null)
				return false;
		} else if (!productBasePrice.equals(other.productBasePrice))
			return false;
		if (availableQuantity != other.availableQuantity)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productCategoryID != other.productCategoryID)
			return false;
		if (productID != other.productID)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
