package sk.upjs.paz1c.griddlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import sk.upjs.paz1c.griddlers.KrizovkaFxModel;
import sk.upjs.paz1c.griddlers.entity.Narocnost;

public class VytvZadanieVstupovController extends Controller{
	
	
	@FXML
	private ComboBox<Integer> sirkaComboBox;
	
	@FXML
	private ComboBox<Integer> vyskaComboBox;

	@FXML
	private TextField nazovTextField;

	@FXML
	private ComboBox<Narocnost> narocnostComboBox;

	@FXML
	private Button spatButton;
	
	@FXML
	private Button zacatButton;
	
	private KrizovkaFxModel krizovkaModel = new KrizovkaFxModel();

	@FXML
	void initialize() {
		spatButton.getStyleClass().setAll("btn","btn-danger");
		zacatButton.getStyleClass().setAll("btn","btn-primary");
		
		ObservableList<Integer> sirkaVyska = FXCollections.observableArrayList(15,20,25,30,35,40,45,50,55,60);
		sirkaComboBox.setItems(sirkaVyska);
		vyskaComboBox.setItems(sirkaVyska);		
		sirkaComboBox.valueProperty().bindBidirectional(krizovkaModel.getSirkaProperty());
		vyskaComboBox.valueProperty().bindBidirectional(krizovkaModel.getVyskaProperty());
		nazovTextField.textProperty().bindBidirectional(krizovkaModel.getNazovProperty());
		narocnostComboBox.valueProperty().bindBidirectional(krizovkaModel.getNarocnostProperty());
		ObservableList<Narocnost> narocnosti = FXCollections.observableArrayList(Narocnost.LAHKA, Narocnost.STREDNA,
				Narocnost.TAZKA);
		narocnostComboBox.setItems(narocnosti);
		narocnostComboBox.getSelectionModel().selectFirst();
		vyskaComboBox.getSelectionModel().selectFirst();
		sirkaComboBox.getSelectionModel().selectFirst();
	}

	@FXML
	void handleZacatButtonAction(ActionEvent event) {
		VytvVytvaranieController controller = new VytvVytvaranieController(krizovkaModel.getKrizovka());
		novaScena(controller, "vytv_vytvaranie.fxml", zacatButton);
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}
}
