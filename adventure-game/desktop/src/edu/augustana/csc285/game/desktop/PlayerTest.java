package edu.augustana.csc285.game.desktop;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.augustana.csc285.game.datamodel.Player;

public class PlayerTest {
	public Player player = new Player();

	@Test
	public void testWordName() {
		player.setName("Bob");
		assertEquals("Bob", player.getName());
	}
	
	@Test
	public void testNumberName() {
		player.setName("123");
		assertEquals("123", player.getName());
	}
	
	@Test
	public void testCapWordName() {
		player.setName("EMU");
		assertEquals("EMU", player.getName());
	}

}
