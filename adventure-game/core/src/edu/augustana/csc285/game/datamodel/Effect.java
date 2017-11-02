package edu.augustana.csc285.game.datamodel;

public interface Effect {

	/**
	 * abstract method for apply the effect should return an int
	 * @param p a player object
	 */
	public abstract void applyEffect(Player p);
	/**
	 * abstract method that set any effect on player takes an int
	 * @param the size that you want the effect to be
	 */
	public abstract void setEffectSize(int size);
	/**
	 * abstract method that sets the effect name
	 * @param name the new name of the effect
	 */
	public abstract void setEffectName(String name);
	/**
	 * abstract get method for effectSize field
	 * @return the size of the effect in the form of an int
	 */
	public abstract int getEffectSize();
	/**
	 * abstract method that returns a string with the basic info of the effect
	 * @return the string containing the effect info
	 */
	public abstract String printEffectInfo();
	/**
	 * toString method
	 * @return a string that is the effect info
	 */
	public abstract String toString();
}
