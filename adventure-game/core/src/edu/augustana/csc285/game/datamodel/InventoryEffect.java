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
	
	public void setEffectName(String itemName){
		this.itemName = itemName;
	}
	
	@Override
	public void applyEffect(Player p) {
		p.addInventory(itemName, effectSize);
	}

	@Override
	public void setEffectSize(int effectSize) {
		this.effectSize = effectSize;
	}

	@Override
	public String toString() {
		return "Inventory change: (ItemName : " + itemName + ", effectSize: " + effectSize;
	}
}
