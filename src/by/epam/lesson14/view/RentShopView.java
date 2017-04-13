package by.epam.lesson14.view;

import java.util.Iterator;
import java.util.List;

import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;

public class RentShopView {
	public static void ShowProductList(List<Product> listProduct) {
		System.out.println("");
		if (listProduct.isEmpty()) {
			System.out.println("There is not any product fit your requirement!");
		}
		Iterator<Product> iterator = listProduct.iterator();
		while (iterator.hasNext()) {
			iterator.next().showProduct();
		}

	}

	public static void ShowProductItemList(List<ProductItem> listProductItem) {
		System.out.println("");
		if (listProductItem.isEmpty()) {
			System.out.println("There is not any product fit your requirement!");
		}
		Iterator<ProductItem> iterator = listProductItem.iterator();
		while (iterator.hasNext()) {
			iterator.next().showProductItem();

		}

	}

}
