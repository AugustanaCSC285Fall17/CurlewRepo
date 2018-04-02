package edu.augustana.csc285.gamebuilder;

import edu.augustana.csc285.game.datamodel.Item;

public class ItemEditor {

	private Item currentItem;
	
	/**
	 * empty constructor, sets the current item equal to null
	 */
	public ItemEditor(){
		currentItem = null;
	}
	/**
	 * constructor
	 * @param item sets the current item equal to the passed in item object
	 */
	public void setCurrentItem(Item item){
		currentItem = item;
	}
	/**
	 * 
	 * @return the current item
 	 */
	public Item getCurrentItem(){
		return currentItem;
	}
	/**
	 * checks to see if an item was selected
	 * @return returns true if an item wat selected and false if not (currentItem == null)
	 */
	public Boolean isItemSelected(){
		if(currentItem == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * sets the visibility equal to a new passed in visibility 
	 * @param visibility boolean 
	 */
	public void setVisibility(Boolean visibility){
		currentItem.setIsVisible(visibility);
	}
	/**
	 * sets the image path of the item
	 * @param path the string of the path 
	 */
	public void setImagePath(String path){
		currentItem.setImageAddress(path);
	}
	/**
	 * sets whether the current item is sellable by calling a setCanSell in item
	 * @param selected a boolean if the item was selected
	 */
	public void setSellable(boolean selected) {
		currentItem.setCanSell(selected);
	}
	/**
	 * sets whether the current item is byable by calling a setCanBuy in item
	 * @param selected a boolean if the item was selected
	 */
	public void setBuyable(boolean selected) {
		currentItem.setCanBuy(selected);
	}
	/**
	 * sets the current sell price of an item
	 * @param price the new price of the item
	 */
	public void setSellPrice(int price){
		currentItem.setSellPrice(price);
	}
	/**
	 * sets the current buy price of an item
	 * @param price the new price of the item
	 */
	public void setBuyPrice(int price){
		currentItem.setBuyPrice(price);
	}
	/**
	 * checks if the user can buy an item
	 * @return true if they can buy an item and false if they cannot
	 */
	public Boolean canBuy(){
		return currentItem.canBuy();
	}
	/**
	 * checks if the user can sell an item
	 * @return true if they can sell an item and false if they cannot
	 */
	public Boolean canSell(){
		return currentItem.canSell();
	}
}
