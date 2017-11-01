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
	public Item getCurrentItem(Item item){
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
}
