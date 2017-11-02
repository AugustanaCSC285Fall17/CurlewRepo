package edu.augustana.csc285.game.datamodel;

public interface Feasible {
	public String feasibilityTypes = "";
	public boolean isFeasible = false;
	public boolean haveItem = false;

	// abstract method for get the requirement of the feasibility
	public abstract void getReq(int requirement);

	// Check if the option is feasible
	public abstract boolean isFeasible(String feasiblityType);

	// get the FeasibilityType information and set it
	public String getFeasibilityType();

	/**
	 * return if the option is feasible
	 * 
	 * @return true or false
	 */
	public boolean getIsFeasible();

	/**
	 * set the feasibility type for an option
	 * 
	 * @param feasibilityType
	 *            String that contain the feasibility type
	 */
	public abstract void setFeasibilityType(String feraibilityType);

	/**
	 * set the feasibility status for the option
	 * 
	 * @param isFeasible
	 *            true or false
	 */
	public abstract void setFeasiableStat(boolean isFeasible);

	/**
	 * check the inventory
	 * 
	 * @return a boolean that state if the inventory has a feasibility option
	 */
	public abstract boolean checkInventory();

}
