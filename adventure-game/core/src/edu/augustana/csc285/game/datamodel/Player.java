package edu.augustana.csc285.game.datamodel;

import java.util.ArrayList;
import java.util.TreeMap;

public class Player {
	
	private String name;
	private Gender gender;
	private TreeMap<String, Integer> stats;
	private ArrayList<Item> inventory = new ArrayList<Item>();

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
	
	public ArrayList<Item> getInventory() {
		return inventory;
		
	}

	// returns the player inventory value stored at a given key s
	public int getItemQuantity(String name) {
		for (Item item : inventory) {
			if (item.getItemName().equals(name)) {
				return item.getItemQty();
			}
		}
		return 0;
	}

	// changes the player stat value stored at a given key
	public void setStat(String name, int value) {
		stats.put(name, value);
	}

	// changes the player inventory value stored at a given key
	public void addItem(String name, int value) {
		inventory.add(new Item(name, value));
	}
	
	// adds the player stat value stored at a given key
	public void addStat(String name, int value) {
		stats.put(name, value + stats.get(name));
	}

	// adds the player inventory value stored at a given key
	public void addInventory(String name, int value) {
		for (Item item : inventory) {
			if (item.getItemName().equals(name)) {
				int newQty = item.getItemQty() + value;
				if (newQty < 0)
					newQty = 0;
				item.setItemQty(newQty);
			}
		}
	}
	
	// subtracts the player stat value stored at a given key
	public void subtractStat(String name, int value) {
		stats.put(name, stats.get(name) - value);
	}
	
	public String toString(){
		String s = "";
		s+= "Name: "+name+"\nGender: "+gender.toString();
		s+=stats.toString();
		s+=inventory.toString();
		return s;
		
	}

}
