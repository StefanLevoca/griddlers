package sk.upjs.paz1c.griddlers;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sk.upjs.paz1c.griddlers.biznis.RiesenieManager;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;
import sk.upjs.paz1c.griddlers.persistentna.LegendaDao;
import sk.upjs.paz1c.griddlers.persistentna.PolickoDao;

public class RiesenieController extends Controller {

	private static final int VELKOST_POLICKA = VytvVytvaranieController.VELKOST_POLICKA;
	private static final int DEFAULT_VELKOST = VytvVytvaranieController.DEFAULT_VELKOST;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button ulozButton;

	@FXML
	private Button spatButton;

	@FXML
	private Canvas krizovkaCanvas;

	@FXML
	private Canvas legendaLCanvas;

	@FXML
	private Canvas legendaHCanvas;

	private Krizovka krizovka;
	private Hra hra;
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();
	private PolickoDao polickoDao = DaoFactory.INSTANCE.getPolickoDao();
	private LegendaDao legendaDao = DaoFactory.INSTANCE.getLegendaDao();
	private RiesenieManager manager;

	public RiesenieController(Krizovka krizovka) {
		this.hra = new Hra();
		this.krizovka = krizovka;
		this.krizovka.setRiesenie(polickoDao.getPodlaId(krizovka.getId()));
		this.krizovka.setLegendaH(legendaDao.getHornaPodlaId(krizovka.getId()));
		this.krizovka.setLegendaL(legendaDao.getLavaPodlaId(krizovka.getId()));
		manager = new RiesenieManager(krizovka);
		hra.setPolickaHry(manager.inicializujPolickaHry());
	}

	@FXML
	void initialize() {
		int sirka = krizovka.getSirka();
		int vyska = krizovka.getVyska();
		anchorPane.setPrefHeight(anchorPane.getPrefHeight() + (vyska - DEFAULT_VELKOST) * VELKOST_POLICKA);
		anchorPane.setPrefWidth(anchorPane.getPrefWidth() + (sirka - DEFAULT_VELKOST) * VELKOST_POLICKA);
		krizovkaCanvas.setHeight(vyska * VELKOST_POLICKA);
		krizovkaCanvas.setWidth(sirka * VELKOST_POLICKA);
		legendaLCanvas.setHeight(legendaLCanvas.getHeight() + (vyska - DEFAULT_VELKOST) * VELKOST_POLICKA);
		legendaHCanvas.setWidth(legendaHCanvas.getWidth() + (sirka - DEFAULT_VELKOST) * VELKOST_POLICKA);
		manager.vytvorMriezku(krizovkaCanvas);
		manager.vytvorMriezku(legendaHCanvas, Color.rgb(0, 0, 0, 0.5));
		manager.vytvorMriezku(legendaLCanvas, Color.rgb(0, 0, 0, 0.5));

	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		Controller controller = new RiesenieVyberController();
		novaScena(controller, "ries_vyber_krizovky.fxml", spatButton);
	}

	@FXML
	void handleCanvasOnPressedAction(MouseEvent event) {
		List<PolickoHry> polickaHry = hra.getPolickaHry();
		PolickoHry policko = manager.spracujKlik(event, krizovkaCanvas);
	}

}