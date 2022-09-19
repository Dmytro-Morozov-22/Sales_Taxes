package com.ua.itemis.sales_taxes.tax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The class contains fields for basic tax, tax for imported products, and a
 * list of products that are free of tax. The class also has methods that are
 * used for calculating the taxes and manage free of tax products
 * 
 * @author Dmytro Morozov morozovdima6@gmail.com
 */
public class TaxFree {

	private static int basicTax = 10;
	private static int importTax = 5;
	private static List<String> taxFreeProducts = new ArrayList<>();

	static {
		taxFreeProducts.add("chocolate");
		taxFreeProducts.add("book");
		taxFreeProducts.add("pills");
	}

	/**
	 * The method sets the amount of a new tax on general products
	 * 
	 * @param basicTax is the amount of tax on general products
	 */
	public static void setBasicTax(int basicTax) {
		TaxFree.basicTax = basicTax;
	}

	/**
	 * The method sets the amount of a new tax on imported products
	 * 
	 * @param importTax is the amount of tax on imported products
	 */
	public static void setImportTax(int importTax) {
		TaxFree.importTax = importTax;
	}

	/**
	 * The method calculates amount of percent for the product
	 * 
	 * @param productAmount is amount items of the product
	 * @param productCost   is cost of the product
	 * @param isImported    marks whether the product is imported
	 * @param taxFree       marks whether the product is free of tax
	 * @return amount of percent for the product
	 */
	public static double getPercent(int productAmount, double productCost, boolean isImported, boolean taxFree) {
		double percent = 0;
		if (isImported)
			percent = percent + ((productAmount * productCost) * importTax / 100);
		if (!taxFree)
			percent = percent + ((productAmount * productCost) * basicTax / 100);
		return percent;
	}

	/**
	 * The method checks whether the product does not belong to the category free of
	 * tax products
	 * 
	 * @param product for checking
	 * @return information about product
	 */
	public static boolean checkProductForTaxFree(String product) {
		boolean isSpecialProduct = false;
		for (String item : taxFreeProducts) {
			if (product.contains(item))
				isSpecialProduct = true;
		}
		return isSpecialProduct;
	}

	/**
	 * The method adds the product to the list free of tax products
	 * 
	 * @param product which must be free of tax
	 */
	public static void addTaxFreeProduct(String product) {
		taxFreeProducts.add(product);
	}

	/**
	 * The method checks whether the product belongs to the category of imported
	 * products
	 * 
	 * @param product for checking
	 * @return map which contains information on whether the product is imported and
	 *         the modified product
	 */
	private static Map<Boolean, String> checkForImported(String product) {
		boolean isImported = false;
		if (product.contains("imported")) {
			isImported = true;
			product = changePositionOfWord(product);
		}
		return Map.of(isImported, product);
	}

	/**
	 * The method moves the word "imported" to the beginning of the product name
	 * 
	 * @param product name
	 * @return modified product name
	 */
	private static String changePositionOfWord(String product) {
		int firstCharOfWord = product.indexOf("imported");
		if (firstCharOfWord > 0) {
			String importedPart = product.substring(firstCharOfWord, firstCharOfWord + 9);
			String firstPart = product.substring(0, firstCharOfWord);
			String endPart = product.substring(firstCharOfWord + 9, product.length());
			product = importedPart + firstPart + endPart;
		}
		return product;
	}

	/**
	 * The method gets information about the product from the map
	 * 
	 * @param product for checking
	 * @return information about product
	 */
	public static boolean isImported(String product) {
		return checkForImported(product).entrySet().iterator().next().getKey();
	}

	/**
	 * The method gets modified product from the map
	 * 
	 * @param product
	 * @return modified product
	 */
	public static String moveImported(String product) {
		return checkForImported(product).entrySet().iterator().next().getValue();
	}

	/**
	 * The method rounds up to the nearest 0.05
	 * 
	 * @param number which must be rounded
	 * @return rounded number
	 */
	public static double round(double number) {
		return Math.round(number * 20.0) / 20.0;
	}

}
