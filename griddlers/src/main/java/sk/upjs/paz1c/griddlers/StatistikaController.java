package sk.upjs.paz1c.griddlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
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
	private TableColumn<Hra, LocalDateTime> datumUkonceniaTableColumn;

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
		spatButton.getStyleClass().setAll("btn", "btn-danger");

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

		nazovKrizovkyTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, String>("nazovKrizovky"));
		//casTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, LocalDateTime>("casRiesenia"));
		
		TableColumn<Hra, LocalDateTime> casTableColumn = new TableColumn<>("casRiesenia");
		casTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, LocalDateTime>("casRiesenia"));
		casTableColumn.setCellFactory((TableColumn<Hra, LocalDateTime> column) -> {
			return new TableCell<Hra, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.format(DateTimeFormatter.ofPattern("HH.mm.ss")));
					}
				}
			};
		});

		pocetTahovTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, Integer>("pocetTahov"));
		// datumUkonceniaTableColumn.setCellValueFactory(new PropertyValueFactory<Hra,
		// LocalDateTime>("koniec"));

		TableColumn<Hra, LocalDateTime> datumUkonceniaTableColumn = new TableColumn<>("koniec");
		datumUkonceniaTableColumn.setCellValueFactory(new PropertyValueFactory<Hra, LocalDateTime>("koniec"));
		datumUkonceniaTableColumn.setCellFactory((TableColumn<Hra, LocalDateTime> column) -> {
			return new TableCell<Hra, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
					}
				}
			};
		});

		statistikaTableView.setItems(hry);
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}
}