package pack;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.DocumentListener;


public class Board extends JPanel implements ActionListener {
	 
	  private static final int BOARD_WIDTH = 10;
	  private static final int BOARD_HEIGHT = 22;
	  Timer timer;
	  private Timer timer_faster;
	  private boolean isFallingFinished = false;
	  private boolean isStarted = false;
	  boolean isPaused = false;
	  public boolean startbool=false;
	  private boolean speedup_time=true;
	  private int curX = 0;
	  private int curY = 0;
	  Shape curPiece;
	  private Tetrominoes[] board;
	  int high;
	  int store=0;
	  int numLines = 0;
	  SpielFrame f;
	  private BufferedImage background;
	 
	  public Board(SpielFrame parent) throws IOException {
		f=parent;
	    setFocusable(true);
	    curPiece = new Shape();
	    timer = new Timer(400, this); // timer for lines down
	    timer_faster= new Timer(230, this);
	    board = new Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];
	    clearBoard();
	    addKeyListener(new Keyadapt());
	    background = ImageIO.read(new File("back.png"));    
	  }
	  
	  
	  @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(400, 400);
	    }
	  public int squareWidth() {
	    return (int) getSize().getWidth() / BOARD_WIDTH;
	  }
	 
	  public int squareHeight() {
	    return (int) getSize().getHeight() / BOARD_HEIGHT;
	  }
	 
	  public Tetrominoes shapeAt(int x, int y) {
	    return board[y * BOARD_WIDTH + x];
	  }
	 
	  void clearBoard() {
	    for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
	      board[i] = Tetrominoes.NO;
	    }
	  }
	 
	  private void pieceDropped() { //der Figur ist gefallen.
		  addstore();
		  for (int i = 0; i < 4; i++) {
	      int x = curX + curPiece.x(i);
	      int y = curY - curPiece.y(i);
	      board[y * BOARD_WIDTH + x] = curPiece.GetShape();
	    }
	 
	    removeFullLines();
	 
	    if (!isFallingFinished) {
	      newPiece();
	    }
	  }
	 
	  void addstore() {
			store+=200;
			f.store.setText(Integer.toString(store));
			if(store>=3000 & speedup_time==true) {
				timer.stop();
				timer_faster.start();
				speedup_time=false;
			}
		
	}
	public void newPiece() {
	    curPiece.RandomShape();
	    curX = BOARD_WIDTH / 2 + 1;
	    curY = BOARD_HEIGHT - 1 + curPiece.minY();
	    if(!startbool) {
	    	pause();
	    }
	    if (!tryMove(curPiece, curX, curY - 1)) {
	      curPiece.SetShape(Tetrominoes.NO);
	      timer.stop();
	      timer_faster.stop();
	      isStarted = false;
	      JOptionPane.showMessageDialog(f, "Gameover");
	      int h=0;
	      for(Spieler spiel : f.data.SpielerList) {
				if(spiel.getName().matches((String) f.combobox.getSelectedItem())) {
					if(store>spiel.GetHighStore()) {
						h=store;
						spiel.SetHighStore(store);
						f.high.setText(Integer.toString(h));
					}
					else {
						f.high.setText(Integer.toString(spiel.GetHighStore()));
					}
				}
	       }
	      store=0;
	      numLines=0;
		  f.store.setText(Integer.toString(store));
		  f.lines.setText(Integer.toString(numLines));
		  this.start();
		  startbool=true;
		  speedup_time=true;
	      this.pause();
	    }
	  }
	 
	  private void oneLineDown() { // 
	    if (!tryMove(curPiece, curX, curY - 1))
	      pieceDropped();
	  }
	 
	  @Override
	  public void actionPerformed(ActionEvent ae) {
	    if (isFallingFinished) {
	      isFallingFinished = false;
	      newPiece();
	    } else {
	      oneLineDown();
	    }
	  } 
	 
	  private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
	    Color color = shape.c;
	    g.setColor(color);
	    g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
	    g.setColor(color.brighter());
	    g.drawLine(x, y + squareHeight() - 1, x, y);
	    g.drawLine(x, y, x + squareWidth() - 1, y);
	    g.setColor(color.darker());
	    g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
	    g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	  }
	 
	  @Override
	  public void paint(Graphics g) { // painting function of Graphics Library (Override)
	    super.paint(g);
	    Dimension size = getSize();
	    int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
	    g.drawImage(background, 0, 0, null); //Am Anfang initializieren -> background
	    for (int i = 0; i < BOARD_HEIGHT; i++) {
	      for (int j = 0; j < BOARD_WIDTH; ++j) {
	        Tetrominoes shape = shapeAt(j, BOARD_HEIGHT - i - 1);
	 
	        if (shape != Tetrominoes.NO) {
	          drawSquare(g, j * squareWidth(), boardTop + i * squareHeight(), shape);
	        }
	      }
	    }
	 
	    if (curPiece.GetShape() != Tetrominoes.NO) {
	      for (int i = 0; i < 4; ++i) {
	        int x = curX + curPiece.x(i);
	        int y = curY - curPiece.y(i);
	        drawSquare(g, x * squareWidth(), boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(), curPiece.GetShape());
	      }
	    }
	  }
	 
	  public void start() {
	    if (isPaused)
	      return;
	   
	    isStarted = true;
	    isFallingFinished = false;
	    numLines = 0;
	    clearBoard();
	    newPiece();
	    if(startbool) {
	    	 timer.start();
	    }
	  }
	 
	  public void pause() { 
		    if (!isStarted)
		      return;
		    
		    isPaused = !isPaused;
		    if(!startbool) {
		    	timer.stop();
		    }
		    else {
		    
		    	timer.start();
		    }
		    if (isPaused) {
		      timer.stop();
		      timer_faster.stop();
		    } else if(store>=3000){
		      timer_faster.start();
		    }
		    else {
		    	timer.start();
		    }
		 
		    repaint();
		  }
	 
	   boolean tryMove(Shape newPiece, int newX, int newY) {
	    for (int i = 0; i < 4; ++i) {
	      int x = newX + newPiece.x(i);
	      int y = newY - newPiece.y(i); //neue koordinate 
	 
	      if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
	        return false; // nicht in dem Spielfeld sich befindet
	 
	      if (shapeAt(x, y) != Tetrominoes.NO)
	        return false;
	    }
	 
	    curPiece = newPiece;
	    curX = newX;
	    curY = newY;
	    repaint();
	 
	    return true;
	  }
	 
	  private void removeFullLines() {
	    int numFullLines = 0;
	 
	    for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
	      boolean lineIsFull = true;
	 
	      for (int j = 0; j < BOARD_WIDTH; ++j) {
	        if (shapeAt(j, i) == Tetrominoes.NO) {
	          lineIsFull = false; // wenn irgendwie (von oben bis unten) eine leere "quadrat"  gibt-> lineISFull->false-> break;
	          break;
	        }
	      }
	 
	      if (lineIsFull) {
	        ++numFullLines; 
	 
	        for (int k = i; k < BOARD_HEIGHT - 1; ++k) { // von i height bis unten
	          for (int j = 0; j < BOARD_WIDTH; ++j) {
	            board[k * BOARD_WIDTH + j] = shapeAt(j, k + 1); //eine Zeile nach unten bringen
	          }
	        }
	        addLines();
	      }
	 
	      if (numFullLines > 0) {
	        numLines += numFullLines; //es kann mehr sein
	        
	        isFallingFinished = true;
	        curPiece.SetShape(Tetrominoes.NO);
	        repaint();
	      }
	    }
	  }
	 
	  void addLines() {
		numLines++;
		store+=2000;
		f.store.setText(Integer.toString(store));
		f.lines.setText(Integer.toString(numLines));
		if(store>=3000 & speedup_time==true) {
			timer.stop();
			timer_faster.start();
			speedup_time=false;
		}
		
	}
	void dropDown() {
	    int newY = curY;
	 
	    while (newY > 0) { //Bis untere Zeile errechen...
	      if (!tryMove(curPiece, curX, newY - 1)) {
	    	  break; // ...oder nicht weiter fallen kann
	      }
	 
	      --newY;
	    }
	 
	    pieceDropped();
	  }
	  public class Keyadapt implements KeyListener {

			@Override
			public void keyPressed(KeyEvent e) {
				if(!isStarted||curPiece.GetShape()==Tetrominoes.NO) {
					return;
				}
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_LEFT) {
					tryMove(curPiece,curX-1,curY);
				}
				else if(key==KeyEvent.VK_RIGHT) {
					tryMove(curPiece,curX+1,curY);
				}
				else if(key==KeyEvent.VK_UP) {
					tryMove(curPiece.rotateright(),curX,curY);
				}
				else if(key==KeyEvent.VK_DOWN) {
					dropDown();
				}
				else if(key==KeyEvent.VK_SPACE) {
					pause();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

		}
	}

