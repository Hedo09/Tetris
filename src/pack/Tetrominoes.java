package pack;
import java.awt.Color;

public enum Tetrominoes {
	NO(new int[][] {{0,0},{0,0},{0,0},{0,0}},new Color(0,0,0)),
	Z(new int[][] {{0,0},{0,-1},{-1,0},{-1,1}},new Color(204,102,102)),
	S(new int[][] {{0,0},{0,-1},{1,0},{1,1}},new Color(102,204,102)),
	Line(new int[][] {{0,-1},{0,0},{0,1},{0,2}},new Color(102,102,204)),
	Square(new int[][] {{0,0},{1,0},{0,1},{1,1}},new Color(204,204,102)),
	L(new int[][] {{-1,-1},{0,-1},{0,0},{0,1}}, new Color(204,102,204)),
	T(new int[][] {{0,0},{-1,0},{1,0},{0,1}},new Color(102,204,204)),
	J(new int[][] {{1,-1},{0,-1},{0,0},{0,1}}, new Color(102,102,102));
	
	public int[][] coords;
	public Color c;
	Tetrominoes(int[][] coo,Color c1) {
		this.c=c1;
		this.coords=coo;
	}
}
