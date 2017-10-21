package edu.augustana.csc285.game.datamodel;

public interface Condition {
	
	public boolean evaluate(Player p);

	public String printEffectInfo();
}
