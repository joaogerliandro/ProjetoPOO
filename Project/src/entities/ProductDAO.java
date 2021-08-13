package entities;

import interfaces.IProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import javax.swing.JOptionPane;
import entities.ConnectionFactory;

public class ProductDAO implements IProduct
{
	private Connection m_connection;

	public ProductDAO()
	{
		m_connection = new ConnectionFactory().GetConnection();
	}

    @Override
    public void Add(Product p) 
	{
		String sql = "insert into products " + 
					 "(name, desc, price, amount)" +
					 "values (?, ?, ?, ?)";

		try 
		{
			PreparedStatement stmt = m_connection.prepareStatement(sql);
			
			stmt.setString(1, p.GetName());
			stmt.setString(2, p.GetDescription());
			stmt.setDouble(3, p.GetPrice());
			stmt.setLong(4, p.GetAmount());

			stmt.execute();
			stmt.close();
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, 
				"Error to add product: " + e.toString(), 
				"Error", JOptionPane.ERROR_MESSAGE
			);
		}
    }

    @Override
    public List<Product> GetProducts() 
	{
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void Edit(Product p) 
	{
        // TODO Auto-generated method stub
    }

    @Override
    public void Remove(Long product_id) 
	{
        // TODO Auto-generated method stub
    }
}