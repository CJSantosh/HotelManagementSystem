
//Controller with all user related details

package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.BookingsModel;
import models.UserModel;


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
	private TableView<BookingsModel> tvMyBookings;
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
	private Label userlabel;
	

	DBConnect dbConnect = null;
	Statement sStatement = null;

	String sUsername;
	String sPassword;
	int TotalPrice;
	
	ObservableList<BookingsModel> lstBookings;

	UserModel usrModel = null;
	BookingsModel bkgModel = null;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public UserController()
	{
		dbConnect = new DBConnect();
		usrModel = new UserModel();
		bkgModel = new BookingsModel();
	}
	@FXML
	public void SwitchUI(ActionEvent event)
	{
		SwitchBtwUI(event, sUsername, sPassword);
	}

	public void displayProfileData(String Username, String Password)
	{
		this.sUsername = Username;
		this.sPassword = Password;
		usrModel.GetUserDetails(sUsername, sPassword);
		this.txtFName.setText(usrModel.getFName());
		this.txtLName.setText(usrModel.getLName());
		this.txtEmail.setText(usrModel.getEmail());
		this.txtPhone.setText(usrModel.getPhone());
		this.pwdPassword.setText(usrModel.getPassword());
		this.pwdConfirmPassword.setText(usrModel.getPassword());
		this.dpDoB.setValue(usrModel.getDOB());
		if(usrModel.getGender().equals("Male"))
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
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/UserView.fxml"));
				root = fxmlLoader.load();
				UserController usrCtrl = (UserController)fxmlLoader.getController();
				usrCtrl.sUsername = this.sUsername;
				usrCtrl.sPassword = this.sPassword;
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

	public void displayBookRoomData(String Username, String Password)
	{
		this.sUsername = Username;
		this.sPassword = Password;
		
	}
	public void onCalculatePrice()
	{

		String sGuest = cbmGuest.getValue();
		String sNoOfRooms = cbmRooms.getValue();
		String sRoomType = cbmRoomType.getValue();
		
		if( sRoomType.equals(""))
			return;
		int RoomPrice = sRoomType.equals("Semi Delux") ? 100 : (sRoomType.endsWith("Delux") ? 200 : (sRoomType.equals("Family Suite") ? 300 : (sRoomType.equals("Premium") ? 400 : 0)));
		int iSpa = cbSpa.isSelected() ? 50 : 0 ;
		int iLounge = cbLounge.isSelected() ? 50 : 0 ;
		int iPool = cbPool.isSelected() ? 50 : 0 ;
		TotalPrice = (Integer.parseInt(sGuest) * 50) + (Integer.parseInt(sNoOfRooms) * RoomPrice) + iSpa + iLounge + iPool;  
		
		lblTotal.setText("$" + TotalPrice);
	}
	
	public void onReserve(ActionEvent event) throws IOException
	{
		try
		{
			String sGuest = cbmGuest.getValue();
			String sNoOfRooms = cbmRooms.getValue();
			String sRoomType = cbmRoomType.getValue();
			LocalDate sDateFrom  = dpDateFrom.getValue();
			LocalDate sDateTo = dpDateTo.getValue();
			String sSpa = cbSpa.isSelected() ? "Yes" : "No" ;
			String sLounge = cbLounge.isSelected() ? "Yes" : "No" ;
			String sPool = cbPool.isSelected() ? "Yes" : "No" ;
			String sTotal = String.valueOf(TotalPrice);
			
			sStatement = dbConnect.getConnection().createStatement();
			
			String sql = "INSERT INTO hms_bookings (Email, NoOfGuests, NoOfRooms, RoomType, DateFrom, DateTo, Spa, Lounge, Pool, Price, Status) VALUES "
					+ "('" + sUsername + "','" + sGuest + "','" + sNoOfRooms + "','" + sRoomType + "','" 
					+ sDateFrom + "','" + sDateTo + "','" + sSpa + "','" + sLounge + "','" 
					+ sPool + "','" + sTotal + "','" + "Booked" + "');";

			int con = sStatement.executeUpdate(sql);
			if (con > 0) 
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/UserView.fxml"));
				root = fxmlLoader.load();
				UserController usrCtrl = (UserController)fxmlLoader.getController();
				usrCtrl.sUsername = this.sUsername;
				usrCtrl.sPassword = this.sPassword;
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
	public void displayBoookingData(String Username, String Password)
	{
		this.sUsername = Username;
		this.sPassword = Password;
		
		String query = "SELECT * FROM hms_bookings WHERE Email='" + Username + "';";

		lstBookings = bkgModel.GetBookingDetails(query);
		
		tcBookingID.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("BookingID"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Email"));
		tcNoOfRooms.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("NoOfRooms"));
		tcBookingRoomType.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("RoomType"));
		tcDateFrom.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("DateFrom"));
		tcDateTo.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("DateTo"));
		tcSpa.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Spa"));
		tcLounge.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Lounge"));
		tcPool.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Pool"));
		tcBookingPrice.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Price"));
		tcBookingStatus.setCellValueFactory(new PropertyValueFactory<BookingsModel, String>("Status"));

		tvMyBookings.setItems(lstBookings);
		tvMyBookings.setColumnResizePolicy((param) -> true);

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
