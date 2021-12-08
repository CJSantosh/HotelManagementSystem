package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.LoginModel;

public class LoginController {


	@FXML
	private TextField txtLoginUser;
	@FXML
	private PasswordField pwdLoginPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegister;
	@FXML
	private RadioButton rbAdmin;
	@FXML
	private RadioButton rbManager;
	@FXML
	private RadioButton rbUser;
	@FXML
	private ToggleGroup UserType;
	@FXML
	private Label lblError;

	private Stage stage;
	private Scene scene;
	private Parent root;

	private LoginModel model;

	public LoginController() 
	{ 
		model = new LoginModel(); 
	}
	
	@FXML
	public void onLogin(ActionEvent event)
	{
		String username = this.txtLoginUser.getText();
		String password = this.pwdLoginPassword.getText();
		lblError.setText("");

		// Validations
		if (username == null || username.trim().equals("") && (password == null || 
				password.trim().equals(""))) 
		{
			lblError.setText("User name / Password Cannot be empty");
			return;
		}

		// authentication check
		checkCredentials(username, password, event);
		
	}

	public void checkCredentials(String username, String password, ActionEvent event) {
		
		String sUserType = ((RadioButton) UserType.getSelectedToggle()).getText();

		
		Boolean isValid = model.getCredentials(username, password, sUserType);


		if (!isValid) 
		{
			lblError.setText(sUserType + " does not exist!");
			return;
		}
		try 
		{

			String newscene="";
			int width = 0;
			int height = 0;

			if(sUserType.equals("Admin"))
			{
				newscene="/views/AdminView.fxml";
				width = 455;
				height = 435;
			}
			else if(sUserType.equals("User"))
			{
				newscene="/views/UserView.fxml";
				width = 455;
				height = 435;
			}

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(newscene));
			Parent root = fxmlLoader.load();
			if(sUserType.equals("Admin"))
			{
				AdminController admCtrl = ((AdminController)fxmlLoader.getController());
				admCtrl.sUsername = username;
				admCtrl.sPassword = password;
			}
			else if(sUserType.equals("User"))
			{
				UserController userCtrl = ((UserController)fxmlLoader.getController());
				userCtrl.sUsername = username;
				userCtrl.sPassword = password;
			}
			
			Node source = (Node) event.getSource();
			Stage stage = (Stage)source.getScene().getWindow();
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(getClass().getResource("/application/Application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	@FXML
	private void onRegisterClick(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Register.fxml"));
		root = fxmlLoader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, 400, 700);
		stage.setScene(scene);
		stage.show();
	}
}
