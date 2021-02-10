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
import models.PurchaseBill;
import models.Manager;

public class ManagerStatistic extends BorderPane{
	
	private GridPane selections = new GridPane();
	private BarChart<String, Integer> chart;
	private BarChart<String, Double> moneyChart;
	private DatePicker fromDate, toDate;
	private Label totalLabel;
	
	public ManagerStatistic() {
		setPadding(new Insets(30));
		totalLabel = new Label();
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
		VBox container = new VBox(15);
		container.setAlignment(Pos.CENTER);
		selections.setVgap(15);
		selections.setHgap(10);
		ComboBox<String> typeSelector = new ComboBox<>();
		typeSelector.getItems().addAll("Money spent", "Items purchased");
		typeSelector.setValue("Money spent");
		ComboBox<String> managerSelector = new ComboBox<>();
		managerSelector.getItems().add("All");
		managerSelector.setPrefWidth(170);
		managerSelector.setValue("All");
		for(Manager c : UserController.getManagers())
			managerSelector.getItems().add(c.toString());
		selections.setAlignment(Pos.CENTER);
		
		
		fromDate = new DatePicker(LocalDate.now());
		toDate = new DatePicker(LocalDate.now());
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
		 selections.add(typeSelector, 1, 0);
		 selections.add(new Label("Manager/s:"), 0, 1);
		 selections.add(managerSelector, 1, 1);
		 selections.add(new Label("From:"), 0, 2);
		 selections.add(fromDate, 1, 2);
		 selections.add(new Label("To:"), 0, 3);
		 selections.add(toDate, 1, 3);
		 setMargin(selections, new Insets(0, 30, 0, 0));
		 container.getChildren().addAll(selections, totalLabel);
		 setLeft(container);
	
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
		double total = 0;
		for(Manager m : UserController.getManagers()) {
			setTotalMoneySpent(from, to, m);
			total += m.getTotal(from, to);
		}
		totalLabel.setText("Total money spent: " + total);
		
	}
	
	private void setTotalMoneySpent(LocalDate from, LocalDate to, Manager m) {
		Series<String, Double> seris = new Series<>();
		seris.getData().add(new Data<>(m.getName(), m.getTotal(from, to)));
		moneyChart.getData().add(seris);
		totalLabel.setText("");
	}
	
	private void setTotalData(LocalDate from, LocalDate to) {
	
		for(Manager m : UserController.getManagers())
			setManagerData(from, to, m);
		totalLabel.setText("");
	}

	private void setManagerData(LocalDate from, LocalDate to, Manager m) {
		
		ArrayList<PurchaseBill> purchaseBills = new ArrayList<>(m.getBillsInPeriod(from, to)); 
		for(PurchaseBill i : removeDuplicates(purchaseBills)) {
			Series<String, Integer> series = new Series<>();
			series.getData().add(new Data<>(i.getItemName(), i.getQuantity()));
			chart.getData().add(series);
		}
		totalLabel.setText("");
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
