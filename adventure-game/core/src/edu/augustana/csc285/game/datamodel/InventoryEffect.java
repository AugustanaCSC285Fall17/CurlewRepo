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
		int modifiedQuan = p.getItemQuantity(itemName) + effectSize;
		if (modifiedQuan < 0)
			modifiedQuan = 0;
		
		p.getInventory().put(itemName, modifiedQuan);
	}

	@Override
	public void setEfect(int size) {
		// TODO Auto-generated method stub
		
	}

}
