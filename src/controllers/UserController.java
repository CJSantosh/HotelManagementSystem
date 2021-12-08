package controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class UserController extends CommonController{

	//User Book Room controls
	@FXML
	private JFXComboBox<String> cbmGuest;
	@FXML
	private JFXComboBox<String> cbmRooms;
	@FXML
	private JFXComboBox<String> cbmRoomType;
	@FXML
	private JFXDatePicker dpDateFrom;
	@FXML
	private JFXDatePicker dpDateTo;
	@FXML
	private JFXCheckBox cbSpa;
	@FXML
	private JFXCheckBox cbLounge;
	@FXML
	private JFXCheckBox cbPool;
	@FXML
	private Label lblTotal;
	@FXML
	private JFXButton btnReserve;
	@FXML
	private JFXButton btnCancel;

	//User Manage Bookings controls
	@FXML
	private TableView<String> tvMyBookings;

	//User Manage Profile controls
	@FXML
	private TextField txtFName;
	@FXML
	private TextField txtLName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private PasswordField pfConfirmPassword;
	@FXML
	private JFXDatePicker dpDoB;
	@FXML
	private JFXRadioButton rbMale;
	@FXML
	private JFXRadioButton rbFemale;
	@FXML
	private JFXButton btnUpdate;
	
	String sUsername;
	String sPassword;
	
	@FXML
	public void SwitchUI(ActionEvent event)
	{
		SwitchBtwUI(event, sUsername, sPassword);
	}
	
	
	public void displayProfileData(String Username, String Password)
	{
		this.sUsername = Username;
		this.sPassword = Password;
	}
	public void displayBookRoomData(String Username, String Password)
	{
		this.sUsername = Username;
		this.sPassword = Password;
	}
	public void displayBoookingData(String Username, String Password)
	{
		this.sUsername = Username;
		this.sPassword = Password;
	}
	public void logOut(ActionEvent event)
	{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
       
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/logOut.fxml"));
        	Parent root = fxmlLoader.load();
        	Stage popup = new Stage();
        	Scene scene = new Scene(root);
        	scene.getStylesheets().add(getClass().getResource("/application/Application.css").toExternalForm());

        	popup.initOwner(stage);
        	popup.setScene(scene);
        	popup.initStyle(StageStyle.UNDECORATED);
        	popup.initModality(Modality.APPLICATION_MODAL);
        	popup.showAndWait();

        } catch (IOException ex) {
        	System.out.println("Error in switching stages logout btn");
        	ex.printStackTrace();
        }
	}
	
	public void CloseStage(ActionEvent event)
	{
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}
