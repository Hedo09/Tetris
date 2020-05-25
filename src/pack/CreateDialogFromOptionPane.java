package pack;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.WindowEvent;

public class CreateDialogFromOptionPane {
	
        public JFrame parent = new JFrame();
        String name= "default";
        SpielerData data1=new SpielerData();
        public CreateDialogFromOptionPane() {
        
        while(name!=null) {
        name = JOptionPane.showInputDialog(parent,
                    "What is your name?", null);
        boolean exist=false;
        if(name!=null) {
        	for(Spieler spiel : data1.SpielerList) {
        		if(spiel.getName().equals(name)) {
        			exist=true; //Check, ob es schon exsistiert 
        		}
        	}
        	if(!exist) {
        		data1.addSpieler(name, 0);
        	}
        }
       
        parent.pack();
        parent.setVisible(true);
        parent.setSize(100,100);
        parent.setLocationRelativeTo(null);
        }
        parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
        /*button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String name = JOptionPane.showInputDialog(parent,
                        "What is your name?", null);
            }
        });*/
    }
}
