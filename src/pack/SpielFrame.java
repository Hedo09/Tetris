package pack;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;


public class SpielFrame extends JFrame{
        
	SpielerData data;
	private JFrame aufnehmen;
	
	JPanel panel_gameover;
	JPanel jp2;
	JTextField high;
	JTextField store;
	JTextField lines;
	JComboBox combobox;
	private JButton start;
	private JButton newgame;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	Board board;
	
	ActionListener StartbuttonListener;
	ActionListener NewgamebuttonListener;
	ActionListener t;
	ActionListener aufnehmen_list;
	ActionListener toplist;
	DocumentListener storetf;
	List<String> ls = new ArrayList<String>();
	public JButton test;
	
	public SpielFrame() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		//Date einladen:
		data=new SpielerData();
		File f=new File("spieler.txt");
		
		if(f.exists() & f.length()>0) {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        data.SpielerList = (List<Spieler>)ois.readObject();
        ois.close();
        for(Spieler spiel : data.SpielerList) {
        	ls.add(spiel.getName());
        	}
		}
        //Am Ende speichern wir die neue Spieler und ihre Highstore:
		addWindowListener(new WindowAdapter() {
	        @Override 
	        public void windowClosing(WindowEvent e) {
	            try {
	                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("spieler.txt"));
	                oos.writeObject(data.SpielerList);
	                oos.close();
	            } catch(Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
		
		
		//data=new SpielerData();
		board = new Board(this);
	    add(board,BorderLayout.WEST);
	    board.start();
	    
	    setSize(1000, 1000);
	    setTitle("My Tetris");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    //String obj[]= {"A","B","C","D"};
	    
	    jp2= new JPanel(new GridLayout(6,2,1,1));
		start= new JButton("Start");
		newgame = new JButton("New Game");
		high=new JTextField("highstore..");
		store= new JTextField(5);
		lines= new JTextField("1");
		combobox=new JComboBox();
		combobox.setModel(new DefaultComboBoxModel(ls.toArray()));
		combobox.setSize(50,40);
		combobox.setFocusable(false);
		
		store.setText(Integer.toString(board.store));
		high.setText(Integer.toString(board.high));
		lines.setText(Integer.toString(board.numLines));
		
		
		high.setEditable(false);
		store.setEditable(false);
		lines.setEditable(false);
		
		l1=new JLabel("Highstore:");
		l2= new JLabel("Store:");
		l3= new JLabel("lines:");
		l4= new JLabel("Spieler:");
		
		//Listeners:
		StartbuttonListener=new startlistener();
		start.addActionListener(StartbuttonListener);
		start.setFocusable(false);
		t= new TActionListener();
		
		
		NewgamebuttonListener = new newgamelistener();
		newgame.addActionListener(NewgamebuttonListener);
		newgame.setFocusable(false);
		
		jp2.add(l1);
		jp2.add(high);
		jp2.add(l2);
		jp2.add(store);
		jp2.add(l3);
		jp2.add(lines);
		jp2.add(l4);
		jp2.add(combobox);
		jp2.add(start);
		jp2.add(newgame);
		
		
		JMenuBar mb= new JMenuBar();
		JMenu menu= new JMenu("Info");
		JMenuItem mi2= new JMenuItem("Topliste");
		toplist= new toplist_listener();
		mi2.addActionListener(toplist);
		
		mb.add(menu);
		menu.add(mi2);
		
		
		test=new JButton("Spieler aufnehmen");
		aufnehmen_list=new Aufnehmen_act();
		test.addActionListener(aufnehmen_list);
		test.setFocusable(false);
		jp2.add(test);
		
		this.add(mb,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.EAST);
	}
	public class startlistener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name =(String) combobox.getSelectedItem();
			int hs;
			for(Spieler spiel : data.SpielerList) {
				if(spiel.getName().matches(name)) {
				hs= spiel.GetHighStore();
				high.setText(Integer.toString(hs));
				break;
				}
	        }
			board.startbool=true;
			board.pause();
			
		}

	}
	public class TActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			store.setText(Integer.toString(board.store));

		}

	}
	public class Aufnehmen_act implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CreateDialogFromOptionPane c= new CreateDialogFromOptionPane();
			data.SpielerList.addAll(c.data1.SpielerList);
			for(Spieler spiel : data.SpielerList) {
				if(!ls.contains(spiel.getName())) {
				combobox.addItem(spiel.getName());
	            ls.add(spiel.getName());
				}
	        }
		}

	}
	public class newgamelistener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s =(String) combobox.getSelectedItem();
			for(Spieler spiel: data.SpielerList) {
				if(s.equals(spiel.getName())) {
					if(board.store>spiel.GetHighStore()) {
						spiel.SetHighStore(board.store);
						high.setText(Integer.toString(board.store));
					}
				}
			}
			board.store=0;
			board.numLines=0;
			store.setText(Integer.toString(board.store));
			lines.setText(Integer.toString(board.numLines));
			board.start();
			board.startbool=true;
			board.pause();
			
		}

	}
	public class toplist_listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTable table= new JTable(data);
			JScrollPane sp=new JScrollPane(table);
			TableRowSorter<SpielerData> sorter =new TableRowSorter<>(data);
	        table.setRowSorter(sorter);
	        table.getRowSorter().toggleSortOrder(1); // automatically sort table by highstore 
	        table.getRowSorter().toggleSortOrder(1); // 2 call same methode-> inverse
	        //table.setTableHeader(null);
			JOptionPane.showMessageDialog(null, sp, "TopListe", JOptionPane.INFORMATION_MESSAGE);

		}

	}
	void startbutton_fur_test() {
		String name =(String) combobox.getSelectedItem();
		int hs;
		for(Spieler spiel : data.SpielerList) {
			if(spiel.getName().matches(name)) {
			hs= spiel.GetHighStore();
			high.setText(Integer.toString(hs));
			break;
			}
        }
		board.startbool=true;
		board.pause();
	}
	void newgamebutton_fur_test() {
		String s =(String) combobox.getSelectedItem();
		for(Spieler spiel: data.SpielerList) {
			if(s.equals(spiel.getName())) {
				if(board.store>spiel.GetHighStore()) {
					spiel.SetHighStore(board.store);
					high.setText(Integer.toString(board.store));
				}
			}
		}
		board.store=0;
		board.numLines=0;
		store.setText(Integer.toString(board.store));
		lines.setText(Integer.toString(board.numLines));
		board.start();
		board.startbool=true;
		board.pause();
	}
	  public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, LineUnavailableException, UnsupportedAudioFileException {
	    SpielFrame myTetris = new SpielFrame();
	    myTetris.setLocationRelativeTo(null);
	    myTetris.pack();
	    myTetris.setVisible(true);
	    playmusic music=new playmusic();
	  }
	 
}

