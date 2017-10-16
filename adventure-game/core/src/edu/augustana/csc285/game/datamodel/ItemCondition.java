package edu.augustana.csc285.game.datamodel;

public class ItemCondition implements Condition {
	
	private String item;
	private RelationalOperator op;
	private int qtyToCompare;
	
	public ItemCondition(){
	}
	
	public ItemCondition(String itemName, RelationalOperator op, int qtyToCompare){
		this.item = itemName;
		this.qtyToCompare = qtyToCompare;
		this.op = op;
	}

	@Override
	public boolean evaluate(Player p) {
		return op.apply(p.getItemQuantity(item), qtyToCompare);
	}
	
	
	

}
