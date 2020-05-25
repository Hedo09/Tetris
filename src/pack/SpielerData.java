package pack;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class SpielerData extends AbstractTableModel{
	List<Spieler> SpielerList= new ArrayList<Spieler>();
	
	public void addSpieler(String n, int hs) {
		Spieler s=new Spieler(n,hs);
		SpielerList.add(s);
		
	}
	public void print() {
		for(Spieler spiel : SpielerList) {
            System.out.println(spiel.getName()+"  "+spiel.GetHighStore()+"       ");
        }
	}
	@Override
	public int getRowCount() {
		return SpielerList.size();
	}
	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Spieler student = SpielerList.get(rowIndex);
		 switch(columnIndex) {
		 case 0: return student.getName();
		 default: return student.GetHighStore();
		 }
	}
	public String getColumnName(int column) {
		if(column==0) {
			return"Name";
		}
	return "HighStore";
	}
	public Class getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
}
