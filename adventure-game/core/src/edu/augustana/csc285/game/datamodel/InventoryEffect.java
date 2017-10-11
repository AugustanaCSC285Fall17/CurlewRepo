package edu.augustana.csc285.game.datamodel;

public class InventoryEffect implements Effect {

	int effectSize;
	String itemName;
	
	public InventoryEffect() {
		effectSize = 1;
		itemName = "";
	}
	
	public InventoryEffect(String itemName, int effectSize) {
		this.effectSize = effectSize;
		this.itemName = itemName;
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	
	public void applyEffect(Player p) {
		int modifiedQuan = p.getItemQuantity(itemName) + effectSize;
		if (modifiedQuan < 0)
			modifiedQuan = 0;
		
		p.getInventory().put(itemName, modifiedQuan);
	}

	public void setEffectSize(int effectSize) {
		this.effectSize = effectSize;
	}

}
