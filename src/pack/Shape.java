package pack;

import java.awt.Color;
import java.util.Random;



public class Shape { // für Tetrominoes Bewegung
	
	private Tetrominoes tetro;
	int [][] koordinate;
	public Shape() {
		koordinate=new int[4][2];
		this.SetShape(tetro.NO);
	}
	public void SetShape(Tetrominoes t) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<2;j++) {
				koordinate[i][j]=t.coords[i][j];
			}
		}
		tetro=t;
	}
	private void SetX(int index, int x) {
		koordinate[index][0]=x;
	}
	private void SetY( int index, int y) {
		koordinate[index][1]=y;
	}
	public int GetX(int index) {
		return koordinate[index][0];
	}
	public int GetY(int index) {
		return koordinate[index][1];
	}
	public Tetrominoes GetShape() {
		return tetro;
	}
	public void RandomShape() {
		Random r = new Random();
	    int x = Math.abs(r.nextInt()) % 7 + 1;
	    Tetrominoes[] values = Tetrominoes.values();
	    SetShape(values[x]);	
	 }
	public int minY() {
		int m=koordinate[0][1];
		for(int i=1;i<4;i++) {
			m=Math.min(m, koordinate[i][1]);
		}
		return m;
	}
	public int minX() {
		int m=koordinate[0][0];
		for(int i=1;i<4;i++) {
			m=Math.min(m, koordinate[m][0]);
		}
		return m;
	}
	
	public Shape rotateright() {
		if(tetro==Tetrominoes.Square) {
			return this;
		}
	Shape s= new Shape();
	s.tetro=tetro;
	for(int i=0;i<4;i++) {
		s.SetX(i, y(i));
		s.SetY(i, -x(i));
	}
	return s;
	}
	public int x(int i) {
		return koordinate[i][0];
	}
	public int y(int i) {
		return koordinate[i][1];
	}
}
