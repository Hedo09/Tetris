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

@RunWith(Parameterized.class)
public class BoardTest {

	public Board board;
	public SpielFrame sf;
	int a;
	int b;
	
	public BoardTest(int a, int b) {
		this.a=a;
		this.b=b;
	}
	
	@Before
	public void setUp() throws IOException, ClassNotFoundException {
		sf= new SpielFrame();
		board = new Board(sf);
	}
	@Test
	public void storeadd() {
		int store_before=board.store;
		board.addstore();
		int store_after=board.store;
		Assert.assertEquals(store_before+200, store_after, 0);
	}
	@Test
	public void linesadd() {
		int store_before=board.store;
		board.addLines();
		int store_after=board.store;
		Assert.assertEquals(store_before+2000, store_after, 0);
	}
	@Test
	public void lines() {
		int lines_before=board.numLines;
		board.addLines();
		int lines_after= board.numLines;
		Assert.assertEquals(lines_before+1, lines_after, 0);
	}
	@Test
	public void move_tetro() {
		boolean try_move= board.tryMove(board.curPiece,a,b);
		Assert.assertEquals(true, try_move);
	}
	@Parameters
	public static List<Object[]> parameters() {
	List<Object[]> params = new ArrayList<Object[]>();
	params.add(new Object[] {0, 0}); // gut
	params.add(new Object[] {10, 0}); // falsch
	params.add(new Object[] {9, 3}); // gut
	params.add(new Object[] {5, 4}); // gut
	params.add(new Object[] {8, 12}); //gut
	//also gut getestet. Das Ergebnis ist diese LogicalWerte.
	return params;
	}
}
