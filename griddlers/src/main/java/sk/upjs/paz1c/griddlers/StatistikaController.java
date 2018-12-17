package sk.upjs.paz1c.griddlers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.paz1c.griddlers.biznis.StatistikaManager;
import sk.upjs.paz1c.griddlers.entity.Hra;

public class StatistikaController extends Controller {

	@FXML
	private TableView<Hra> statistikaTableView;

	@FXML
	private TableColumn<Hra, String> casTableColumn;

	@FXML
	private TableColumn<Hra, String> nazovKrizovkyTableColumn;

	@FXML
	private TableColumn<Hra, Integer> pocetTahovTableColumn;

	@FXML
	private TableColumn<Hra, String> datumUkonceniaTableColumn;

	@FXML
	private ComboBox<Obdobie> obdobieComboBox;

	@FXML
	private Button spatButton;

	private StatistikaManager manager;

	private ObservableList<Hra> hry;

	public StatistikaController() {
		this.manager = new StatistikaManager();
	}

	@FXML
	void initialize() {

		ObservableList<Obdobie> obdobia = FXCollections.observableArrayList(Obdobie.VSETKY, Obdobie.DEN, Obdobie.TYZDEN,
				Obdobie.MESIAC);
		obdobieComboBox.setItems(obdobia);
		obdobieComboBox.getSelectionModel().selectFirst();
		hry = manager.zmenHry(obdobieComboBox.getSelectionModel().getSelectedItem());
		hry.sort(new HraKoniecComparator());
		obdobieComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Obdobie>() {

			@Override
			public void changed(ObservableValue<? extends Obdobie> observable, Obdobie oldValue, Obdobie newValue) {
				hry = manager.zmenHry(newValue);
				hry.sort(new HraKoniecComparator());
				statistikaTableView.setItems(hry);
			}
		});

		nazovKrizovkyTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, String>("nazovKrizovky"));
		casTableColumn
				.setCellValueFactory(c -> new SimpleStringProperty(manager.formatujCas(c.getValue().getCasRiesenia())));
		pocetTahovTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, Integer>("pocetTahov"));
		datumUkonceniaTableColumn
				.setCellValueFactory(c -> new SimpleStringProperty(Hra.preformatujCas(c.getValue().getKoniec())));

		statistikaTableView.setItems(hry);
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}
}