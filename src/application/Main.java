package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 400, 370);
			stage.setTitle("Login Form");


//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/UserView.fxml"));
//			Scene scene = new Scene(fxmlLoader.load(), 455, 435);

			scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
			stage.setTitle("Login Form");
			stage.setScene(scene);
			stage.show();
			//			BorderPane root = new BorderPane();
			//			Scene scene = new Scene(root, 400, 400);
			//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//			primaryStage.setScene(scene);
			//			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
