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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Bill;
import models.BillItem;
import models.Cashier;

public class CashierStatistics extends BorderPane {

	private GridPane selections = new GridPane();
	private BarChart<String, Integer> chart;
	private BarChart<String, Double> moneyChart;
	private DatePicker fromDate, toDate;
	private Label totalLabel;
	
	public CashierStatistics() {
		totalLabel = new Label();
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
		VBox container = new VBox(15);
		container.setAlignment(Pos.CENTER);
		selections.setVgap(15);
		selections.setHgap(10);
		ComboBox<String> typeSelector = new ComboBox<>();
		typeSelector.getItems().addAll("Revenue generated", "Bills printed", "Items sold");
		typeSelector.setValue("Revenue generated");
		ComboBox<String> cashierSelector = new ComboBox<>();
		cashierSelector.setPrefWidth(190);
		cashierSelector.getItems().add("All");
		cashierSelector.setValue("All");
		for(Cashier c : UserController.getCashiers())
			cashierSelector.getItems().add(c.toString());
		selections.setAlignment(Pos.CENTER);
		fromDate = new DatePicker(LocalDate.now());
		
		toDate = new DatePicker(LocalDate.now());
		
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
		
	
	 selections.add(typeSelector, 1, 0);
	 selections.add(new Label("Cashier/s:"), 0, 1);
	 selections.add(cashierSelector, 1, 1);
	 selections.add(new Label("From:"), 0, 2);
	 selections.add(fromDate, 1, 2);
	 selections.add(new Label("To:"), 0, 3);
	 selections.add(toDate, 1, 3);
	 setMargin(selections, new Insets(0, 30, 0, 0));
	 container.getChildren().addAll(selections, totalLabel);
	 setLeft(container);
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
		totalLabel.setText("");
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
		totalLabel.setText("");
	}
	
	private void setTotalBillsData(LocalDate from, LocalDate to) {
		
		for(Cashier c : UserController.getCashiers()) {
			setTotalBillsDate(from, to, c);
		}
		totalLabel.setText("");
	}
	
	private void setTotalBillsDate(LocalDate from, LocalDate to, Cashier c) {
		Series<String, Integer> seris = new Series<>();
		seris.getData().add(new Data<>(c.getName(), c.getNrBillsInPeriod(from, to)));
		chart.getData().add(seris);
		totalLabel.setText("");
	}
	
	private void setTotalMoneyMade(LocalDate from, LocalDate to) {
		double total = 0;
		for(Cashier c : UserController.getCashiers()) {
			setTotalMoneyMade(from, to, c);
			total += c.getTotal(from, to);
		}
		totalLabel.setText("Total money made: " + total);
	}
	
	private void setTotalMoneyMade(LocalDate from, LocalDate to, Cashier c) {
		Series<String, Double> seris = new Series<>();
		seris.getData().add(new Data<>(c.getName(), c.getTotal(from, to)));
		moneyChart.getData().add(seris);
		totalLabel.setText("");
	}

	private static ArrayList<BillItem> removeDuplicates(ArrayList<BillItem> items){
		Collections.sort(items);
		ArrayList<BillItem> updatedItems = new ArrayList<>();
	
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
