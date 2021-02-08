package view_models;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import models.Manager;

public class ManagerStatistics extends BorderPane {

	private CashierStatistics cashierStats;
	private PurchasesView purchasesStats;
	private MenuBar menuBar;
	private Manager manager;
	
	public ManagerStatistics(Manager manager) {
		this.manager = manager;
		initMenuBar();
	}
	
	private void initMenuBar() {
		Label sales = new Label("Sales",
				ImageGetter.getImage("Resources/buttons/sales_white.png", 26, 26));
		sales.setOnMouseClicked(e->{
			cashierStats = new CashierStatistics();
			setCenter(cashierStats);
		});
	
		Label purchases = new Label("My Purchases",
				ImageGetter.getImage("Resources/buttons/expense_white.png", 26, 26));

		purchases.setOnMouseClicked(e->{
			purchasesStats = new PurchasesView(manager);
			setCenter(purchasesStats);
			setMargin(purchasesStats, new Insets(50, 50, 20, 50));
		});
		
		menuBar = new MenuBar(new Menu("", sales),
				new Menu("",  purchases));
		menuBar.setPrefWidth(850);
		cashierStats = new CashierStatistics();
		setCenter(cashierStats);
		setBottom(menuBar);
	}

}
