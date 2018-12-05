package sk.upjs.paz1c.griddlers.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Hra {
	
	private Long id;
	private int pocetTahov;
	private Long krizovkaId;
	private String nazovKrizovky;
	private List<PolickoHry> polickaHry;
	private int casRiesenia;
	private boolean ukoncena;
	private LocalDateTime zaciatok;
	private LocalDateTime medzicas;
	private LocalDateTime poslednyMedzicas;
	private LocalDateTime koniec;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPocetTahov() {
		return pocetTahov;
	}
	public void setPocetTahov(int pocetTahov) {
		this.pocetTahov = pocetTahov;
	}
	public Long getKrizovkaId() {
		return krizovkaId;
	}
	public String getNazovKrizovky() {
		return nazovKrizovky;
	}
	public void setKrizovkaId(Long krizovka) {
		this.krizovkaId = krizovka;
	}
	public void setNazovKrizovky(String nazovKrizovky) {
		this.nazovKrizovky = nazovKrizovky;
	}
	public List<PolickoHry> getPolickaHry() {
		return polickaHry;
	}
	public void setPolickaHry(List<PolickoHry> polickaHry) {
		this.polickaHry = polickaHry;
	}
	public int getCasRiesenia() {
		return casRiesenia;
	}
	public void setCasRiesenia(int casRiesenia) {
		this.casRiesenia = casRiesenia;
	}
	public boolean isUkoncena() {
		return ukoncena;
	}
	public void setUkoncena(boolean ukoncena) {
		this.ukoncena = ukoncena;
	}
	public LocalDateTime getZaciatok() {
		return zaciatok;
	}
	public void setZaciatok(LocalDateTime zaciatok) {
		this.zaciatok = zaciatok;
	}
	public LocalDateTime getMedzicas() {
		return medzicas;
	}
	public void setMedzicas(LocalDateTime medzicas) {
		this.medzicas = medzicas;
	}
	public LocalDateTime getPoslednyMedzicas() {
		return poslednyMedzicas;
	}
	public void setPoslednyMedzicas(LocalDateTime poslednyMedzicas) {
		this.poslednyMedzicas = poslednyMedzicas;
	}
	public LocalDateTime getKoniec() {
		return koniec;
	}
	public void setKoniec(LocalDateTime koniec) {
		this.koniec = koniec;
	}
	
	
	
}
