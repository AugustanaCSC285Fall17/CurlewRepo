package edu.augustana.csc285.game.datamodel;

import java.util.TreeMap;

public class Player {
	
	private String name;
	private Gender gender;
	private TreeMap<String, Integer> stats;
	private TreeMap<String, Integer> inventory = new TreeMap<String, Integer>();;

	// constructor
	public Player() {
		this.name = "MinhJackSteveMax";
		gender = Gender.MALE;
		stats = new TreeMap<String, Integer>();
	}

	public Player(String name) {
		this.name = name;
		gender = Gender.MALE;
		stats = new TreeMap<String, Integer>();
	}


	// get method for name
	public String getName() {
		return name;
	}

	// set method for name
	public void setName(String name) {
		this.name = name;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	// returns the player stat value stored at a given key s
	public int getStat(String s) {
		return stats.get(s);
	}
	
	public TreeMap<String, Integer> getInventory() {
		return inventory;
		
	}

	// returns the player inventory value stored at a given key s
	public int getItemQuantity(String s) {
		if (inventory.containsKey(s))
			return inventory.get(s);
		else
			return 0;
	}

	// changes the player stat value stored at a given key
	public void setStat(String s, int value) {
		stats.put(s, value);
	}

	// changes the player inventory value stored at a given key
	public void addItem(String s, int value) {
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
