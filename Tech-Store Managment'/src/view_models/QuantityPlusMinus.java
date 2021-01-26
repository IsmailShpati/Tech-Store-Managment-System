package view_models;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


//Custom node ex: + 1 -, incrementing and decrementing [Part of the Right Side of CashierView]
public class QuantityPlusMinus extends HBox{

	private int minimumProducts, quantity;
	private Text quantityText ; 
	public QuantityPlusMinus(int minProducts) {
		this.minimumProducts = minProducts;
		quantity = minimumProducts;
		quantityText = new Text(quantity+"");
		setSpacing(10);
		setAlignment(Pos.CENTER);
		initButtons();
		
	}
	
	private void initButtons() {
		Button plusBtn = new Button("+");
		plusBtn.setOnAction(e -> {
			quantity++;
			System.out.println(quantity);
			quantityText.setText(quantity+"");
		});
		
		Button minusBtn = new Button("-");
	    minusBtn.setOnAction(e -> {
	    	if(quantity > minimumProducts)
	    		quantity--;
	    	quantityText.setText(quantity+"");
	    });	
	    
	    getChildren().addAll(plusBtn, quantityText, minusBtn);
	
	}
	
	public void reset() {
		quantityText.setText(minimumProducts+"");
		quantity = minimumProducts;
	}
	

	public int getQuantity() {
		return quantity;
	}
	
	
}
