package sk.upjs.paz1c.griddlers;

public enum Obdobie {
	DEN("Deň"), TYZDEN("Týždeň"), MESIAC("Mesiac"), VSETKY("Všetky");

	private String nazov;

	private Obdobie(String nazov) {
		this.nazov = nazov;
	}

	@Override
	public String toString() {
		return nazov;
	}
}
