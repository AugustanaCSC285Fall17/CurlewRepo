package edu.augustana.csc285.game.datamodel;

public class InventoryCondiction {
	
	private String itemName;
	private int itemNumber;
	private RelationOperator op;
	
	private InventoryCondiction(){
		
	}
	
	private InventoryCondiction(String itemName, int itemNumber, RelationOperator op){
		this.itemName = itemName;
		this.itemNumber = itemNumber;
		this.op = op;
	}
	
	

}
