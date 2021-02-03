package view_models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Bill;
import models.BillItem;
import models.Cashier;

public class CashierStatistics extends BorderPane {

	private VBox selections = new VBox(15);
	private BarChart<String, Integer> chart;
	private BarChart<String, Double> moneyChart;
	private DatePicker fromDate, toDate;
	public CashierStatistics() {
		setPadding(new Insets(30));
		initQuantityChart();
		initMoneyChart();
		setTotalMoneyMade(LocalDate.now(), LocalDate.now());
		initGrid();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initQuantityChart() {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setAnimated(false);
		xAxis.setLabel("Items");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Quantities sold");
		chart = new BarChart(xAxis, yAxis);
		chart.setLegendVisible(false);
		chart.setAnimated(false);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initMoneyChart() {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setAnimated(false);
		xAxis.setLabel("Cashiers");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Money");
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis,null," LEK"));
		moneyChart = new BarChart(xAxis, yAxis);
		moneyChart.setLegendVisible(false);
		moneyChart.setAnimated(false);
		setRight(moneyChart);
	}
	
	private void initGrid() {
		ComboBox<String> typeSelector = new ComboBox<>();
		typeSelector.getItems().addAll("Revenue generated", "Bills printed", "Items sold");
		typeSelector.setValue("Revenue generated");
		ComboBox<String> cashierSelector = new ComboBox<>();
		cashierSelector.getItems().add("All");
		cashierSelector.setValue("All");
		for(Cashier c : UserController.getCashiers())
			cashierSelector.getItems().add(c.toString());
		selections.setAlignment(Pos.CENTER);
		HBox firstRow = new HBox(10);
		firstRow.getChildren().addAll(new Label("Cashier/s: "), cashierSelector);
		
		HBox container = new HBox(10);
		
		fromDate = new DatePicker(LocalDate.now());
		container.getChildren().addAll(new Label("From"), fromDate);
		toDate = new DatePicker(LocalDate.now());
		HBox secondCont = new HBox(25);
		secondCont.getChildren().addAll(new Label("To"), toDate);
		
		typeSelector.setOnAction(e->{
			handleChange(cashierSelector, typeSelector);
		});
		cashierSelector.setOnAction(e->{
			handleChange(cashierSelector, typeSelector);
		});
		fromDate.setOnAction(e->{
			handleChange(cashierSelector, typeSelector);
		});
		toDate.setOnAction(e->{
			handleChange(cashierSelector, typeSelector);
		});
		
	
	
	 selections.getChildren().addAll(typeSelector, firstRow, container, secondCont);
	 setMargin(selections, new Insets(0, 30, 0, 0));
	 setLeft(selections);
	}
	
	private void handleChange(ComboBox<String> cashierSelector, ComboBox<String> typeSelector) {
		Cashier c = getCashier(cashierSelector);
		switch(typeSelector.getSelectionModel().getSelectedIndex()) {
		case 0:
			changeForMoney();
			if(c != null) 
				setTotalMoneyMade(fromDate.getValue(), toDate.getValue(), c);
			else 
				setTotalMoneyMade(fromDate.getValue(), toDate.getValue());
			break;
		case 1:
			changeForBills();
			if(c != null) 
				setTotalBillsDate(fromDate.getValue(), toDate.getValue(), c);
			
			else 
				setTotalBillsData(fromDate.getValue(), toDate.getValue());
			break;
		case 2:
			changeForTotal();
			if(c!=null) 
				setCashierData(fromDate.getValue(), toDate.getValue(), c);
			else 
				setTotalData(fromDate.getValue(), toDate.getValue());
			break;
		}
	}
	
	
	private Cashier getCashier(ComboBox<String> cashierSelector) {
		if(cashierSelector.getSelectionModel().getSelectedIndex() == 0)
			return null;
		 
		return UserController.getCashiers().get(
					cashierSelector.getSelectionModel().getSelectedIndex()-1);
			
		
	}
	
	private void changeForTotal() {
		setRight(chart);
		chart.getData().clear();
		chart.getXAxis().setLabel("Items");
		chart.getYAxis().setLabel("Quantities sold");
	}
	
	private void changeForBills() {
		setRight(chart);
		chart.getData().clear();
		chart.getXAxis().setLabel("Cashiers");
		chart.getYAxis().setLabel("Bill printed");
	}
	
	private void changeForMoney() {
		setRight(moneyChart);
		moneyChart.getData().clear();
		moneyChart.getXAxis().setLabel("Cashiers");
		moneyChart.getYAxis().setLabel("Revenue");
	}
	
	private void setTotalData(LocalDate from, LocalDate to) {	
		ArrayList<BillItem> items = new ArrayList<>();
		
		for(Cashier c : UserController.getCashiers()) 
			for(Bill b : c.getBillsInPeriod(from, to))
				for(BillItem i : b.getItems())
					items.add(i);
		
		for(BillItem i : removeDuplicates(items)) {
			Series<String, Integer> series = new Series<>();
			series.getData().add(new Data<>(i.getItemName(), i.getQuantity()));
			chart.getData().add(series);
		}
	}
	
	private void setCashierData(LocalDate from, LocalDate to, Cashier c) {
		ArrayList<BillItem> items = new ArrayList<>();
		
		for(Bill b : c.getBillsInPeriod(from, to)) 
			for(BillItem i : b.getItems())
				items.add(i);
		
		for(BillItem i : removeDuplicates(items)) {
			Series<String, Integer> series = new Series<>();
			series.getData().add(new Data<>(i.getItemName(), i.getQuantity()));
			chart.getData().add(series);
		}
	}
	
	private void setTotalBillsData(LocalDate from, LocalDate to) {
		
		for(Cashier c : UserController.getCashiers()) {
			setTotalBillsDate(from, to, c);
 		}
	}
	
	private void setTotalBillsDate(LocalDate from, LocalDate to, Cashier c) {
		Series<String, Integer> seris = new Series<>();
		seris.getData().add(new Data<>(c.getName(), c.getNrBillsInPeriod(from, to)));
		chart.getData().add(seris);
	}
	
	private void setTotalMoneyMade(LocalDate from, LocalDate to) {

		for(Cashier c : UserController.getCashiers()) {
			setTotalMoneyMade(from, to, c);
		}
	}
	
	private void setTotalMoneyMade(LocalDate from, LocalDate to, Cashier c) {
		Series<String, Double> seris = new Series<>();
		seris.getData().add(new Data<>(c.getName(), c.getTotal(from, to)));
		moneyChart.getData().add(seris);
	}

	private static ArrayList<BillItem> removeDuplicates(ArrayList<BillItem> items){
		Collections.sort(items);
		ArrayList<BillItem> updatedItems = new ArrayList<>();
		for(BillItem current : updatedItems)
			System.out.println("Current "+current.getItemName() + " " + current.getQuantity());

		if(items.size() > 0) {
			BillItem current = new BillItem(items.get(0));

			updatedItems.add(current);
			for(int ind = 1; ind < items.size(); ind++ ) {
				BillItem i = items.get(ind);
					if(i.equals(current)) 
						current.setQuantity(current.getQuantity() + i.getQuantity());

					else {
						current = new BillItem(i);
						updatedItems.add(current);
					}
			}
		}
		else 
			new Alert(AlertType.WARNING, "No bills in current period").showAndWait();
		return updatedItems;
	}
}
