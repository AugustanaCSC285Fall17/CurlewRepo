package edu.augustana.csc285.game.datamodel;

public class InventoryEffect implements Effect {
	
	private String effectName;
	private int effectSize;
	
	public InventoryEffect(){}

	public InventoryEffect(String effectName, int effectSize) {
		this.effectName = effectName;
		this.effectSize = effectSize;
	}

	@Override
	public void applyEffect(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEffectSize(int size) {
		effectSize = size;

	}

	@Override
	public void setEffectName(String name) {
		effectName = name;

	}

}
