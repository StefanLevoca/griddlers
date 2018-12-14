package sk.upjs.paz1c.griddlers;

public enum Narocnost {
	LAHKA("Ľahká"), STREDNA("Stredná"), TAZKA("Ťažká");

	private String nazov;

	private Narocnost(String nazov) {
		this.nazov = nazov;
	}

	@Override
	public String toString() {
		return nazov;
	}

}
