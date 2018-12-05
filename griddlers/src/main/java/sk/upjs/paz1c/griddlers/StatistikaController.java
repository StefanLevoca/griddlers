package sk.upjs.paz1c.griddlers;

import java.time.LocalDateTime;

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
import sk.upjs.paz1c.griddlers.biznis.StatistikaVyberManager;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.entity.Obdobie;

public class StatistikaController extends Controller {

	@FXML
	private TableView<Hra> statistikaTableView;

	@FXML
	private TableColumn<Hra, LocalDateTime> casTableColumn;

	@FXML
	private TableColumn<Hra, String> nazovKrizovkyTableColumn;

	@FXML
	private TableColumn<Hra, Integer> pocetTahovTableColumn;

	@FXML
	private ComboBox<Obdobie> obdobieComboBox;

	@FXML
	private Button spatButton;

	private StatistikaVyberManager manager;

	private ObservableList<Hra> hry;

	public StatistikaController() {
		this.manager = new StatistikaVyberManager();
	}

	@FXML
	void initialize() {
		ObservableList<Obdobie> obdobia = FXCollections.observableArrayList(Obdobie.VSETKY, Obdobie.DEN, Obdobie.TYZDEN,
				Obdobie.MESIAC);
		obdobieComboBox.setItems(obdobia);
		obdobieComboBox.getSelectionModel().selectFirst();
		hry = manager.zmenHry(obdobieComboBox.getSelectionModel().getSelectedItem());
		obdobieComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Obdobie>() {

			@Override
			public void changed(ObservableValue<? extends Obdobie> observable, Obdobie oldValue, Obdobie newValue) {
				hry = manager.zmenHry(newValue);
				statistikaTableView.setItems(hry);
			}
		});

		nazovKrizovkyTableColumn.setCellValueFactory(new PropertyValueFactory("nazovKrizovky"));
		casTableColumn.setCellValueFactory(new PropertyValueFactory("casRiesenia"));
		pocetTahovTableColumn.setCellValueFactory(new PropertyValueFactory("pocetTahov"));

		statistikaTableView.setItems(hry);
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}
}