
//Model for user related data

package models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.DBConnect;

public class UserModel extends DBConnect{

	private String sFName;
	private String sLName;
	private String sEmail;
	private String sPhone;
	private String sPassword;
	private LocalDate dpDOB;
	private String sGender;
	
	public String getFName() 
	{
		return sFName;
	}
	public String getLName() 
	{
		return sLName;
	}
	public String getEmail() 
	{
		return sEmail;
	}
	public String getPhone() 
	{
		return sPhone;
	}
	public String getPassword() 
	{
		return sPassword;
	}
	public LocalDate getDOB() 
	{
		return dpDOB;
	}
	public String getGender() 
	{
		return sGender;
	}
	
	public String setFName(String FName) 
	{
		return this.sFName = FName;
	}
	public String setLName(String LName) 
	{
		return sLName = LName;
	}
	public String setEmail(String Email) 
	{
		return sEmail = Email;
	}
	public String setPhone(String Phone) 
	{
		return sPhone = Phone;
	}
	public String setPassword(String Password) 
	{
		return sPassword = Password;
	}
	public LocalDate setDOB(Date DOB) 
	{
		return dpDOB = DOB.toLocalDate();
	}
	public String setGender(String Gender) 
	{
		return sGender = Gender;
	}
	
	public void GetUserDetails(String sUsername, String sPassword)
	{
		String query = "SELECT * FROM hms_login WHERE Email = ? and Password = ? and UserType = ?;";
		
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, sUsername);
			stmt.setString(2, sPassword);
			stmt.setString(3, "User");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) 
			{
				setFName(rs.getString("FName"));
				setLName(rs.getString("LName"));
				setEmail(rs.getString("Email"));
				setPhone(rs.getString("Phone"));
				setPassword(rs.getString("Password"));
				setDOB(rs.getDate("DOB"));
				setGender(rs.getString("Gender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
