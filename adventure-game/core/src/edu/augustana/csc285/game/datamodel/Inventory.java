package edu.augustana.csc285.game.datamodel;

public class Inventory {

	private String itemName;
	private String imageAddress;
	private int itemQty;
	
	public Inventory() {
	}
	
	public Inventory(String itemName, int itemQty) {
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getImageAddress() {
		return imageAddress;
	}

	public void setImageAddress(String imageAddress) {
		this.imageAddress = imageAddress;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		if(itemQty < 0)
			this.itemQty = 0;
		else
			this.itemQty = itemQty;
		
	}
}
