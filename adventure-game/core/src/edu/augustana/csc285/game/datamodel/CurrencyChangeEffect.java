package edu.augustana.csc285.game.datamodel;

public class CurrencyChangeEffect implements Effect {
	
	private String from;
	private String to;
	
	public CurrencyChangeEffect() {
	}
	
	public CurrencyChangeEffect(String from, String to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public void applyEffect(Player p) {
		float currentMoney = p.getItemQuantity(from);
		if (from.equals("Kronor")) {
			p.incrementAvailableItem(from, -p.getItemQuantity(from));
			p.incrementAvailableItem(to, (int) (currentMoney / 4));
		} else if (from.equals("Dollar")){
			p.incrementAvailableItem(from, -p.getItemQuantity(from));
			p.incrementAvailableItem(to, (int) (currentMoney * 4));
		}
		
	}

	@Override
	public void setEffectSize(int size) {
		
	}

	@Override
	public void setEffectName(String name) {
		
	}

	@Override
	public int getEffectSize() {
		return 0;
	}

	@Override
	public String printEffectInfo() {
		return "Currency Change: " + from + " to " + to;
	}
}
