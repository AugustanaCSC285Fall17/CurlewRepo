package edu.augustana.csc285.game.desktop;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.augustana.csc285.game.datamodel.Slide;

public class SlideTest {
	
	public Slide slide  = new Slide();

	@Test
	public void test() {
		slide.setTitle("test");
		assertEquals("test", slide.getTitle());
	}
	
	@Test
	public void testNumberName() {
		slide.setTitle("1992");
		assertEquals("1992", slide.getTitle());
	}
	
	@Test
	public void testCapWord(){
		slide.setTitle("EMU");
		assertEquals("EMU", slide.getTitle());
	}

}
