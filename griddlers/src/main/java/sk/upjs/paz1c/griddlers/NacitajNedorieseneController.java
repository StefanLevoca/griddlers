package sk.upjs.paz1c.griddlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class NacitajNedorieseneController extends Controller{

	@FXML
	private Button spatButton;
    @FXML
    private Button nacitajButton;

    @FXML
    private ListView<?> nedorieseneListView;

    @FXML
    void initialize() {
     
    }
    
    @FXML
	void handleSpatButtonAction(ActionEvent event) {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		novaScena(mainController, "uvodna_obr.fxml", spatButton);
	}
}
