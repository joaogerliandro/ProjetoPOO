package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
	private String m_url  = "jdbc:mysql://localhost:3306/zbdb";
	private String m_user = "root";
	private String m_psw  = "root";

	public ConnectionFactory() {}
	public ConnectionFactory(String url, String user, String password)
	{
		m_url  = url;
		m_user = user;
		m_psw  = password;
	}

	public Connection GetConnection()
	{
		try 
		{
			return DriverManager.getConnection(m_url, m_user, m_psw);
		} 
		catch (SQLException e) 
		{
			Utilities.ShowPopupError("Connection Failure: " + e.getMessage());
			System.exit(1);
		}
        
        return null;
	} 
}
