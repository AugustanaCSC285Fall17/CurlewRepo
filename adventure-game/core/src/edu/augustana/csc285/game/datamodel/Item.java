package edu.augustana.csc285.game.datamodel;

import java.util.Observable;

public class Item extends Observable{

	private String itemName;
	private String imageName;
	private int itemQty;
	private boolean visible;
	private int sellPrice;
	private int buyPrice;
	private boolean canSell;
	private boolean canBuy;
	
	/**
	 * Initialize a newly created Item object that will behaves as a game item with 
	 * a name, a quantity, and prices. Also, this object has visible, canSell, and
	 * canBuy fields that determine the corresponding value.
	 */
	public Item() {// for JSON
	}

	/**
	 * Initialize a newly created Item object that will behaves as a game item with 
	 * a name, a quantity, and prices. Also, this object has visible, canSell, and
	 * canBuy fields that determine the corresponding value.
	 * 
	 * @param itemName	- name of the item
	 */
	public Item(String itemName) {
		this.itemName = itemName;
		this.itemQty = 0;
		visible = true;
		canSell = false;
		canBuy = false;
		sellPrice = 0;
		buyPrice = 0;
	}
	
	/**
	 * Initialize a newly created Item object that will behaves as a game item with 
	 * a name, a quantity, and prices. Also, this object has visible, canSell, and
	 * canBuy fields that determine the corresponding value. This constructor is mainly
	 * used in the GameBuilder.
	 * 
	 * @param itemName	- name of the item
	 * @param isVisible	- true if the item is visible in the player's inventory.
	 */
	public Item(String itemName, boolean isVisible){
		this.itemName = itemName;
		this.visible = isVisible;
		imageName = null;
		canSell = false;
		canBuy = false;
	}
	
	/**
	 * Initialize a newly created Item object that will behaves as a game item with 
	 * a name, a quantity, and prices. Also, this object has visible, canSell, and
	 * canBuy fields that determine the corresponding value. This constructor is mainly
	 * used to test the ShopScreen and for the GameBuilder
	 * 
	 * @param itemName	- name of the item
	 * @param isVisible	- true if the item is visible in the player's inventory.
	 * @param itemQty	- quantity of the item
	 * @param imageName	- name of the item image, located in assets/art/icons/
	 * @param buyPrice	- cost to buy the item
	 * @param sellPrice	- cost to sell the item
	 * @param canSell	- true if the player can sell the item from their inventory
	 * @param canBuy	- true if the player can buy the item from the shop
	 */
	public Item(String itemName, boolean isVisible, int itemQty, String imageName, int buyPrice, int sellPrice,
			boolean canSell, boolean canBuy){
		this.itemName = itemName;
		this.visible = isVisible;
		this.itemQty = itemQty;
		this.imageName = imageName;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.canSell = canSell;
		this.canBuy = canBuy;
	}
	
	/**
	 * Initialize a newly created Item object that will behaves as a game item with 
	 * a name, a quantity, and prices. Also, this object has visible, canSell, and
	 * canBuy fields that determine the corresponding value. This constructor is mainly
	 * used in the ShopScreen
	 * 
	 * @param itemName	- name of the item
	 * @param itemQty	- quantity of the item
	 */
	public Item(String itemName, int itemQty) {
		this.itemName = itemName;
		this.itemQty = itemQty;
		canSell = false;
		canBuy = false;
	}
	
	/**
	 * Initialize a newly created Item object that will behaves as a game item with 
	 * a name, a quantity, and prices. Also, this object has visible, canSell, and
	 * canBuy fields that determine the corresponding value.
	 * 
	 * @param itemName	- name of the item
	 * @param isVisible	- true if the item is visible in the player's inventory.
	 * @param imageName	- name of the item image, located in assets/art/icons/
	 */
	public Item(String itemName, boolean isVisible, String imageName){
		this.itemName = itemName;
		this.visible = isVisible;
		this.imageName = imageName;
		canSell = false;
		canBuy = false;
	}

	/**
	 * @return name of the item
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Set name of the item
	 * @param itemName - new name of the item
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return name of the item image, located in assets/art/icons/
	 */
	public String getImageAddress() {
		return imageName;
	}

	/**
	 * Set name of the item image, located in assets/art/icons/
	 * @param imageName name of the item image, located in assets/art/icons/
	 */
	public void setImageAddress(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return 
	 */
	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		if (itemQty < 0)
			this.itemQty = 0;
		else
			this.itemQty = itemQty;

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

	public void setCanSell(boolean canSell) {
		this.canSell = canSell;
	}
	
	public void setCanBuy(boolean canBuy) {
		this.canBuy = canBuy;
	}
	
	public boolean canSell() {
		return canSell;
	}
	
	public boolean canBuy() {
		return canBuy;
	}
	
	public void setSellPrice(int price){
		sellPrice = price;
	}
	
	public void setBuyPrice(int price){
		buyPrice = price;
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
	
	public String toString(){
		return itemName;
	}
	
	public String printInfo() {
		String s = "";
		s+= "Item Name: " + itemName + "\nItem Quantity: " + itemQty +  "\nItem Image Address: " + imageName;
		s+= "\nVisibility: "+ visible;
		s+= "\nCan Buy: "+ canBuy;
		s+= "\nBuyPrice "+ buyPrice;
		s+= "\nCan Sell: "+ canSell;
		s+= "\nSell price: "+ sellPrice;
		s+="\n\n";
		
		return s;
	}
}
