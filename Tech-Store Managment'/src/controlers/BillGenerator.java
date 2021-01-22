package controlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import models.Bill;
import models.BillItem;

public class BillGenerator {

	private static final int BILL_WIDTH = 36; //36 CHARS
	private static final String folderPath = "src/bills/";
	private static final File readNOBills = new File("src/databases/BillGeneratorDB.txt");
	private static PrintWriter writer;
	private static int billNO = 1;
	private BillGenerator() {}
	

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter write = new PrintWriter(readNOBills);
		write.flush();
		write.print(billNO);
		write.close();
	}
	
	static {
		Scanner fin;
		try {
			fin = new Scanner(readNOBills);
			billNO = fin.nextInt();
			fin.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error reading billNO");
		}
	}
	
	
	public static void printBill(Bill b) {
		try {
			//CashierName + billNO
			File destination = new File(folderPath+billNO);
			if(destination.exists()) 
				destination = new File(folderPath+billNO);
			billNO++;
			System.out.println(billNO);
			PrintWriter write = new PrintWriter(readNOBills);
			write.flush();
			write.print(billNO);
			write.close();
			writer = new PrintWriter(destination);
			writer.print(formatBill(b));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Using StringBuilder when making multiple concatinations to increase efficiency
	private static String formatBill(Bill b) {
		StringBuilder format = new StringBuilder();
        format.append( getBillHeader(b));	
        
        for(BillItem i : b.getItems()) {
        	format.append(formatItemInfo(i));
        }
        
        format.append(getBillFooter(b));
		return format.toString();
	}
	
	public static String formatItemInfo(BillItem i) {
		StringBuilder itemInfo = new StringBuilder();
		itemInfo.append(i.getQuantity() + " X " + i.getSellingPrice() + "\n");
		itemInfo.append(i.getItemName() + "\n");
		itemInfo.append(String.format("%36.2f", i.getTotalBillPrice()) + "\n");
		itemInfo.append(repeat('-', BILL_WIDTH) + "\n" );
		return itemInfo.toString();	
	}
	
	public static String getBillHeader(Bill b) {
		StringBuilder header = new StringBuilder();
		header.append("\n" + centerText("Tech-Store") + "\n");
		header.append(b.getDate()+"\n");
		header.append(repeat('#', BILL_WIDTH) + "\n\n");
		
		return header.toString();
	}
	
	private static String getBillFooter(Bill b) {	 
		String footer  = "TOTAL PRICE:" ;
		footer += String.format("%24.2f", b.getTotalBillPrice());
		return footer;
	}
	
	private static String centerText(String text) {
		int padding = (BILL_WIDTH - text.length())/2;
		return repeat(' ', padding) + text 
				+ repeat(' ', padding) + "\n";
		
	}
	
	
	private static String repeat(char c, int len) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len; i++)
			sb.append(c);
		return sb.toString();
	}
	
	
}
