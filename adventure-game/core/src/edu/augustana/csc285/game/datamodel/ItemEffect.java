package edu.augustana.csc285.game.datamodel;

public class ItemEffect implements Effect {


	private int effectSize;
	
	private Item item;

	
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
	
	public ItemEffect(Item item, int effectSize) {
		System.out.print("Effect "+item.getItemName());
		this.effectSize = effectSize;
		this.item = new Item(item.getItemName());
		System.out.println("Effect2 "+this.item.getItemName());
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
	public String toString() {
		return "Item change: (ItemName : " + item.getItemName() + ", effectSize: " + effectSize;
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
