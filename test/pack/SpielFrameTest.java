package pack;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpielFrameTest {
	SpielFrame sf;
	
	@Before
	public void setUp() throws FileNotFoundException, ClassNotFoundException, IOException{
		sf= new SpielFrame();
	}
	@Test
	public void click_startbutton() {
		sf.startbutton_fur_test();
		boolean value_pause = sf.board.isPaused;
		String name =(String) sf.combobox.getSelectedItem();
		int hs=0;
		for(Spieler spiel : sf.data.SpielerList) {
			if(spiel.getName().matches(name)) {
			hs= spiel.GetHighStore();
			break;
			}
        }
		Assert.assertEquals(hs,Integer.parseInt(sf.high.getText()),0); //der Highstorewert ist gleich mit der Highstore dem Spieler, der zurzeit spielt.
		//Assert.assertEquals(false, value_pause);
	}
	@Test 
	public void click_newgame() {
		sf.startbutton_fur_test();
		sf.board.dropDown();
		sf.board.dropDown();
		sf.board.dropDown();
		Assert.assertEquals(600, sf.board.store,0);
		sf.newgamebutton_fur_test();
		boolean bool= sf.board.isPaused;
		int value= sf.board.store;
		Assert.assertEquals(0, value,0);
		Assert.assertEquals(true,bool);
	}
}
