
//Model for all booking related details

package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookingsModel 
{
	private String sBookingID;
	private String sEmail;
	private String sNoOfRooms;
	private String sRoomType;
	private String sDateFrom;
	private String sDateTo;
	private String sSpa;
	private String sLounge;
	private String sPool;
	private String sPrice;
	private String sStatus;
	
	DBConnect dbConnect = null;
	
	public BookingsModel()
	{
		dbConnect = new DBConnect();
	}
	
	public String getBookingID()
	{
		return sBookingID;
	}
	public String getEmail()
	{
		return sEmail;
	}
	public String getNoOfRooms()
	{
		return sNoOfRooms;
	}
	public String getRoomType()
	{
		return sRoomType;
	}
	public String getDateFrom()
	{
		return sDateFrom;
	}
	public String getDateTo()
	{
		return sDateTo;
	}
	public String getSpa()
	{
		return sSpa;
	}
	public String getLounge()
	{
		return sLounge;
	}
	public String getPool()
	{
		return sPool;
	}
	public String getPrice()
	{
		return sPrice;
	}
	public String getStatus()
	{
		return sStatus;
	}
	
	public String setBookingID(String BookingID)
	{
		return sBookingID = BookingID;
	}
	public String setEmail(String Email)
	{
		return sEmail = Email;
	}
	public String setNoOfRooms(String NoOfRooms)
	{
		return sNoOfRooms = NoOfRooms;
	}
	public String setRoomType(String RoomType)
	{
		return sRoomType = RoomType;
	}
	public String setDateFrom(String DateFrom)
	{
		return sDateFrom = DateFrom;
	}
	public String setDateTo(String DateTo)
	{
		return sDateTo = DateTo;
	}
	public String setSpa(String Spa)
	{
		return sSpa = Spa;
	}
	public String setLounge(String Lounge)
	{
		return sLounge = Lounge;
	}
	public String setPool(String Pool)
	{
		return sPool = Pool;
	}
	public String setPrice(String Price)
	{
		return sPrice = Price;
	}
	public String setStatus(String Status)
	{
		return sStatus = Status;
	}

	public ObservableList<BookingsModel> GetBookingDetails(String query)
	{
		ObservableList<BookingsModel> bookingsList = FXCollections.observableArrayList();
		
		try (PreparedStatement statement = dbConnect.getConnection().prepareStatement(query))
		{
			ResultSet rs = statement.executeQuery();
			while (rs.next()) 
			{
				BookingsModel bookingsModel = new BookingsModel();

				bookingsModel.setBookingID(rs.getString("BookingID"));
				bookingsModel.setEmail(rs.getString("Email"));
				bookingsModel.setNoOfRooms(rs.getString("NoOfRooms"));
				bookingsModel.setRoomType(rs.getString("RoomType"));
				bookingsModel.setDateFrom(rs.getString("DateFrom"));
				bookingsModel.setDateTo(rs.getString("DateTo"));
				bookingsModel.setSpa(rs.getString("Spa"));
				bookingsModel.setLounge(rs.getString("Lounge"));
				bookingsModel.setPool(rs.getString("Pool"));
				bookingsModel.setPrice(rs.getString("Price"));
				bookingsModel.setStatus(rs.getString("Status"));
				
				bookingsList.add(bookingsModel);
			}
		} 
		catch (SQLException e) {
			System.out.println("Error Displaying Booking ");
		}
		
		return bookingsList;
	}
}
