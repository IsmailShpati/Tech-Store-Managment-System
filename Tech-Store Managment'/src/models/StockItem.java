package models;

public class StockItem extends Item {

	private static final long serialVersionUID = 7693147414316147698L;

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

	public String getStockQuantity() {
		return stockQuantity+"";
	}
	public int getQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getPurchasingPrice() {
		return purchasingPrice+"";
	}
	public double getpurchasingPrice() {
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
