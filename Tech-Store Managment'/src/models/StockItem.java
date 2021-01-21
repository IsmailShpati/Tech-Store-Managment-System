package models;

public class StockItem extends Item {

	private String category;
	private int stockQuantity;
	private double purchasingPrice;
	
	public StockItem(String itemName, String itemModel, double sellingPrice,
			String category, int stockQuantity, double purchasingPrice) {
		super(itemName, itemModel, sellingPrice);
		this.category = category;
		this.stockQuantity = stockQuantity;
		this.purchasingPrice = purchasingPrice;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public double getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(double purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}
	
	public void addStock(int stockPurchased) {
		stockQuantity += stockPurchased;
	}
	
	public void sellStock(int stockSold) {
		stockQuantity -= stockSold;
		System.out.println("Stock for: " + getItemName() + " " + stockQuantity);
	}
	
	public boolean equals(StockItem i) {
		if(i.getItemName().equals(getItemName()))
				return false;
		return true;
	}
}
