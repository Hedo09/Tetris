package pack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

public class ShapeTest {
	Shape s;
	Tetrominoes tetro;
	
	@Before
	public void setUp() {
		s=new Shape();
	}
	
	@Test
	public void set_tetro() {
		s.SetShape(tetro.Square);
		boolean value=false;
		if(s.koordinate[1][0]==1 & s.koordinate[2][1]==1 & s.koordinate[3][0]==1 & s.koordinate[3][1]==1) {
		value=true;
		}
		Assert.assertEquals(true, value);
		//eingestellt.
	}
	@Test
	public void rotate_square() {
		s.SetShape(tetro.Square);
		Shape s1=new Shape();
		s1= s.rotateright();
		boolean value=true;
		for(int i=0;i<4;i++) {
			for(int j=0;j<2;j++) {
				if(s1.koordinate[i][j]!=s.koordinate[i][j]) {
					value=false;
				}
			}
		}
		Assert.assertEquals(true, value);
	}
	
}
