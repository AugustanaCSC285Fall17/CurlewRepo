package edu.augustana.csc285.game.datamodel;

public interface Effect {

	// abstract method for apply the effect should return an int

	public abstract void applyEffect(Player p);

	// abstract method that set any effect on player takes an int
	public abstract void setEffectSize(int size);
	//abstract method that sets the effect name
	public abstract void setEffectName(String name);
	//abstract get method for effectSize field
	public abstract int getEffectSize();
	//abstract method that returns a string with the basic info of the effect
	public abstract String printEffectInfo();
	// returns a toString method
	public abstract String toString();
}
