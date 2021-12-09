//******************************************
//Hotel Management system
//Project created by
//Santosh Kumar Chattanahalli Jagadeesh
//Deekshitha Somanahalli Umesh
//Skanditha Satish
//ITMD 510
//******************************************
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Main method

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 400, 370);

			scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
			stage.setTitle("");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
