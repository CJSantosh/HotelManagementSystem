package controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CommonController {

	String sUsername;
	String sPassword;

	public void SwitchBtwUI(ActionEvent event, String Username, String Password)
	{
		sUsername = Username;
		sPassword = Password;
		String newscene="";
		int width = 0;
		int height = 0;
		AdminController admCtrl = null;
		UserController userCtrl = null;

		switch(((JFXButton)(event.getSource())).getId())
		{
		case "btnBookRoom":
			newscene="/views/UserBookRoom.fxml";	
			width = 860;
			height = 600;
			break;
		case "btnManageBooking":
			newscene="/views/UserManageBooking.fxml";	
			width = 1020;
			height = 600;
			break;
		case "btnManageProfile":
			newscene="/views/UserManageProfile.fxml";
			width = 940;
			height = 700;
			break;
		case "btnCancel":
			newscene="/views/UserView.fxml";
			width = 455;
			height = 435;
			break;
		case "btnViewBookings":
			newscene="/views/AdminViewBooking.fxml";	
			width = 1030;
			height = 600;
			break;
		case "btnManageRoom":
			newscene="/views/AdminManageRoom.fxml";	
			width = 1020;
			height = 600;
			break;
		case "btnAdminManageProfile":
			newscene="/views/AdminManageProfile.fxml";
			width = 940;
			height = 700;
			break;
		case "btnAdminCancel":
			newscene="/views/AdminView.fxml";
			width = 455;
			height = 435;
			break;
		}

		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(newscene));
			Parent root = fxmlLoader.load();

			switch(((JFXButton)(event.getSource())).getId())
			{
			case "btnViewBookings":
				admCtrl = (AdminController)fxmlLoader.getController();
				admCtrl.displayBookingsData(Username, Password);
				break;
			case "btnManageRoom":
				admCtrl = (AdminController)fxmlLoader.getController();
				admCtrl.displayRoomData(Username, Password);
				break;
			case "btnAdminManageProfile":
				admCtrl = (AdminController)fxmlLoader.getController();
				admCtrl.displayProfileData(sUsername, sPassword);
				break;
			case "btnBookRoom":
				userCtrl = (UserController)fxmlLoader.getController();
				userCtrl.displayBookRoomData(sUsername, sPassword);
				break;
			case "btnManageBooking":
				userCtrl = (UserController)fxmlLoader.getController();
				userCtrl.displayBoookingData(sUsername, sPassword);
				break;
			case "btnManageProfile":
				userCtrl = (UserController)fxmlLoader.getController();
				userCtrl.displayProfileData(sUsername, sPassword);
				break;
			}

			Node source = (Node) event.getSource();
			Stage stage = (Stage)source.getScene().getWindow();
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(getClass().getResource("/application/Application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {

		}
	}
}
