package edu.augustana.csc285.gamebuilder;

import edu.augustana.csc285.game.datamodel.Item;

public class ItemEditor {

	private Item currentItem;
	
	public ItemEditor(){
		currentItem = null;
	}
	public void setCurrentItem(Item item){
		currentItem = item;
	}
	public Item getCurrentItem(){
		return currentItem;
	}
	public Boolean isItemSelected(){
		if(currentItem == null){
			return false;
		}else{
			return true;
		}
	}
	public void setVisibility(Boolean visibility){
		currentItem.setIsVisible(visibility);
	}
	
	public void setImagePath(String path){
		currentItem.setImageAddress(path);
	}
	public void setSellable(boolean selected) {
		currentItem.setCanSell(selected);
	}
	public void setBuyable(boolean selected) {
		currentItem.setCanBuy(selected);
	}
	public void setSellPrice(int price){
		currentItem.setSellPrice(price);
	}
	public void setBuyPrice(int price){
		currentItem.setBuyPrice(price);
	}
	public Boolean canBuy(){
		return currentItem.canBuy();
	}
	public Boolean canSell(){
		return currentItem.canSell();
	}
}
