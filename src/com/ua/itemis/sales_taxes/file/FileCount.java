package com.ua.itemis.sales_taxes.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import com.ua.itemis.sales_taxes.dto.CountedFileInfoDto;
import com.ua.itemis.sales_taxes.exception.WrongFileContentException;
import com.ua.itemis.sales_taxes.tax.TaxFree;

/**
 * This class contains methods for working with the chosen file
 * 
 * @author Dmytro Morozov morozovdima6@gmail.com
 */
public class FileCount {

	/**
	 * The program uses this method in order to get file from client directory
	 * 
	 * @return a chosen file
	 */
	private static File chooseFile() {
		JFileChooser chooser = new JFileChooser();
		int openDialog = chooser.showOpenDialog(null);
		return openDialog == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile() : null;
	}

	/**
	 * This method asks the client for the directory and file name and extension to
	 * save the file
	 * 
	 * @return a selected file
	 */
	private static File saveFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(new File("D:\\output.txt"));
		int saveDialog = chooser.showSaveDialog(null);
		return saveDialog == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile() : null;
	} 

	/**
	 * This method uses a lot of other methods to calculate product cost, product
	 * amount, and product name, and use them to calculate a tax for all products
	 * and total price
	 * 
	 * @return CountedFileInfoDto which consists a list of calculated products, tax
	 *         for all products, and total price
	 */
	public static CountedFileInfoDto countFile() {
		File selectedFile = chooseFile();
		List<String> productsList = new ArrayList<>();
		double saleTaxes = 0;
		double totalPrice = 0;

		if (selectedFile != null) {
			try (Scanner myReader = new Scanner(new File(selectedFile.getAbsolutePath()))) {

				while (myReader.hasNextLine()) {

					String textLine = myReader.nextLine();
					int index = textLine.indexOf(" ");
					int lastIndex = textLine.lastIndexOf(" ");

					int productAmount = Integer.parseInt(textLine.substring(0, index));
					double productCost = Double.parseDouble(textLine.substring(lastIndex));
					String productName = textLine.substring((index + 1), (lastIndex - 3));

					boolean isImported = TaxFree.isImported(productName);
					String checkedProduct = TaxFree.moveImported(productName);

					boolean taxFree = TaxFree.checkProductForTaxFree(checkedProduct);
					double percent = TaxFree.getPercent(productAmount, productCost, isImported, taxFree);
					double costWithPercent = percent + (productAmount * productCost);

					saleTaxes = saleTaxes + percent;
					totalPrice = totalPrice + costWithPercent;
					productsList.add(productAmount + " " + checkedProduct + ": " + TaxFree.round(costWithPercent));
				}

			} catch (NumberFormatException e) {
				throw new WrongFileContentException("The file content is wrong! \r\n"
						+ "The correct file format is \"number + space + text + space + number\"");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new CountedFileInfoDto(TaxFree.round(totalPrice), TaxFree.round(saleTaxes), productsList);
	}

	/**
	 * The method writes information about the product, product cost, and product
	 * tax to the selected file
	 * 
	 * @param fileInfo consists of a list of products, tax for all products, and
	 *                 total price
	 */
	public static void outputFile(CountedFileInfoDto fileInfo) {
		File fileToSave = saveFile();
		if (fileToSave != null) {
			try (FileWriter fw = new FileWriter(fileToSave)) {
				for (String item : fileInfo.getListOfCountedProducts()) {
					fw.write(item + '\n');
				}
				fw.write("Sales Taxes: " + fileInfo.getSaleTaxes() + '\n');
				fw.write("Total: " + fileInfo.getTotalPrice());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The method displays information about the product, product cost, and product
	 * tax to the console
	 * 
	 * @param fileInfo consists of a list of products, tax for all products, and
	 *                 total price
	 */
	public static void outputConsole(CountedFileInfoDto fileInfo) {
		for (String item : fileInfo.getListOfCountedProducts()) {
			System.out.println(item);
		}
		System.out.println("Sales Taxes: " + fileInfo.getSaleTaxes());
		System.out.println("Total: " + fileInfo.getTotalPrice());
	}

}
