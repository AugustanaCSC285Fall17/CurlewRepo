package edu.augustana.csc285.game.datamodel;

import static org.junit.Assert.*;


import org.junit.Test;

public class ItemTest {
	public Item apple = new Item();
	

	@Test
	public void testPostiveNumber() {	
		apple.setItemQty(30);
		assertEquals(30, apple.getItemQty());
	}

	@Test
	public void testNegativeNumber() {
		apple.setItemQty(-6);
		assertEquals(0, apple.getItemQty());
	}
	
	@Test
	public void testZeroValue(){
		apple.setItemQty(0);
		assertEquals(0, apple.getItemQty());
	}


}
