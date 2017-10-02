package edu.augustana.csc285.game.datamodel;

public interface Feasible {
	public String feasibilityTypes ="";
	public boolean isFeasible = false;
	public boolean haveItem = false;
	
	//abstract method for get the requirement of the feasibility
		public abstract void getReq (int requirement);
		
	// Check if the option is feasible
		public abstract boolean isFeasible(String feasiblityType);
		
	// get the FeasibilityType information and set it
		public String getFeasibilityType();
		
	//
		public boolean getIsFeasible();
		
		public abstract void setFeasibilityType(String feraibilityType);
		
		public abstract void setFeasiableStat(boolean isFeasible);
		
		public abstract boolean checkInventory();
		
		
		
		

}
