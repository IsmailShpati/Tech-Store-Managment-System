package view_models;

import java.time.LocalDate;
import java.util.ArrayList;

import controllers.BillGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Bill;
import models.Cashier;

public class BillsView extends BorderPane{

	
	private DatePicker from, to;
	private ScrollPane pane;
	private Cashier cashier;
	private Label totalBills = new Label();
	
	public BillsView(Cashier cashier) {
		this.cashier = cashier;
		pane = new ScrollPane();
		initPickers();
		pane.setPrefWidth(410);
		pane.setPadding(new Insets(9));
		setCenter(pane);
		setMargin(pane, new Insets(30, 30, 30, 0));
	}

	private void initPickers() {
		GridPane picker = new GridPane();
		
		if(cashier.getBills().size() > 0) {
			from = new DatePicker();
			from.setValue(cashier.getBills().get(0).getDate().toLocalDate());
			to = new DatePicker();
			to.setValue(LocalDate.now());
		}
		else {
			from = new DatePicker(LocalDate.now());
			to = new DatePicker(LocalDate.now());
		}
		initScrollPane();
		from.setOnAction(e->{
			initScrollPane();
		});
		to.setOnAction(e->{
			initScrollPane();
		});
		picker.setVgap(20); picker.setHgap(15);
		picker.add(from, 2, 1);
		picker.add(new Label("From "), 1, 1);
		picker.add(to,  2, 2);
		picker.add(new Label("To "), 1, 2);
		picker.add(totalBills, 1, 3);
		setLeft(picker);
		setMargin(picker, new Insets(30));
	}
	
	public void refresh() {
		initScrollPane();
	}
	
	private void initScrollPane() {
		ArrayList<Bill> billsInPeriod = 
				cashier.getBillsInPeriod(from.getValue(), to.getValue());
		VBox content = new VBox(20);
		totalBills.setText("Total number of bills: " + billsInPeriod.size());
		for(Bill b : billsInPeriod) {
			content.getChildren().add(new BillInfo(BillGenerator.formatBill(b, cashier)));
		}
		pane.setContent(content);
	}

	
	class BillInfo extends StackPane{
		private Text text;
		private Rectangle shape;
		
		public BillInfo(String text) {
			this.text = new Text(text);
			this.text.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
			shape = new Rectangle(this.text.getBoundsInLocal().getWidth()+10,
					this.text.getBoundsInLocal().getHeight()+10, Color.WHITE);
			getChildren().addAll(shape, this.text);
		}
		
		
	}
}

