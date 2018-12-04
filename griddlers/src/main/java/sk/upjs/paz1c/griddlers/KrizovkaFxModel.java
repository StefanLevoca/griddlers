package sk.upjs.paz1c.griddlers;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Narocnost;
import sk.upjs.paz1c.griddlers.entity.Policko;

public class KrizovkaFxModel {

	private Long id;
	private ObjectProperty<Integer> sirka = new SimpleObjectProperty<>();
	private ObjectProperty<Integer> vyska = new SimpleObjectProperty<>();
	private StringProperty nazov = new SimpleStringProperty();
	private ObjectProperty<Narocnost> narocnost = new SimpleObjectProperty<>();
	private List<Policko> riesenie = new ArrayList<>();
	private List<Legenda> legendaL = new ArrayList<>();
	private List<Legenda> legendaH = new ArrayList<>();
	private Krizovka krizovka;

	public KrizovkaFxModel(Krizovka krizovka) {
		this.krizovka = krizovka;
		setSirka(krizovka.getSirka());
		setVyska(krizovka.getVyska());
		setNazov(krizovka.getNazov());
		setNarocnost(krizovka.getNarocnost());
	}

	public KrizovkaFxModel() {
		// TODO Auto-generated constructor stub
	}

	public Krizovka getKrizovka() {
		Krizovka krizovka = new Krizovka();
		krizovka.setId(getId());
		krizovka.setSirka(getSirka());
		krizovka.setVyska(getVyska());
		krizovka.setNazov(getNazov());
		krizovka.setNarocnost(getNarocnost());
		krizovka.setRiesenie(getRiesenie());
		krizovka.setLegendaL(getLegendaL());
		krizovka.setLegendaH(getLegendaH());
		return krizovka;
	}

	public void setKrizovka(Krizovka krizovka) {
		setId(krizovka.getId());
		setSirka(krizovka.getSirka());
		setVyska(krizovka.getVyska());
		setNazov(krizovka.getNazov());
		setNarocnost(krizovka.getNarocnost());
		;
		setRiesenie(krizovka.getRiesenie());
		setLegendaL(krizovka.getLegendaL());
		setLegendaH(krizovka.getLegendaH());
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public void setSirka(int sirka) {
		this.sirka.set(sirka);
	}

	public int getSirka() {
		return this.sirka.get();
	}

	public void setVyska(int vyska) {
		this.vyska.set(vyska);
	}

	public int getVyska() {
		return this.vyska.get();
	}

	public void setNazov(String nazov) {
		this.nazov.set(nazov);
	}

	public String getNazov() {
		return this.nazov.get();
	}

	public void setNarocnost(Narocnost narocnost) {
		this.narocnost.set(narocnost);
	}

	public Narocnost getNarocnost() {
		return this.narocnost.get();
	}

	public ObjectProperty<Integer> getVyskaProperty() {
		return vyska;
	}

	public ObjectProperty<Integer> getSirkaProperty() {
		return sirka;
	}

	public StringProperty getNazovProperty() {
		return nazov;
	}

	public ObjectProperty<Narocnost> getNarocnostProperty() {
		return narocnost;
	}

	public List<Policko> getRiesenie() {
		return riesenie;
	}

	public void setRiesenie(List<Policko> riesenie) {
		this.riesenie = riesenie;
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

	@Override
	public String toString() {
		return "KrizovkaFxModel [id=" + id + ", sirka=" + sirka.get() + ", vyska=" + vyska.get() + ", nazov="
				+ nazov.get() + ", narocnost=" + narocnost.get() + "]";
	}

}
