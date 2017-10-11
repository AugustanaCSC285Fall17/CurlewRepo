package edu.augustana.csc285.game.datamodel;

public interface Effect {

	// abstract method for apply the effect should return an int

	public abstract void applyEffect(Player p);

	// abstract method that set any effect on player takes an int
	public abstract void setEffectSize(int size);
	
	// returns a toString method
	public abstract String toString();

}
