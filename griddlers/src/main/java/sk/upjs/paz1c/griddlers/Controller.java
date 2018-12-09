package sk.upjs.paz1c.griddlers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
	
	public void novaScena(Controller controller, String fxmlSubor, Button komponent) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlSubor));
			fxmlLoader.setController(controller);
			Parent rootPane;
			rootPane = fxmlLoader.load();
			Scene scene = new Scene(rootPane);
			scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

			Stage stage = (Stage) komponent.getScene().getWindow();
			stage.setResizable(false);
			stage.setTitle("Griddlers");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
