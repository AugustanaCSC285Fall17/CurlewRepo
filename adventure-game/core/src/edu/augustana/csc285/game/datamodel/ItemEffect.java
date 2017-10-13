package edu.augustana.csc285.game.datamodel;

public class ItemEffect implements Effect {

	int effectSize;
	String itemName;
	Item item;
	
	public ItemEffect() {
		effectSize = 1;
		itemName = "";
	}
	
	public ItemEffect(Item item, int effectSize) {
		this.effectSize = effectSize;
		this.item = item;
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
		return "Item change: (ItemName : " + itemName + ", effectSize: " + effectSize;
	}
}
