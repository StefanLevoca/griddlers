package sk.upjs.paz1c.griddlers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sk.upjs.paz1c.griddlers.biznis.Platnovac;
import sk.upjs.paz1c.griddlers.biznis.RiesenieManager;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.PolickoHry;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;
import sk.upjs.paz1c.griddlers.persistentna.PolickoHryDao;

public class RiesenieController extends Controller {

	private static final int VELKOST_POLICKA = Platnovac.VELKOST_POLICKA;
	private static final int DEFAULT_VELKOST = Platnovac.DEFAULT_VELKOST;

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
	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();
	private PolickoHryDao polickoHryDao = DaoFactory.INSTANCE.getPolickoHryDao();
	private RiesenieManager manager;

	public RiesenieController(Krizovka krizovka) {
		this.krizovka = krizovka;
		this.hra = new Hra();
		hra.setKrizovkaId(krizovka.getId());
		hra.setZaciatok(LocalDateTime.now(ZoneId.systemDefault()));
		hra.setMedzicas(LocalDateTime.now(ZoneId.systemDefault()));
		manager = new RiesenieManager(krizovka);
		hra.setPolickaHry(manager.inicializujPolickaHry());
	}

	public RiesenieController(Hra hra) {
		this.hra = hra;
		this.hra.setPolickaHry(polickoHryDao.getPodlaHraId(hra.getId()));
		this.hra.setMedzicas(LocalDateTime.now(ZoneId.systemDefault()));
		this.krizovka = hraDao.getKrizovkaPodlaHraId(hra.getId());
		manager = new RiesenieManager(krizovka);
	}

	@FXML
	void initialize() {
		int sirka = krizovka.getSirka();
		int vyska = krizovka.getVyska();
		anchorPane.setPrefHeight(anchorPane.getPrefHeight() + (vyska - DEFAULT_VELKOST) * VELKOST_POLICKA);
		anchorPane.setPrefWidth(anchorPane.getPrefWidth() + (sirka - DEFAULT_VELKOST) * VELKOST_POLICKA);
		krizovkaCanvas.setHeight(vyska * VELKOST_POLICKA);
		krizovkaCanvas.setWidth(sirka * VELKOST_POLICKA);
		manager.kresliBielePlatno(krizovkaCanvas);
		int pocetPotrebnychL = manager.zistiPocetPotrebnych(false);
		int pocetPotrebnychH = manager.zistiPocetPotrebnych(true);
		legendaLCanvas.setHeight(legendaLCanvas.getHeight() + (vyska - DEFAULT_VELKOST) * VELKOST_POLICKA);
		legendaLCanvas.setWidth(pocetPotrebnychL * VELKOST_POLICKA);
		legendaHCanvas.setWidth(legendaHCanvas.getWidth() + (sirka - DEFAULT_VELKOST) * VELKOST_POLICKA);
		legendaHCanvas.setHeight(pocetPotrebnychH * VELKOST_POLICKA);
		manager.kresliBielePlatno(legendaHCanvas);
		manager.kresliBielePlatno(legendaLCanvas);
		manager.vytvorMriezku(krizovkaCanvas);
		manager.vytvorMriezku(legendaHCanvas, Color.rgb(0, 0, 0, 0.5));
		manager.vytvorMriezku(legendaLCanvas, Color.rgb(0, 0, 0, 0.5));
		manager.zobrazLegendu(legendaHCanvas, true);
		manager.zobrazLegendu(legendaLCanvas, false);
		if (hra.getId() != null) {
			manager.obnovPlatno(krizovkaCanvas, hra);
		}
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		Controller controller = new RiesenieVyberController();
		novaScena(controller, "ries_vyber_krizovky.fxml", spatButton);
	}

	@FXML
	void handleCanvasOnDraggedAction(MouseEvent event) {
		handleCanvasOnPressedAction(event);
	}

	@FXML
	void handleCanvasOnPressedAction(MouseEvent event) {
		List<PolickoHry> polickaHry = hra.getPolickaHry();
		manager.zvysPocetTahov(event, hra);
		PolickoHry policko = manager.spracujKlik(event, krizovkaCanvas);
			
		for (PolickoHry pol : polickaHry) {
			if (pol.equals(policko)) {
				pol.setStav(policko.getStav());
			}
		}
		if (manager.overRiesenie(polickaHry)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Výhra");
			alert.setHeaderText("Gratulujem!");
			alert.setContentText("Gratulujem, vyriešil si krížovku!");
			alert.showAndWait();
			krizovkaCanvas.setDisable(true);
			ulozButton.setDisable(true);
			hra.setUkoncena(true);
			long casRiesenia = manager.novyCasRiesenia(hra);
			hra.setCasRiesenia(casRiesenia);
			hra.setKoniec(LocalDateTime.now(ZoneId.systemDefault()));
			hra.setId(hraDao.ulozit(hra).getId());
			polickoHryDao.vymazat(hra.getId());
		}

	}

	@FXML
	void handleUlozButtonAction(ActionEvent event) {
		long casRiesenia = manager.novyCasRiesenia(hra);
		hra.setCasRiesenia(casRiesenia);
		hra.setId(hraDao.ulozit(hra).getId());
		for (PolickoHry pol : hra.getPolickaHry()) {
			pol.setIdHry(hra.getId());
		}
		polickoHryDao.ulozit(hra.getPolickaHry());
		hra.setPolickaHry(polickoHryDao.getPodlaHraId(hra.getId()));
	}

}