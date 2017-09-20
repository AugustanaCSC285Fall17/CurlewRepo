package edu.augustana.csc285.game.datamodel;

public interface Feasible {
	int feasibility = 0;
	int feasibilityType = 0;
	
	//abstract method for get the requirement of the feasibility
		public abstract void getReq (int requirement);
}
