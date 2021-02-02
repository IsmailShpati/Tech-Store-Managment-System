package view_models;

import java.time.LocalDate;


import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Bill;
import models.Cashier;
import models.Manager;
import models.User;

public class AdminStatistics extends BorderPane {

	private PieChart chart = new PieChart();
	private VBox selectors = new VBox(15);
	
	public AdminStatistics() {
		setPadding(new Insets(30));
		
		setRight(chart);
		initSelectors();
	}
	
	
	private double getTotalRevenue(LocalDate from, LocalDate to) {
		double revenue = 0;
		for(Cashier c : UserController.getCashiers())
			for(Bill b : c.getBillsInPeriod(from, to))
				revenue += b.getTotalBillPrice();
		return revenue;
	}
	
	public static double getPurchaseExpenses(LocalDate from, LocalDate to) {
		double expenses = 0;
		for(Manager m : UserController.getManagers())
			expenses += m.getTotalPurchasesDate(from, to);
		return expenses;
	}
	
	public static double getSalaryExpenses(LocalDate from, LocalDate to) {
		double expenses = 0;
		for(User u : UserController.getUsers())
			expenses += (u.getsalary()/30) * getNrDays(from, to);
		return expenses;
	}
	
	private void initSelectors() {
		DatePicker from = new DatePicker();
		from.setValue(LocalDate.now());
		DatePicker to = new DatePicker();
		to.setValue(LocalDate.now());
		
		HBox cont1 = new HBox(15);
		cont1.getChildren().addAll(new Label("From:"), from);
		HBox cont2 = new HBox(30);
		cont2.getChildren().addAll(new Label("To :"), to);
		HBox cont3 = new HBox();
		Button show = new Button("Show");
		show.setOnAction(e-> {
			initPie(from.getValue(), to.getValue());
		});
		cont3.setAlignment(Pos.CENTER_RIGHT);
		cont3.getChildren().add(show);
		initPie(from.getValue(), to.getValue());
		selectors.setAlignment(Pos.CENTER);
		selectors.getChildren().addAll(cont1, cont2, cont3);
		setLeft(selectors);
	}
	
	private void initPie(LocalDate a, LocalDate b) {
		chart.getData().clear();
		double revenueP = getTotalRevenue(a,  b);
		double salaryExpenses = getSalaryExpenses(a, b);
		double purchaseExpenses = getPurchaseExpenses(a, b);
		PieChart.Data revenue = new PieChart.Data("Revenue\n"+String.format("%.2f",revenueP), 
				revenueP);
		PieChart.Data expenses = new PieChart.Data("Salary Expenses\n"+
				String.format("%.2f",salaryExpenses),salaryExpenses);
		PieChart.Data expensesP = new PieChart.Data("Purchases Expenses\n"+
				String.format("%.2f",purchaseExpenses), purchaseExpenses);
		chart.getData().addAll(revenue, expenses, expensesP);
	}
	
	private static int getNrDays(LocalDate from, LocalDate to) {
		int days = 1;
		days += (to.getYear() - from.getYear()) * 365;
		days += (to.getDayOfYear() - from.getDayOfYear());
		return days;
	}
}
