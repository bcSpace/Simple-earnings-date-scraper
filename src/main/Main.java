package main;

public class Main {
	
	LoadDate ld;
	String symbol = "AAPL";
	
	
	Main() {
		ld = new LoadDate();
		ld.loadDoc(symbol);
		ld.getEarnings();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
