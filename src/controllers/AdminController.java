package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;

import dao.DBConnect;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.AdminModel;
import models.RoomModel;

public class AdminController extends CommonController{


	//User View Bookings controls
	@FXML
	private TableView<String> tvBookings;
	//User Manage Rooms controls
	@FXML
	private TableView<RoomModel> tvManageRooms;
	@FXML
	private TableColumn<RoomModel, String> tcRoomNo;
	@FXML
	private TableColumn<RoomModel, String> tcRoomType;
	@FXML
	private TableColumn<RoomModel, String> tcPrice;
	@FXML
	private TableColumn<RoomModel, String> tcStatus;

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
	private PasswordField pwdPassword;
	@FXML
	private PasswordField pwdConfirmPassword;
	@FXML
	private JFXDatePicker dpDoB;
	@FXML
	private JFXRadioButton rbMale;
	@FXML
	private JFXRadioButton rbFemale;
	@FXML
	private ToggleGroup Gender;
	@FXML
	private JFXButton btnUpdate;
	@FXML
	private Button testbtn;
	@FXML
	private Label lblError;
	@FXML
	private JFXComboBox cbmRoomNo;
	@FXML
	private JFXComboBox cbmRoomStatus;

	DBConnect dbConnect = null;
	Statement sStatement = null;

	String sUsername;
	String sPassword;
	ObservableList<RoomModel> lstRooms;

	AdminModel admModel = null;
	RoomModel rmModel = null;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public AdminController()
	{
		dbConnect = new DBConnect();
		admModel = new AdminModel();
		rmModel = new RoomModel();
	}

	public void SwitchUI(ActionEvent event)
	{
		SwitchBtwUI(event, sUsername, sPassword);
	}

	public void displayProfileData(String username, String password) 
	{
		this.sUsername = username;
		this.sPassword = password;
		admModel.GetUserDetails(sUsername, sPassword);
		this.txtFName.setText(admModel.getFName());
		this.txtLName.setText(admModel.getLName());
		this.txtEmail.setText(admModel.getEmail());
		this.txtPhone.setText(admModel.getPhone());
		this.pwdPassword.setText(admModel.getPassword());
		this.pwdConfirmPassword.setText(admModel.getPassword());
		this.dpDoB.setValue(admModel.getDOB());
		if(admModel.getGender().equals("Male"))
			this.Gender.selectToggle(rbMale);
		else
			this.Gender.selectToggle(rbFemale);
	}

	public void onUpdateProfile(ActionEvent event) throws IOException
	{
		try
		{
			String sFName = txtFName.getText();
			String sLName = txtLName.getText();
			String sEmail = txtEmail.getText();
			String sPhone = txtPhone.getText();
			String spwdPassword = pwdPassword.getText();
			String spwdConfirmPassword = pwdConfirmPassword.getText();
			String sGender = ((RadioButton) Gender.getSelectedToggle()).getText();
			LocalDate sDOB =  dpDoB.getValue(); 

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

			String sql = "UPDATE hms_login SET FName='" + sFName + "', LName='" + sLName 
					+ "', Phone='" + sPhone + "', Password='" + spwdPassword 
					+ "', DOB='" + sDOB + "', Gender='" + sGender 
					+ "' WHERE Email='" + sEmail + "' ;";
			System.out.print(sql);

			String sql_Admin_User = "";

			sql_Admin_User = "UPDATE hms_Admin SET FName='"
					+ sFName + "' where Email='" + sEmail + "' ;";

			int con = sStatement.executeUpdate(sql);
			int con1 = sStatement.executeUpdate(sql_Admin_User);
			if (con > 0 && con1 > 0) 
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AdminView.fxml"));
				root = fxmlLoader.load();
				AdminController admCtrl = (AdminController)fxmlLoader.getController();
				admCtrl.sUsername = this.sUsername;
				admCtrl.sPassword = this.sPassword;
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void onUpdateRoom()
	{
		try
		{
			String sRoomNo = cbmRoomNo.getSelectionModel().getSelectedItem().toString();
			String sStatus = cbmRoomStatus.getSelectionModel().getSelectedItem().toString();
			
			
			String sql = "UPDATE hms_Login SET Status='" + sStatus
					+ "' WHERE RoomNo='" + sRoomNo + "' ;";
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void displayBookingsData(String username, String password) 
	{
		this.sUsername = username;
		this.sPassword = password;
		this.sUsername = username;
		this.sPassword = password;
		admModel.GetUserDetails(sUsername, sPassword);
		this.txtFName.setText(admModel.getFName());
		this.txtLName.setText(admModel.getLName());
	}
	public void displayRoomData(String username, String password) 
	{
		this.sUsername = username;
		this.sPassword = password;

		String query = "SELECT * FROM hms_rooms ;";

		lstRooms = rmModel.GetRoomDetails(query);

		tcRoomNo.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("RoomNo"));
		tcRoomType.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("RoomType"));
		tcPrice.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("Price"));
		tcStatus.setCellValueFactory(new PropertyValueFactory<RoomModel, String>("Status"));

		tvManageRooms.setItems(lstRooms);
		tvManageRooms.setColumnResizePolicy((param) -> true);
	}
	public void LogOut(ActionEvent event)
	{

	}
}
