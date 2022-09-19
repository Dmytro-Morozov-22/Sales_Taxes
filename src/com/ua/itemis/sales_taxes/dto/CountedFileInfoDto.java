package com.ua.itemis.sales_taxes.dto;

import java.util.List;

/**
 * The class is used to pass information between methods namely this class serves as data transfer object.
 * The method countFile() returns type of this class and methods outputFile() and outputConsole()
 * take type of this class
 * @author Dmytro Morozov
 */
public class CountedFileInfoDto {

	private double totalPrice;
	private double saleTaxes;
	private List<String> listOfCountedProducts;
	
	public CountedFileInfoDto(double totalPrice, double saleTaxes, List<String> listOfCountedProducts) {
		this.totalPrice = totalPrice;
		this.saleTaxes = saleTaxes;
		this.listOfCountedProducts = listOfCountedProducts;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getSaleTaxes() {
		return saleTaxes;
	}

	public List<String> getListOfCountedProducts() {
		return listOfCountedProducts;
	}
	
}
