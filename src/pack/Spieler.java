package pack;

import java.io.Serializable;

public class Spieler implements Serializable {
	private String name;
	private int highstore;
	
	public Spieler(String n, int hs) {
		name=n;
		highstore=hs;
	}
	public String getName() {
		return name;
	}
	public int GetHighStore(){
		return highstore;
	}
	public void SetHighStore(int hs) {
		highstore=hs;
	}
}
