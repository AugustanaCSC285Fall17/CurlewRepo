package edu.augustana.csc285.game.datamodel;

import java.util.Observable;

public class Item extends Observable{

	private String itemName;
	private String imageAddress;
	private int itemQty;
	private boolean visible;
	private int sellPrice;
	private int buyPrice;
	private boolean canSell;
	private boolean canBuy;

	public Item() {
	}

	public Item(String itemName) {
		this.itemName = itemName;
		this.itemQty = 0;
		visible = true;
		canSell = false;
		canBuy = false;

	}

	//used by game builder, isVisible should always be false
	public Item(String itemName, boolean isVisible){
		this.itemName = itemName;
		this.visible = isVisible;
		imageAddress = null;
		canSell = false;
		canBuy = false;
	}
	
	//used by shopScreen
	public Item(String itemName, boolean isVisible, int itemQty, String imageAddress, int buyPrice, int sellPrice){
		this.itemName = itemName;
		this.visible = isVisible;
		this.itemQty = itemQty;
		this.imageAddress = imageAddress;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		canSell = false;
		canBuy = false;
	}
	
	public Item(String itemName, int itemQty) {
		this.itemName = itemName;
		this.itemQty = itemQty;
		canSell = false;
		canBuy = false;
	}
	
	public Item(String itemName, boolean isVisible, String imageAddress){
		this.itemName = itemName;
		this.visible = isVisible;
		this.imageAddress = imageAddress;
		canSell = false;
		canBuy = false;
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
		return itemName;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setIsVisible(boolean isVisible) {
		this.visible = isVisible;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
	
	public int getBuyPrice() {
		return buyPrice;
	}
	
	public boolean canSell() {
		return canSell;
	}
	
	public boolean canBuy() {
		return canBuy;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Item){
			Item i = (Item) o;
			if(i.getItemName().equals(itemName)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
