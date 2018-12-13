package sk.upjs.paz1c.griddlers;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import sk.upjs.paz1c.griddlers.entity.Hra;
import sk.upjs.paz1c.griddlers.persistentna.DaoFactory;
import sk.upjs.paz1c.griddlers.persistentna.HraDao;

public class NacitajNedorieseneController extends Controller {

	@FXML
	private Button spatButton;
	@FXML
	private Button nacitajButton;

	@FXML
	private ListView<Hra> nedorieseneListView;

	private HraDao hraDao = DaoFactory.INSTANCE.getHraDao();
	private Hra hra;

	@FXML
	void initialize() {
		List<Hra> hry = hraDao.getVsetky();
		List<Hra> naVymazanie = new ArrayList<>();
		for (Hra hra : hry) {
			if (hra.isUkoncena()) {
				naVymazanie.add(hra);
			}
		}
		hry.removeAll(naVymazanie);
		nedorieseneListView.setItems(FXCollections.observableArrayList(hry));
		nedorieseneListView.getSelectionModel().selectFirst();
		hra = nedorieseneListView.getSelectionModel().getSelectedItem();
		nedorieseneListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hra>() {

			@Override
			public void changed(ObservableValue<? extends Hra> observable, Hra oldValue, Hra newValue) {
				hra = newValue;
			}
		});
	}

	@FXML
	void handleSpatButtonAction(ActionEvent event) {
		Controller mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}

	@FXML
	void handleNacitajButtonAction(ActionEvent event) {
		Controller mainController = new RiesenieController(hra);
		novaScena(mainController, "ries_riesenie.fxml", nacitajButton);
	}
}
