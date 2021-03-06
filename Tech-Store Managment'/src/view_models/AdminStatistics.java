package view_models;

import java.time.LocalDate;


import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Cashier;
import models.Manager;
import models.User;

public class AdminStatistics extends BorderPane {

	private PieChart chart = new PieChart();
	private VBox selectors = new VBox(15);
	private Label totalMoney;
	
	public AdminStatistics() {
		setPadding(new Insets(30));
		setRight(chart);
		totalMoney = new Label();
		initSelectors();
	}
	
	
	private double getTotalRevenue(LocalDate from, LocalDate to) {
		double revenue = 0;
		for(Cashier c : UserController.getCashiers())
			revenue += c.getTotal(from, to);
		return revenue;
	}
	
	public static double getPurchaseExpenses(LocalDate from, LocalDate to) {
		double expenses = 0;
		for(Manager m : UserController.getManagers())
			expenses += m.getTotal(from, to);
		return expenses;
	}
	
	public static double getSalaryExpenses(LocalDate from, LocalDate to) {
		double expenses = 0;
		for(User u : UserController.getUsers())
			expenses += (u.getsalary()/30) * getNrDays(from, to);
		return expenses;
	}
	
	private void initSelectors() {
		Label title = new Label("Income");
		title.setId("title");
		setAlignment(title, Pos.CENTER);
		setTop(title);
		DatePicker from = new DatePicker();
		from.setValue(LocalDate.now());
		DatePicker to = new DatePicker();
		to.setValue(LocalDate.now());
		from.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initPie(from.getValue(), to.getValue());				
			} });
		to.setOnAction(e->{
			initPie(from.getValue(), to.getValue());	
		});
		HBox cont1 = new HBox(15);
		cont1.getChildren().addAll(new Label("From:"), from);
		HBox cont2 = new HBox(30);
		cont2.getChildren().addAll(new Label("To :"), to);
		initPie(from.getValue(), to.getValue());
		selectors.setAlignment(Pos.CENTER);
		selectors.getChildren().addAll(cont1, cont2, totalMoney);
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
		totalMoney.setText("Total profit: " + (revenueP-salaryExpenses-purchaseExpenses));
		chart.getData().addAll(revenue, expenses, expensesP);
	}
	
	private static int getNrDays(LocalDate from, LocalDate to) {
		int days = 1;
		days += (to.getYear() - from.getYear()) * 365;
		days += (to.getDayOfYear() - from.getDayOfYear());
		return days;
	}
}
