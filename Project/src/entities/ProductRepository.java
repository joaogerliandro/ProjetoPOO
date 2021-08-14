package entities;
import interfaces.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/* INFO(Garcia): Utilização de classes abstrata e membros protegidos */
public abstract class ProductRepository implements IProductRepository
{
	/* 
								INFO(Garcia): 
		Como é uma classe abstrata não precisamos implementar os métodos
		da interface aqui, iremos implementar na classe derivada desta.
	*/

	protected Connection m_connection;
	protected static final String g_insertion_query = "insert into products (name, description, price, amount) values (?, ?, ?, ?)";
	protected static final String g_findall_query = "select * from products";
	protected static final String g_update_query = "update products set name=?, description=?, price=?, amount=? where id = ?";

	// close and free all resources
	public void Release()
	{
		try 
		{
			m_connection.close();
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, 
				"Error to release resources: " + e.toString(), 
				"Error", JOptionPane.ERROR_MESSAGE
			);
		}
	}
}
