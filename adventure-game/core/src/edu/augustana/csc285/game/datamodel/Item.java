package edu.augustana.csc285.game.datamodel;

public class Item {

	private String itemName;
	private String imageAddress;
	private int itemQty;

	public Item() {
	}

	public Item(String itemName) {
		this.itemName = itemName;
		this.itemQty = 0;

	}

	public Item(String itemName, int itemQty) {
		this.itemName = itemName;
		this.itemQty = itemQty;
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
		if (itemQty < 0)
			this.itemQty = 0;
		else
			this.itemQty = itemQty;

	}

	public String toString() {
		return "Item Name: " + itemName + "\nItem Quantity: " + itemQty + "\nItem Image Address: " + imageAddress + "\n";
	}
}
