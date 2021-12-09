
//Controller which are used to register user and admin details and connect to database

package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import dao.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegisterController {

	ObservableList<String> lstUserType = FXCollections.observableArrayList("Admin","User");

	@FXML
	private TextField txtFName;
	@FXML
	private TextField txtLName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;
	@FXML
	private PasswordField pwdPassword;
	@FXML
	private PasswordField pwdConfirmPassword;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;
	@FXML
	private ComboBox<String> cbUserType;
	@FXML
	private DatePicker dpDatePicker;
	@FXML
	private ToggleGroup Gender;
	@FXML
	private Label lblError;

	private Stage stage;
	private Scene scene;
	private Parent root;

	DBConnect dbConnect = null;
	Statement sStatement = null;
	@FXML
	private void initialize()
	{
		cbUserType.setItems(lstUserType);
		dbConnect = new DBConnect();
	}
	@FXML
	private void onRegisterClick(ActionEvent event) throws IOException
	{
		try
		{
			String sFName = txtFName.getText();
			String sLName = txtLName.getText();
			String sEmail = txtEmail.getText();
			String sPhone = txtPhone.getText();
			String spwdPassword = pwdPassword.getText();
			String spwdConfirmPassword = pwdConfirmPassword.getText();
			String sUserType = cbUserType.getValue();
			String sGender = ((RadioButton) Gender.getSelectedToggle()).getText();
			LocalDate sDOB =  dpDatePicker.getValue(); 
			
			if (sFName == null || sFName.trim().equals("")) {
				lblError.setText("Name Cannot be empty or spaces");
				return;
			}
			
			if (sEmail == null || sEmail.trim().equals("")) {
				lblError.setText("Email Cannot be empty or spaces");
				return;
			}
			if (sPhone == null || sPhone.trim().equals("")) {
				lblError.setText("Phone Cannot be empty or spaces");
				return;
			}
			if ((spwdPassword == null || spwdPassword.trim().equals("")) &&
					(spwdConfirmPassword == null || spwdConfirmPassword.trim().equals(""))) 
			{
				lblError.setText("Password Cannot be empty or spaces");
				return;
			}
			if (!spwdPassword.equals(spwdConfirmPassword)) 
			{
				lblError.setText("Passwords doesn't match");
				return;
			}

			sStatement = dbConnect.getConnection().createStatement();

			String sql = "INSERT INTO hms_login (FName, LName, Email, Phone, Password, UserType, DOB, Gender) VALUES ('"
					+ sFName + "','" + sLName + "','" + sEmail + "','" + sPhone + "','" 
					+ spwdPassword + "','" + sUserType + "','" + sDOB + "','" + sGender + "');";

			int con = sStatement.executeUpdate(sql);
			if (con > 0) 
			{
				callLogin(event);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	@FXML
	private void onCancelClick(ActionEvent event) throws IOException
	{
		callLogin(event);
	}

	private void callLogin(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
		root = fxmlLoader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
