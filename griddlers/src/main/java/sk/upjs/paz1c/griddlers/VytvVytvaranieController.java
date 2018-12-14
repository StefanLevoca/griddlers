package sk.upjs.paz1c.griddlers;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sk.upjs.paz1c.griddlers.biznis.Platnovac;
import sk.upjs.paz1c.griddlers.biznis.VytvVytvaranieManager;
import sk.upjs.paz1c.griddlers.entity.Krizovka;
import sk.upjs.paz1c.griddlers.entity.Policko;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.KrizovkaDao;

public class VytvVytvaranieController extends Controller {

	private static final int VELKOST_POLICKA = Platnovac.VELKOST_POLICKA;
	private static final int DEFAULT_VELKOST = Platnovac.DEFAULT_VELKOST;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Button ulozButton;

	@FXML
	private Button spatButton;

	@FXML
	private Button resetButton;

	@FXML
	private Canvas krizovkaCanvas;

	private Krizovka krizovka;
	private VytvVytvaranieManager manager;
	private int sirka;
	private int vyska;
	private List<Policko> riesenie;
	private KrizovkaDao krizovkaDao = DaoFactory.INSTANCE.getKrizovkaDao();

	public VytvVytvaranieController(Krizovka krizovka) {
		this.krizovka = krizovka;
		manager = new VytvVytvaranieManager(krizovka);
		sirka = krizovka.getSirka();
		vyska = krizovka.getVyska();
		riesenie = krizovka.getRiesenie();
		manager.vyplnRiesenieFalse();
	}

	@FXML
	void initialize() {
		krizovkaCanvas.setHeight(vyska * VELKOST_POLICKA);
		krizovkaCanvas.setWidth(sirka * VELKOST_POLICKA);
		borderPane.setPrefWidth(borderPane.getPrefWidth() + VELKOST_POLICKA * (sirka - DEFAULT_VELKOST));
		borderPane.setPrefHeight(borderPane.getPrefHeight() + VELKOST_POLICKA * (vyska - DEFAULT_VELKOST));
		manager.vytvorMriezku(krizovkaCanvas);
	}

	@FXML
	void handleUlozButtonAction(ActionEvent event) {
		if (manager.mozeUlozit()) {
			Controller controller = new UvodnaObrazovkaController();
			krizovka.setLegendaH(manager.vytvorLegenduH());
			krizovka.setLegendaL(manager.vytvorLegenduL());
			krizovkaDao.ulozit(krizovka);
			novaScena(controller, "uvodna_obr.fxml", ulozButton);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Upozornenie");
			alert.setHeaderText("Upozornenie");
			alert.setContentText("Nesprávne vyplnenie krížovky");
			alert.showAndWait();
		}
	}

	@FXML
	void handleResetButtonAction(ActionEvent event) {
		manager.canvasReset(krizovkaCanvas);
		manager.vyplnRiesenieFalse();
	}
	
	

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		Controller mainController = new VytvZadanieVstupovController();
		novaScena(mainController, "vytv_zadanie_vst.fxml", spatButton);
	}
	
	@FXML
	void handleCanvasOnDraggedAction(MouseEvent event) {
		canvasOnMousePressed(event);
	}

	@FXML
	void canvasOnMousePressed(MouseEvent event) {
		Policko policko = manager.spracujKlik(event, krizovkaCanvas);
		riesenie.remove(policko); // pretoze equals testuje surX a surY
		riesenie.add(policko);
	}
}
