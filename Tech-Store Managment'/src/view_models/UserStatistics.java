 package view_models;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

//Used in administrator
public class UserStatistics extends BorderPane {
	
	private Label title = new Label("Cashier Statistics");
	private MenuBar menu;
	
	public UserStatistics() {
		title.setId("title");
		setMargin(title, new Insets(20));
		setTop(title);
		setAlignment(title, Pos.CENTER);
		initMenu();
	}
	
	private void initMenu() {
		menu = new MenuBar();
		setCenter(new CashierStatistics());
		Label manager = new Label("Managers");
		manager.setOnMouseClicked(e->{
			if(getCenter() instanceof CashierStatistics) {
				setCenter(new ManagerStatistic());
				title.setText("Manager Statistics");
			}
		});
		
		Label cashier = new Label("Cashier");
		cashier.setOnMouseClicked(e->{
			if(getCenter() instanceof ManagerStatistic) {
				setCenter(new CashierStatistics());
				title.setText("Cashier Statistics");
			}
		});
		
		menu.getMenus().addAll(new Menu("", cashier), new Menu("", manager));
		setBottom(menu);
	}
	
}
