package edu.augustana.csc285.game.datamodel;

public class ItemCondition implements Condition {
	
	private Item item;
	private RelationalOperator op;
	private int qtyToCompare;
	
	public ItemCondition(){
	}
	
	public ItemCondition(String itemName, RelationalOperator op, int qtyToCompare){
		this.item = new Item(itemName);
		this.op = op;
		this.qtyToCompare = qtyToCompare;
	}
	
	public ItemCondition(Item item, RelationalOperator op, int qtyToCompare){
		this.item = item;
		this.op = op;
		this.qtyToCompare = qtyToCompare;
	}

	@Override
	public boolean evaluate(Player p) {
		return op.apply(p.getItemQuantity(item.getItemName()), qtyToCompare);
	}
	
	
	

}
