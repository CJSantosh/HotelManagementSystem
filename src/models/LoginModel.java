package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class LoginModel extends DBConnect{

	private String Username;
	private String Password;

	public String getUsername() {
		return Username;
	}
	public String getPassword() {
		return Password;
	}

	public void setUsername(String username) {
		this.Username = username;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public Boolean getCredentials(String sUsername, String sPassword, String sUserType)
	{

		String query = "SELECT * FROM hms_login WHERE Email = ? and Password = ? and UserType = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)) 
		{
			stmt.setString(1, sUsername);
			stmt.setString(2, sPassword);
			stmt.setString(3, sUserType);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{ 
				setUsername(rs.getString("Email"));
				setPassword(rs.getString("Password"));
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();   
		}
		return false;
	}

}
