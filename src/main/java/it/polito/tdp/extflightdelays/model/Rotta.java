package it.polito.tdp.extflightdelays.model;

public class Rotta {
	
@Override
	public boolean equals(Object obj) {
		Rotta r2=(Rotta)obj;
		if((r2.a1==this.a1&&r2.a2==this.a2)||(r2.a2==this.a1&&r2.a1==this.a2))
			return true;
		return false;
		
	}

Airport a1;
Airport a2;
int distanza;

public Rotta(Airport a1, Airport a2, int distanza) {
	this.a1 = a1;
	this.a2 = a2;
	this.distanza = distanza;
}

public void aumentaDistanza() {
	distanza++;
}



}
