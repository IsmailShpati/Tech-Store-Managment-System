package view_models;

import controllers.StockController;
import interfaces.ViewException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Manager;
import models.PurchaseBill;
import models.StockItem;
import view_models.PurchaseStock.StockView;

public class PurchasePopUp extends Application {
	private GridPane purchaseSide = new GridPane();
	private Label quantityLabel = new Label("Quantity");
	private Button purchaseBtn = new Button("Purchase");
	private QuantityPlusMinus quantity = new QuantityPlusMinus(1);
	private StockItem item;
	private Stage stage;
	private PurchaseStock.StockView stockView;
	
	public PurchasePopUp(StockItem item, StockView stockView) {
		this.item = item;
		this.stockView = stockView;
	}

	private void initPurchaseBtn(Button btn) throws ViewException {
		btn.setOnAction(e -> {
			StockController.purchaseStock(item, quantity.getQuantity());
			Manager m = stockView.getManager();
			stockView.refresh();
			m.addBill(new PurchaseBill(item.getItemName(), m.getName()+ " " + m.getSurname(),
					quantity.getQuantity(), item.getpurchasingPrice()));
			stage.close();
			new Alert(AlertType.CONFIRMATION).show();
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws ViewException {
		stage = primaryStage;
		purchaseSide.setHgap(20);
		purchaseSide.setPadding(new Insets(50));
		purchaseSide.setAlignment(Pos.CENTER);
		purchaseSide.setVgap(10);
		purchaseSide.add(quantityLabel, 1, 1);
		purchaseSide.add(quantity, 2, 1);
		initPurchaseBtn(purchaseBtn);
		purchaseSide.add(purchaseBtn, 2, 2);
		
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.setScene(new Scene(purchaseSide));
		primaryStage.setTitle("Purchase Item");
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
		
	}

}
