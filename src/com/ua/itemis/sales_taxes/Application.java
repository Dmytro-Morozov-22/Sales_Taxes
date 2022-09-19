package com.ua.itemis.sales_taxes;

import com.ua.itemis.sales_taxes.dto.CountedFileInfoDto;
import com.ua.itemis.sales_taxes.file.FileCount;

/**
 * The application starts its work from the method 'countFile()' next from this
 * method is called another method 'chooseFile()' and this method calls the
 * window which asks a client to choose the file for counting. After that
 * Scanner starts to scan each row of the selected file and determine the amount
 * of product, product cost, and product name. Next calling the method
 * 'isImported()' program checks whether the product is imported and if it is
 * imported the word 'imported' will move to the beginning of the name of the
 * product. After that, the program checks whether the product is free of tax by
 * means of the method 'checkProductForTaxFree()'. Next, the program gets a
 * percent of product using the method 'getPercent()', and finally the method
 * 'countFile()' counts and returns the list of calculated products, tax for all
 * products, and total price of all products. Next, the program calls the method
 * 'outputFile()' and from this method is called the method 'saveFile()' that
 * involve the window which asks a client to select a directory and name of a
 * file after that the method 'outputFile()' starts to write information to the
 * created file. Next, the program calls the method 'outputConsole()' and this
 * method displays all calculated information in the console. I also created an
 * API for changing the amount of tax for basic and imported products and adding
 * the products that are free of tax to the list.
 * 
 * @author Dmytro Morozov morozovdima6@gmail.com
 *
 */
public class Application {

	public static void main(String[] args) throws Exception {
		CountedFileInfoDto countedFile = FileCount.countFile();
		FileCount.outputFile(countedFile);
		FileCount.outputConsole(countedFile);
	}
}
