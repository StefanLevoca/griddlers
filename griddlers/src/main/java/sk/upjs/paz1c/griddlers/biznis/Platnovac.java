package sk.upjs.paz1c.griddlers.biznis;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sk.upjs.paz1c.griddlers.VytvVytvaranieController;
import sk.upjs.paz1c.griddlers.entity.Krizovka;

public abstract class Platnovac {

	private static final int VELKOST_POLICKA = VytvVytvaranieController.VELKOST_POLICKA;

	private Krizovka krizovka;

	public Platnovac(Krizovka krizovka) {
		this.krizovka = krizovka;
	}
	
	// samopopisujuci nazov
	public void vytvorMriezku(Canvas platno) {
		int sirka = krizovka.getSirka() * VELKOST_POLICKA;
		int vyska = krizovka.getVyska() * VELKOST_POLICKA;
		GraphicsContext gc = platno.getGraphicsContext2D();
		double x1;
		for (int i = 0; i <= (sirka / VELKOST_POLICKA); i++) {
			x1 = i * VELKOST_POLICKA + 0.5;
			if (i % 5 == 0) {
				x1 -= 0.5;
			}
			gc.moveTo(x1, 0);
			gc.lineTo(x1, vyska);
			gc.stroke();
		}
		double y1;
		for (int i = 0; i <= (vyska / VELKOST_POLICKA); i++) {
			y1 = i * VELKOST_POLICKA + 0.5;
			if (i % 5 == 0) {
				y1 -= 0.2;
			}
			gc.moveTo(0, y1);
			gc.lineTo(sirka, y1);
			gc.stroke();
		}
	}
	
	// metoda na vykreslenie stvorca
		protected void kresliStvorec(GraphicsContext gc, double x, double y, Color farba) {
			gc.setFill(farba);
			gc.beginPath();
			gc.moveTo(x, y);
			gc.lineTo(x + VELKOST_POLICKA, y);
			gc.lineTo(x + VELKOST_POLICKA, y + VELKOST_POLICKA);
			gc.lineTo(x, y + VELKOST_POLICKA);
			gc.lineTo(x, y);
			gc.closePath();
			gc.fill();
			if (gc.getFill().equals(Color.WHITE)) {
				gc.setLineWidth(1.0);
				gc.setFill(Color.BLACK);
				gc.moveTo(x, y + 0.5);
				gc.lineTo(x + VELKOST_POLICKA, y + 0.5);
				gc.lineTo(x + VELKOST_POLICKA, y + VELKOST_POLICKA);
				gc.lineTo(x, y + VELKOST_POLICKA);
				gc.lineTo(x, y);
				gc.stroke();
				// TODO kreslit ostre ciary
			}
		}

		// metoda na vykreslenie bodky
		protected void kresliBodku(GraphicsContext gc, double x, double y) {
			double surX = x + (VELKOST_POLICKA / 2);
			double surY = y + (VELKOST_POLICKA / 2);
			kresliStvorec(gc, x, y, Color.WHITE);
			gc.setFill(Color.BLACK);
			gc.fillOval(surX - 2, surY - 2, 4, 4);
		}
}
