package pack;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SpielerDataTest {
	SpielerData data;
	
	@Before
	public void setUp() {
		data= new SpielerData();
	}
	
	@Test
	public void add_Spieler() {
		data.addSpieler("norbi", 0);
		boolean value=false;
		for(Spieler spiel : data.SpielerList) {
			if(spiel.getName().equals("norbi")) {
				value=true;
			}
		}
		Assert.assertEquals(true, value);
		// norbi wird zu der List addiert.
	}
}
