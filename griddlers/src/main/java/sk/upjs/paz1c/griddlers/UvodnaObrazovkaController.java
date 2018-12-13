package sk.upjs.paz1c.griddlers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UvodnaObrazovkaController extends Controller {

	@FXML
	private Button statistikaButton;

	@FXML
	private Button novaButton;

	@FXML
	private Button koniecButton;

	@FXML
	private Button pokracovatButton;

	@FXML
	private Button vlastnaButton;

	@FXML
	public void initialize() {

	}

	@FXML
	void handleVlastnaButtonAction(ActionEvent event) {
		VytvZadanieVstupovController controller = new VytvZadanieVstupovController();
		novaScena(controller, "vytv_zadanie_vst.fxml", vlastnaButton);
	}

	@FXML
	void handlePokracovatButtonAction(ActionEvent event) {
		NacitajNedorieseneController controller = new NacitajNedorieseneController();
		novaScena(controller, "nacit_nedories_krizovky.fxml", pokracovatButton);
	}

	@FXML
	void handleKoniecButtonAction(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void handleNovaButtonAction(ActionEvent event) {
		RiesenieVyberController controller = new RiesenieVyberController();
		novaScena(controller, "ries_vyber_krizovky.fxml", novaButton);
	}

	@FXML
	void handleStatistikaButtonAction(ActionEvent event) {
		StatistikaController controller = new StatistikaController();
		novaScena(controller, "statistika.fxml", statistikaButton);
	}
}
