// Admin controller for admin related UI's

package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import dao.DBConnect;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import models.BookingsModel;
import models.RoomModel;

public class AdminController extends CommonController{


	//User View Bookings controls
	@FXML
	private TableView<BookingsModel> tvBookings;
	@FXML
	private TableColumn<BookingsModel, String> tcBookingID;
	@FXML
	private TableColumn<BookingsModel, String> tcEmail;
	@FXML
	private TableColumn<BookingsModel, String> tcNoOfRooms;
	@FXML
	private TableColumn<BookingsModel, String> tcBookingRoomType;
	@FXML
	private TableColumn<BookingsModel, String> tcDateFrom;
	@FXML
	private TableColumn<BookingsModel, String> tcDateTo;
	@FXML
	private TableColumn<BookingsModel, String> tcSpa;
	@FXML
	private TableColumn<BookingsModel, String> tcLounge;
	@FXML
	private TableColumn<BookingsModel, String> tcPool;
	@FXML
	private TableColumn<BookingsModel, String> tcBookingPrice;
	@FXML
	private TableColumn<BookingsModel, String> tcBookingStatus;
	@FXML
	private JFXTextField txtBookingID;
	
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
	private Label lblError;
	@FXML
	private JFXComboBox<String> cbmRoomNo;
	@FXML
	private JFXComboBox<String> cbmRoomStatus;
	@FXML
	private Label userlabel;
	
	DBConnect dbConnect = null;
	Statement sStatement = null;

	String sUsername;
	String sPassword;
	
	ObservableList<RoomModel> lstRooms;
	ObservableList<BookingsModel> lstBookings;

	AdminModel admModel = null;
	RoomModel rmModel = null;
	BookingsModel bkgModel = null;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public AdminController()
	{
		dbConnect = new DBConnect();
		admModel = new AdminModel();
		rmModel = new RoomModel();
		bkgModel = new BookingsModel();
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


			int con = sStatement.executeUpdate(sql);
			if (con > 0) 
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

	public void displayBookingsData(String username, String password) 
	{
		this.sUsername = username;
		this.sPassword = password;
		
		String query = "SELECT * FROM hms_bookings ;";
		
		lstBookings = bkgModel.GetBookingDetails(query);

		tcBookingID.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("BookingID"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Email"));
		tcNoOfRooms.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("NoOfRooms"));
		tcBookingRoomType.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("RoomType"));
		tcDateFrom.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("DateFrom"));
		tcDateTo.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("DateTo"));
		tcBookingPrice.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Price"));
		tcBookingStatus.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Status"));

		tvBookings.setItems(lstBookings);
		tvBookings.setColumnResizePolicy((param) -> true);

	}
	public void onDelete()
	{
		try
		{			
			int iBookingID = Integer.parseInt(txtBookingID.getText());
			
			System.out.print(iBookingID);
			sStatement = dbConnect.getConnection().createStatement();
			String sql = "DELETE FROM hms_Bookings WHERE BookingID='" + iBookingID + "' ;";
			
			int con = sStatement.executeUpdate(sql);
			if(con > 0)
			{
				displayBookingsData( this.sUsername, this.sPassword);
				txtBookingID.setText(null);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
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
	
	public void onUpdateRoom()
	{
		try
		{
			String sRoomNo = cbmRoomNo.getSelectionModel().getSelectedItem().toString();
			String sStatus = cbmRoomStatus.getSelectionModel().getSelectedItem().toString();
			
			sStatement = dbConnect.getConnection().createStatement();
			String sql = "UPDATE hms_Rooms SET Status='" + sStatus
					+ "' WHERE RoomNo='" + sRoomNo + "' ;";
			int con = sStatement.executeUpdate(sql);
			if(con > 0)
			{
				displayRoomData( this.sUsername, this.sPassword);
				cbmRoomNo.getSelectionModel().clearSelection();
				cbmRoomStatus.getSelectionModel().clearSelection();
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@FXML
	public void logOut(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
		root = fxmlLoader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
