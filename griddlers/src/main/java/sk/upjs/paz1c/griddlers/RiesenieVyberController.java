package sk.upjs.paz1c.griddlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.paz1c.griddlers.biznis.RiesenieVyberManager;
import sk.upjs.paz1c.griddlers.entity.Krizovka;

public class RiesenieVyberController extends Controller {

	@FXML
	private CheckBox zobrazitNazvyCheckBox;

	@FXML
	private ComboBox<Narocnost> narocnostComboBox;

	@FXML
	private Button potvrdButton;

	@FXML
	private Button spatButton;

	@FXML
	private TableView<Krizovka> krizovkyTableView;

	@FXML
	private TableColumn<Krizovka, Long> idTableColumn;

	@FXML
	private TableColumn<Krizovka, Narocnost> narocnostTableColumn;

	@FXML
	private TableColumn<Krizovka, Integer> sirkaTableColumn;

	@FXML
	private TableColumn<Krizovka, Integer> vyskaTableColumn;

	@FXML
	private TableColumn<Krizovka, String> nazovTableColumn;

	private RiesenieVyberManager manager;

	private ObservableList<Krizovka> krizovky;
	private Krizovka krizovka;

	public RiesenieVyberController() {
		this.manager = new RiesenieVyberManager();
	}

	@FXML
	void initialize() {
		ObservableList<Narocnost> narocnosti = FXCollections.observableArrayList(Narocnost.LAHKA, Narocnost.STREDNA,
				Narocnost.TAZKA);
		narocnostComboBox.setItems(narocnosti);
		narocnostComboBox.getSelectionModel().selectFirst();
		krizovky = manager.zmenKrizovky(Narocnost.LAHKA);

		narocnostComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Narocnost>() {

			@Override
			public void changed(ObservableValue<? extends Narocnost> observable, Narocnost oldValue,
					Narocnost newValue) {
				krizovky = manager.zmenKrizovky(newValue);
				krizovkyTableView.setItems(krizovky);
				krizovkyTableView.getSelectionModel().selectFirst();
			}

		});

		krizovkyTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Krizovka>() {

			@Override
			public void changed(ObservableValue<? extends Krizovka> observable, Krizovka oldValue, Krizovka newValue) {
				krizovka = newValue;

			}
		});

		idTableColumn.setCellValueFactory(new PropertyValueFactory<Krizovka, Long>("id"));
		narocnostTableColumn.setCellValueFactory(new PropertyValueFactory<Krizovka, Narocnost>("narocnost"));
		sirkaTableColumn.setCellValueFactory(new PropertyValueFactory<Krizovka, Integer>("sirka"));
		vyskaTableColumn.setCellValueFactory(new PropertyValueFactory<Krizovka, Integer>("vyska"));
		nazovTableColumn.setCellValueFactory(new PropertyValueFactory<Krizovka, String>("nazov"));
		nazovTableColumn.setVisible(false);
		krizovkyTableView.setItems(krizovky);
		krizovkyTableView.getSelectionModel().selectFirst();
		krizovkyTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		krizovka = krizovkyTableView.getSelectionModel().getSelectedItem();
		zobrazitNazvyCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					nazovTableColumn.setVisible(true);
				} else {
					nazovTableColumn.setVisible(false);
				}
			}
		});

		krizovkyTableView.setItems(krizovky);
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}

	@FXML
	void handlePotvrdButtonAction(ActionEvent event) {
		RiesenieController mainController = new RiesenieController(krizovka);
		novaScena(mainController, "ries_riesenie.fxml", potvrdButton);

	}
}
