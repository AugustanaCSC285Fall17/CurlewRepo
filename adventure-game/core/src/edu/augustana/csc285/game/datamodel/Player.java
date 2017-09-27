package edu.augustana.csc285.game.datamodel;
import java.util.*;

public class Player {

	private String name;
	private boolean male;
	private TreeMap<String, Integer> stats;
	private TreeMap<String, Integer> inventory;

	// constructor
	public Player(String name) {
		this.name = name;
		male = true;
		stats = new TreeMap<String, Integer>();
		inventory = new TreeMap<String, Integer>();
	}


	// get method for name
	public String getName() {
		return name;
	}

	// set method for name
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	// returns the player stat value stored at a given key s
	public int getStat(String s) {
		return stats.get(s);
	}

	// returns the player inventory value stored at a given key s
	public int getInventory(String s) {
		return inventory.get(s);
	}

	// changes the player stat value stored at a given key
	public void setStat(String s, int value) {
		stats.put(s, value);
	}

	// changes the player inventory value stored at a given key
	public void setInventory(String s, int value) {
		inventory.put(s, value);
	}
	
	// adds the player stat value stored at a given key
	public void addStat(String s, int value) {
		stats.put(s, value + stats.get(s));
	}

	// adds the player inventory value stored at a given key
	public void addInventory(String s, int value) {
		inventory.put(s, value + inventory.get(s));
	}
	
	// subtracts the player stat value stored at a given key
	public void subtractStat(String s, int value) {
		stats.put(s, stats.get(s) - value);
	}

	// subtracts the player inventory value stored at a given key
	public void subtractInventory(String s, int value) {
		inventory.put(s, inventory.get(s) - value);
	}

}
