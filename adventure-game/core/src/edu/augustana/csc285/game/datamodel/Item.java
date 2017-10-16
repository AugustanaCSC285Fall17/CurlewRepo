package edu.augustana.csc285.game.datamodel;

public class Item {

	private String itemName;
	private String imageAddress;
	private int itemQty;
	private Boolean visible;

	public Item() {
	}

	public Item(String itemName) {
		this.itemName = itemName;
		this.itemQty = 0;
		visible=true;

	}

	//used by game builder, isVisible should always be false
	public Item(String itemName, Boolean isVisible){
		this.itemName = itemName;
		this.visible = isVisible;
		imageAddress = null;
	}
	
	//used by game builder, isVisible should alwas be true
	public Item(String itemName, Boolean isVisible, String imageAddress){
		this.itemName = itemName;
		this.visible = isVisible;
		this.imageAddress = imageAddress;
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
		return itemName;
	}

	public Boolean getIsVisible() {
		return visible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.visible = isVisible;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Item){
			Item i = (Item) o;
			if(i.getItemName().equals(((Item) o).getItemName())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
