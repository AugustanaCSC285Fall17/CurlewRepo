package edu.augustana.csc285.game.datamodel;

public interface Effect {
	// fields
	int effectSize = 0;
	String effectType = "";

	// abstract method for apply the effect should return an int

	public abstract int applyEffect();

	// abstract method that set any effect on player takes an int
	public abstract void setEfect(int size);
	
	//

}
