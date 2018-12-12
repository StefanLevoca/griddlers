package sk.upjs.paz1c.griddlers.biznis;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sk.upjs.paz1c.griddlers.VytvVytvaranieController;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;

public class VytvVytvaranieManager extends Platnovac{

	private static final int VELKOST_POLICKA = VytvVytvaranieController.VELKOST_POLICKA;

	private Krizovka krizovka;

	public VytvVytvaranieManager(Krizovka krizovka) {
		this.krizovka = krizovka;
	}

	

	// metoda ktora spracuje stlacenie tlacidla a vrati objekt triedy policko ktore
	// nasledne spracuje metoda v controlleri
	public Policko spracujKlik(MouseEvent event, Canvas platno) {
		int x1 = (int) (event.getX() / VELKOST_POLICKA);
		int y1 = (int) (event.getY() / VELKOST_POLICKA);
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			kresliStvorec(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA, Color.BLACK);
			return new Policko(true, x1, y1);
		} else if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			kresliStvorec(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA, Color.WHITE);
			return new Policko(false, x1, y1);
		} else if (event.getButton().equals(MouseButton.SECONDARY)) {
			kresliBodku(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA);
			return new Policko(false, x1, y1);
		}
		return null;
	}

	// vyplni zoznam riesenie polickami false
	public void vyplnRiesenieFalse() {
		int sirka = krizovka.getSirka();
		int vyska = krizovka.getVyska();
		List<Policko> riesenie = krizovka.getRiesenie();
		Policko p;
		for (int i = 0; i < sirka; i++) {
			for (int j = 0; j < vyska; j++) {
				p = new Policko(false, i, j);
				riesenie.add(p);
			}
		}
	}

	// metoda ktora testuje ci krizovka moze byt ulozena
	public boolean mozeUlozit() {
		int sirka = krizovka.getSirka();
		int vyska = krizovka.getVyska();
		boolean[][] rieseniePole = riesenieNaMaticu();
		// ak riadok nema aspon jednu hodnotu true nemoze byt ulozena krizovka
		for (int i = 0; i < vyska; i++) {
			boolean maTrue = false;
			for (int j = 0; j < sirka; j++) {
				if (rieseniePole[i][j]) {
					maTrue = true;
					break;
				}
			}
			if (!maTrue) {
				return false;
			}
		}
		// analogicky pre stlpce
		for (int i = 0; i < sirka; i++) {
			boolean maTrue = false;
			for (int j = 0; j < vyska; j++) {
				if (rieseniePole[j][i]) {
					maTrue = true;
					break;
				}
			}
			if (!maTrue) {
				return false;
			}
		}
		return true;
	}
	
	// metoda ktora vytvori legenduH
	public List<Legenda> vytvorLegenduH() {
		boolean horna = true;
		List<Legenda> legendaH = vytvorLegendu(horna);
		return legendaH;

	}
	
	// anologicka metoda ku vytvorLegenduH pre lavu legendu
	public List<Legenda> vytvorLegenduL() {
		boolean horna = false;
		List<Legenda> legendaL = vytvorLegendu(horna);
		return legendaL;
	}

	// metoda ktora vytvori legendu
	private List<Legenda> vytvorLegendu(boolean horna) {
		boolean[][] riesenie = riesenieNaMaticu();
		if (horna) {
			riesenie = transponujMaticu(riesenie);
		}
		List<Legenda> legenda = new ArrayList<>();
		for (int i = 0; i < riesenie.length; i++) {
			List<Integer> hodnotyLegendy = riadokNaHodnoty(riesenie[i]);
			for (int j = 0; j < hodnotyLegendy.size(); j++) {
				Legenda polickoLegendy = new Legenda(horna, i, j, hodnotyLegendy.get(hodnotyLegendy.size() - j - 1));
				legenda.add(polickoLegendy);
			}
		}
		return legenda;
	}

	// vezme riadok matice riesenia a priradi mu prilusne hodnoty legendy
	private List<Integer> riadokNaHodnoty(boolean[] riadok) {
		List<Integer> hodnotyLegendy = new ArrayList<>();
		int vyplneneZaSebou = 0;
		for (int i = 0; i < riadok.length; i++) {
			if (riadok[i] == true) {
				vyplneneZaSebou++;
			} else {
				if (vyplneneZaSebou == 0) {
					continue;
				}
				hodnotyLegendy.add(vyplneneZaSebou);
				vyplneneZaSebou = 0;
			}
		}
		if (riadok[riadok.length - 1] == true) {
			hodnotyLegendy.add(vyplneneZaSebou);
		}
		return hodnotyLegendy;
	}

	// prerobi riesenie na maticu
	private boolean[][] riesenieNaMaticu() {
		List<Policko> riesenieList = krizovka.getRiesenie();
		int vyska = krizovka.getVyska();
		int sirka = krizovka.getSirka();
		System.out.println(vyska + " " + sirka);
		boolean[][] rieseniePole = new boolean[vyska][sirka];
		int riadok;
		int stlpec;
		// prerobi zoznam riesenie na maticu
		for (Policko policko : riesenieList) {
			riadok = policko.getSurY();
			stlpec = policko.getSurX();
			rieseniePole[riadok][stlpec] = policko.getStav();
		}
		return rieseniePole;
	}

	// transponuje maticu
	private boolean[][] transponujMaticu(boolean[][] matica) {
		boolean[][] novaMatica = new boolean[matica[0].length][matica.length];
		for (int i = 0; i < novaMatica.length; i++) {
			for (int j = 0; j < novaMatica[0].length; j++) {
				novaMatica[i][j] = matica[j][i];
			}
		}
		return novaMatica;
	}
	
	// metoda ktora premaze platno a nanovo vykresli mriezku
	public void canvasReset(Canvas platno) {
		GraphicsContext gc = platno.getGraphicsContext2D();
		gc.clearRect(0, 0, platno.getWidth(), platno.getHeight());
		vytvorMriezku(platno);
	}

}