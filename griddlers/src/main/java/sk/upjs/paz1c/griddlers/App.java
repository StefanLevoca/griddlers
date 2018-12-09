package sk.upjs.paz1c.griddlers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
       launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		UvodnaObrazovkaController mainController = new UvodnaObrazovkaController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("uvodna_obr.fxml"));
		fxmlLoader.setController(mainController);
		Parent rootPane = fxmlLoader.load();
		primaryStage.setResizable(false);
		
		Scene scene = new Scene(rootPane);
		scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		primaryStage.setTitle("Griddlers");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
