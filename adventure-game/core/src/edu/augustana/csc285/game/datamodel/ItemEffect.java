package edu.augustana.csc285.game.datamodel;

public class ItemEffect implements Effect {

	private Item item;
	private int effectSize;
	

	public ItemEffect(Item item, int effectSize) {
		this.effectSize = effectSize;
		this.item = new Item(item.getItemName());
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getEffectSize() {
		return effectSize;
	}

	public ItemEffect() {
		effectSize = 1;
	}
	
	
	public void setEffectName(String itemName){
		item.setItemName(itemName);
	}
	
	@Override
	public void applyEffect(Player p) {
		p.addInventory(item.getItemName(), effectSize);
	}

	@Override
	public void setEffectSize(int effectSize) {
		this.effectSize = effectSize;
	}
	
	@Override
	public String printEffectInfo(){
		return "Item Change: Item Name : " + item.getItemName() + ", Effect Size: " + effectSize;
	}

	@Override
	public String toString() {
		return item.getItemName();
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ItemEffect){
			ItemEffect e = (ItemEffect) o;
			if(e.getItem().equals(this.getItem())){
				return true;
			}
			return false;
		}else{
			return false;
		}
	}
}
