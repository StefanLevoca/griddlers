package sk.upjs.paz1c.griddlers.biznis;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Legenda;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.LegendaDao;

public class RiesenieManager extends Platnovac {

	private Krizovka krizovka;
	private LegendaDao legendaDao;

	public RiesenieManager(Krizovka krizovka) {
		this.krizovka = krizovka;
		this.legendaDao = DaoFactory.INSTANCE.getLegendaDao();
	}

	// metoda urcena na vytvaranie mriezky legendy
	public void vytvorMriezku(Canvas platno, Color farba) {
		double sirka = platno.getWidth();
		double vyska = platno.getHeight();
		GraphicsContext gc = platno.getGraphicsContext2D();
		for (int i = 0; i <= sirka; i += VELKOST_POLICKA) {
			gc.setStroke(farba);
			gc.strokeLine(i, 0, i, vyska);
		}
		for (int i = 0; i <= vyska; i += VELKOST_POLICKA) {
			gc.setStroke(farba);
			gc.strokeLine(0, i, sirka, i);
		}
	}

	// metoda ktora spracuje stlacenie tlacidla a vrati objekt triedy polickoHry
	// ktore
	// nasledne spracuje metoda v controlleri
	public PolickoHry spracujKlik(MouseEvent event, Canvas platno) {
		int x1 = (int) (event.getX() / VELKOST_POLICKA);
		int y1 = (int) (event.getY() / VELKOST_POLICKA);
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			kresliStvorec(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA, Color.BLACK);
			return new PolickoHry(true, x1, y1);
		} else if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			kresliStvorec(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA, Color.WHITE);
			return new PolickoHry(null, x1, y1);
		} else if (event.getButton().equals(MouseButton.SECONDARY)) {
			kresliBodku(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA);
			return new PolickoHry(false, x1, y1);
		}
		return null;

	}

	// TODO napisat test
	// nastavi sa pozadovany stav policok hry
	public List<PolickoHry> inicializujPolickaHry() {
		List<PolickoHry> polickaHry = new ArrayList<>();
		List<Policko> riesenie = krizovka.getRiesenie();
		PolickoHry polickoHry;
		for (Policko pol : riesenie) {
			polickoHry = new PolickoHry(null, pol.getSurX(), pol.getSurY(), pol.getStav());
			polickaHry.add(polickoHry);
		}
		return polickaHry;
	}

	// metoda ktora vykresli legendu
	public void zobrazLegendu(Canvas platno, boolean horna) {
		List<Legenda> legenda;
		if (horna) {
			legenda = legendaDao.getHornaPodlaId(krizovka.getId());
		} else {
			legenda = legendaDao.getLavaPodlaId(krizovka.getId());
		}
		for (Legenda polickoLegendy : legenda) {
			vykresliLegendu(platno, polickoLegendy);
		}

	}
	
	public void obnovPlatno(Canvas platno, Hra hra) {
		List<PolickoHry> polickaHry = hra.getPolickaHry();
		GraphicsContext gc = platno.getGraphicsContext2D();
		double x1;
		double y1;
		for(PolickoHry polickoHry: polickaHry) {
			x1 = polickoHry.getSurX() * VELKOST_POLICKA;
			y1 = polickoHry.getSurY() * VELKOST_POLICKA;
			if(polickoHry.getStav() != null && polickoHry.getStav() == true) {
				kresliStvorec(gc, x1, y1, Color.BLACK);
			}else if(polickoHry.getStav() != null && polickoHry.getStav() == false) {
				kresliBodku(gc, x1, y1);
			}
		}
	}
	
	
	// pomocna metoda pri vytvarani legendy
	private void vykresliLegendu(Canvas platno, Legenda polickoLegendy) {
		int poradie = polickoLegendy.getPoradie();
		int riadokStlpec = polickoLegendy.getRiadokStlpec();
		int hodnota = polickoLegendy.getHodnota();
		GraphicsContext gc = platno.getGraphicsContext2D();
		double x1;
		double y1;
		if (polickoLegendy.isHorna()) {
			final double dolnyOkraj = platno.getHeight() - 3;
			final double lavyOkraj = 3;
			x1 = lavyOkraj + (riadokStlpec * VELKOST_POLICKA);
			y1 = dolnyOkraj - (poradie * VELKOST_POLICKA);
			if (hodnota >= 10) {
				x1 -= 3;
			}
		} else {
			final double hornyOkraj = 12.5;
			final double pravyOkraj = platno.getWidth() - 12;
			x1 = pravyOkraj - (poradie * VELKOST_POLICKA);
			y1 = hornyOkraj + (riadokStlpec * VELKOST_POLICKA);
			if (hodnota >= 10) {
				x1 -= 3;
			}
		}
		gc.fillText(Integer.toString(hodnota), x1, y1);
	}
	
	//TODO spravit test
	// metoda na zistenie maximalneho poctu cisiel v jednom riadku/stlpci legendy
	// (kvoli prisposobeniu okna)
	public int zistiPocetPotrebnych(boolean horna) {
		List<Legenda> legenda;
		if (horna) {
			legenda = legendaDao.getHornaPodlaId(krizovka.getId());
		} else {
			legenda = legendaDao.getLavaPodlaId(krizovka.getId());
		}
		int maxPoradie = Integer.MIN_VALUE;
		for (Legenda leg : legenda) {
			if (leg.getPoradie() > maxPoradie) {
				maxPoradie = leg.getPoradie();
			}
		}
		return maxPoradie + 1;
	}
	//TODO spravit test
	// metoda ktora sa vola pri kazdom kliku na krizovku a overuje ci uz nahodou krizovka nie je vyriesena
	public boolean overRiesenie(List<PolickoHry> polickaHry) {
		Boolean stav;
		boolean pozadovanyStav;
		for (PolickoHry pol : polickaHry) {
			stav = pol.getStav();
			pozadovanyStav = pol.getPozadovanyStav();
			if((stav == null || stav == false) && pozadovanyStav != false) {
				return false;
			}else if ((stav != null && stav == true) && pozadovanyStav != true) {
				return false;
			}
		}
		return true;
	}

	// TODO testy
	public long novyCasRiesenia(Hra hra) {
		long casRiesenia = hra.getCasRiesenia();
		long sekundy = ChronoUnit.SECONDS.between(hra.getPoslednyMedzicas(), LocalDateTime.now(ZoneId.systemDefault()));
		return casRiesenia + sekundy;
	}

	

}
