package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

import dao.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomModel 
{
	private String sRoomNo;
	private String sRoomType;
	private String sPrice;
	private String sStatus;
	
	DBConnect dbConnect = null;
	Statement stmt = null;
	
	public RoomModel()
	{
		dbConnect = new DBConnect();
	}
	
	public String getRoomNo()
	{
		return sRoomNo;
	}

	public String getRoomType()
	{
		return sRoomType;
	}
	
	public String getPrice()
	{
		return sPrice;
	}
	
	public String getStatus()
	{
		return sStatus;
	}
	
	public String setRoomNo(String room)
	{
		return sRoomNo = room;
	}

	public String setRoomType(String roomType)
	{
		return sRoomType = roomType;
	}
	
	public String setPrice(String price)
	{
		return sPrice = price;
	}
	
	public String setStatus(String status)
	{
		return sStatus = status;
	}

	public ObservableList<RoomModel> GetRoomDetails(String query)
	{
		ObservableList<RoomModel> roomList = FXCollections.observableArrayList();
		
		try (PreparedStatement statement = dbConnect.getConnection().prepareStatement(query))
		{
			ResultSet rs = statement.executeQuery();
			while (rs.next()) 
			{
				RoomModel roomModel = new RoomModel();
				
				roomModel.setRoomNo(rs.getString("RoomNo"));
				roomModel.setRoomType(rs.getString("RoomType"));
				roomModel.setPrice(rs.getString("Price"));
				roomModel.setStatus(rs.getString("Status"));
				
				roomList.add(roomModel);
			}
		} 
		catch (SQLException e) {
			System.out.println("Error Displaying Room ");
		}
		
		return roomList;
	}
	
}
