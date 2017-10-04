package edu.augustana.csc285.game.datamodel;

public class InventoryCondition implements Condition {
	
	private String itemName;
	private RelationOperator op;
	private int qtyToCompare;
	
	public InventoryCondition(){
	}
	
	public InventoryCondition(String itemName, RelationOperator op, int qtyToCompare){
		this.itemName = itemName;
		this.qtyToCompare = qtyToCompare;
		this.op = op;
	}

	@Override
	public boolean evaluate(Player p) {
		return op.apply(p.getItemQuantity(itemName), qtyToCompare);
	}
	
	
	

}