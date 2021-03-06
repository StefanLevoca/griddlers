package sk.upjs.paz1c.griddlers.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Hra {

	private Long id;
	private int pocetTahov;
	private Long krizovkaId;
	private String nazovKrizovky;
	private List<PolickoHry> polickaHry;
	private long casRiesenia;
	private boolean ukoncena;
	private LocalDateTime zaciatok;
	private LocalDateTime medzicas;
	private LocalDateTime koniec;

	@Override
	public String toString() {

		return "ID: " + id + ", Počet ťahov: " + pocetTahov
				+ ", Čas riešenia: " + casRiesenia + ", Čas začiatku: " + preformatujCas(zaciatok);
	}

	public static String preformatujCas(LocalDateTime cas) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		return cas.format(format);
	}

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

	public long getCasRiesenia() {
		return casRiesenia;
	}

	public void setCasRiesenia(long casRiesenia) {
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

	public LocalDateTime getKoniec() {
		return koniec;
	}

	public void setKoniec(LocalDateTime koniec) {
		this.koniec = koniec;
	}

}
