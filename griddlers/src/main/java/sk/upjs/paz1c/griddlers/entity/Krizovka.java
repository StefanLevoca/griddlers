package sk.upjs.paz1c.griddlers.entity;

import java.util.List;

public class Krizovka {

	private Long id;
	private String nazov;
	private Narocnost narocnost;
	private List<Policko> riesenie;
	private List<Legenda> legendaL;
	private List<Legenda> legendaH;
	private int sirka;
	private int vyska;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public List<Legenda> getLegendaL() {
		return legendaL;
	}

	public void setLegendaL(List<Legenda> legendaL) {
		this.legendaL = legendaL;
	}

	public List<Legenda> getLegendaH() {
		return legendaH;
	}

	public void setLegendaH(List<Legenda> legendaH) {
		this.legendaH = legendaH;
	}

	public Narocnost getNarocnost() {
		return narocnost;
	}

	public void setNarocnost(Narocnost narocnost) {
		this.narocnost = narocnost;
	}

	public List<Policko> getRiesenie() {
		return riesenie;
	}

	public void setRiesenie(List<Policko> riesenie) {
		this.riesenie = riesenie;
	}

	public int getSirka() {
		return sirka;
	}

	public void setSirka(int sirka) {
		this.sirka = sirka;
	}

	public int getVyska() {
		return vyska;
	}

	public void setVyska(int vyska) {
		this.vyska = vyska;
	}

}
