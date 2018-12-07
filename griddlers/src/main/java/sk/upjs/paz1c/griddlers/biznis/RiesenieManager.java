package sk.upjs.paz1c.griddlers.biznis;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
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
		super(krizovka);
		this.krizovka = krizovka;
		this.legendaDao = DaoFactory.INSTANCE.getLegendaDao();
	}

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

	public PolickoHry spracujKlik(MouseEvent event, Canvas platno) {
		int x1 = (int) (event.getX() / VELKOST_POLICKA);
		int y1 = (int) (event.getY() / VELKOST_POLICKA);
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			kresliStvorec(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA, Color.BLACK);
			return new PolickoHry(true, x1, y1);
		} else if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			kresliStvorec(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA, Color.WHITE);
			return new PolickoHry(false, x1, y1);
		} else if (event.getButton().equals(MouseButton.SECONDARY)) {
			kresliBodku(platno.getGraphicsContext2D(), x1 * VELKOST_POLICKA, y1 * VELKOST_POLICKA);
			return new PolickoHry(false, x1, y1);
		}
		return null;

	}

	// TODO napisat test
	public List<PolickoHry> inicializujPolickaHry() {
		List<PolickoHry> polickaHry = new ArrayList<>();
		List<Policko> riesenie = krizovka.getRiesenie();
		PolickoHry polickoHry;
		for (Policko pol : riesenie) {
			polickoHry = new PolickoHry(false, pol.getSurX(), pol.getSurY(), pol.getStav());
			polickaHry.add(polickoHry);
		}
		return polickaHry;
	}

	public void zobrazLegendu(Canvas platno, boolean horna) {
		List<Legenda> legenda;
		if (horna) {
			legenda = legendaDao.getHornaPodlaId(krizovka.getId());
		} else {
			legenda = legendaDao.getLavaPodlaId(krizovka.getId());
		}
		for (Legenda polickoLegendy : legenda) {
			vykresli(platno, polickoLegendy);
		}

	}

	private void vykresli(Canvas platno, Legenda polickoLegendy) {
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

}
