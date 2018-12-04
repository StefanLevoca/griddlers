package sk.upjs.paz1c.griddlers.entity;

public enum Obdobie {
	DEN("Deň"),
	TYZDEN("Týždeň"),
	MESIAC("Mesiac");
	
	private String nazov;
	
	private Obdobie(String nazov) {
		this.nazov = nazov;
	}
	
	@Override
	public String toString() {
		return nazov;
	}
}
