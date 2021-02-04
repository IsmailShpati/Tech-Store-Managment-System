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
import models.PurchaseBill;
import models.Manager;

public class ManagerStatistic extends BorderPane{
	
	private VBox selections = new VBox(15);
	private BarChart<String, Integer> chart;
	private BarChart<String, Double> moneyChart;
	private DatePicker fromDate, toDate;
	
	public ManagerStatistic() {
		setPadding(new Insets(30));
		initQuantityChart();
		initMoneyChart();
		setTotalMoneySpent(LocalDate.now(), LocalDate.now());
		initGrid();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initQuantityChart() {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setAnimated(false);
		xAxis.setLabel("Items");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Quantities purchased");
		chart = new BarChart(xAxis, yAxis);
		chart.setLegendVisible(false);
		chart.setAnimated(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initMoneyChart() {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setAnimated(false);
		xAxis.setLabel("Manager/s");
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
		typeSelector.getItems().addAll("Money spent", "Items purchased");
		typeSelector.setValue("Money spent");
		ComboBox<String> managerSelector = new ComboBox<>();
		managerSelector.getItems().add("All");
		managerSelector.setValue("All");
		for(Manager c : UserController.getManagers())
			managerSelector.getItems().add(c.toString());
		selections.setAlignment(Pos.CENTER);
		HBox firstRow = new HBox(10);
		firstRow.getChildren().addAll(new Label("Manager/s: "), managerSelector);
		
		HBox container = new HBox(10);
		
		fromDate = new DatePicker(LocalDate.now());
		container.getChildren().addAll(new Label("From"), fromDate);
		toDate = new DatePicker(LocalDate.now());
		HBox secondCont = new HBox(25);
		secondCont.getChildren().addAll(new Label("To"), toDate);
		
		typeSelector.setOnAction(e->{
			handleChange(managerSelector, typeSelector);
		});
		managerSelector.setOnAction(e->{
			handleChange(managerSelector, typeSelector);
		});
		fromDate.setOnAction(e->{
			handleChange(managerSelector, typeSelector);
		});
		toDate.setOnAction(e->{
			handleChange(managerSelector, typeSelector);
		});
		
		selections.getChildren().addAll(typeSelector, firstRow, container, secondCont);
		 setMargin(selections, new Insets(0, 30, 0, 0));
		 setLeft(selections);
	
	}

	private void handleChange(ComboBox<String> managerSelector, ComboBox<String> typeSelector) {
		Manager m = getManager(managerSelector);
		switch(typeSelector.getSelectionModel().getSelectedIndex()) {
		case 0:
			changeForMoney();
			if(m != null) 
				setTotalMoneySpent(fromDate.getValue(), toDate.getValue(), m);
			else 
				setTotalMoneySpent(fromDate.getValue(), toDate.getValue());
			break;
		case 1:
			changeForTotal();
			if(m!=null) 
				setManagerData(fromDate.getValue(), toDate.getValue(), m);
			else 
				setTotalData(fromDate.getValue(), toDate.getValue());
			break;
		}
		
	}
	
	private void setTotalMoneySpent(LocalDate from, LocalDate to) {
		for(Manager m : UserController.getManagers()) {
			setTotalMoneySpent(from, to, m);
		}
		
	}
	
	private void setTotalMoneySpent(LocalDate from, LocalDate to, Manager m) {
		Series<String, Double> seris = new Series<>();
		seris.getData().add(new Data<>(m.getName(), m.getTotal(from, to)));
		moneyChart.getData().add(seris);
		
	}
	
	private void setTotalData(LocalDate from, LocalDate to) {
	
		for(Manager m : UserController.getManagers())
			setManagerData(from, to, m);
	}

	private void setManagerData(LocalDate from, LocalDate to, Manager m) {
		
		ArrayList<PurchaseBill> purchaseBills = new ArrayList<>(m.getBillsInPeriod(from, to)); 
		for(PurchaseBill i : removeDuplicates(purchaseBills)) {
			Series<String, Integer> series = new Series<>();
			series.getData().add(new Data<>(i.getItemName(), i.getQuantity()));
			chart.getData().add(series);
		}
	}

	

	private void changeForTotal() {
		setRight(chart);
		chart.getData().clear();
		chart.getXAxis().setLabel("Items");
		chart.getYAxis().setLabel("Quantities sold");
	}

	private void changeForMoney() {
		setRight(moneyChart);
		moneyChart.getData().clear();
		moneyChart.getXAxis().setLabel("Cashiers");
		moneyChart.getYAxis().setLabel("Revenue");
	}

	private Manager getManager(ComboBox<String> managerSelector) {
		if(managerSelector.getSelectionModel().getSelectedIndex() == 0)
			return null;
		 
		return UserController.getManagers().get(
					managerSelector.getSelectionModel().getSelectedIndex()-1);
	}
	
	private static ArrayList<PurchaseBill> removeDuplicates(ArrayList<PurchaseBill> items){
		Collections.sort(items);
		ArrayList<PurchaseBill> updatedItems = new ArrayList<>();
	
		if(items.size() > 0) {
			PurchaseBill current = new PurchaseBill(items.get(0));

			updatedItems.add(current);
			for(int ind = 1; ind < items.size(); ind++ ) {
				PurchaseBill i = items.get(ind);
					if(i.equals(current)) 
						current.addQuantityPurchased(i.getQuantity());
					else {
						current = new PurchaseBill(i);
						updatedItems.add(current);
					}
			}
		}
		else 
			new Alert(AlertType.WARNING, "No bills in current period").showAndWait();
		return updatedItems;
	}
}
