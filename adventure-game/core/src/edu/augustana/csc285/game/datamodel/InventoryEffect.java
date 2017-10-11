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
	
	@Override
	public void applyEffect(Player p) {
		p.addInventory(itemName, effectSize);
	}

	@Override
	public void setEfect(int size) {
		// TODO Auto-generated method stub
		
	}

}
