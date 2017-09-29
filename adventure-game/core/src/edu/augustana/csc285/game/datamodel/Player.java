package edu.augustana.csc285.game.datamodel;
import java.util.*;

public class Player {

	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final int GAY = 2;
	public static final int LESBIAN = 3;
	public static final int TRANSGENDER = 4;
	public static final int BISEXUAL = 5;
	public static final int QUESTIONING = 6;
	public static final int OTHER = 7;
	
	private String name;
	private int gender;
	private TreeMap<String, Integer> stats;
	private TreeMap<String, Integer> inventory;

	// constructor
	public Player(String name) {
		this.name = name;
		gender = MALE;
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
	
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	// returns the player stat value stored at a given key s
	public int getStat(String s) {
		return stats.get(s);
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
