package entities;

import exceptions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ProductDAO extends ProductRepository
{
	public ProductDAO()
	{
		m_connection = new ConnectionFactory().GetConnection();
	}

    @Override
    public void Add(Product p)
	{
		try
		{
			if (p == null)
				throw new InvalidProductObj("Product object cannot be null");

			PreparedStatement stmt = m_connection.prepareStatement(g_insertion_query, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, p.GetName());
			stmt.setString(2, p.GetDescription());
			stmt.setDouble(3, p.GetPrice());
			stmt.setLong(4, p.GetAmount());

			stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) 
			{
                if (rs.next()) 
				{
                    p.SetId(rs.getLong(1));
                }
            } 
			catch (SQLException s) 
			{
               throw s;
            }

			stmt.close();
		}
		catch (Exception e)
		{
			Utilities.ShowPopupError("Error to add product: " + e.getMessage());
		}
    }

    @Override
    public List<Product> GetProducts() 
	{
        List<Product> result = new ArrayList<>();
		
        try (Statement stmt = m_connection.createStatement();
             ResultSet rs   = stmt.executeQuery(g_findall_query)) 
		{
            while (rs.next()) 
			{
				result.add(new Product(rs.getLong("id"), 
									   rs.getString("name"),
									   rs.getString("description"),
									   rs.getDouble("price"),
									   rs.getLong("amount")));
            }
        } 
		catch (SQLException e) 
		{
			Utilities.ShowPopupError("Error to get products: " + e.getMessage());
        }

        return result;
    }

	@Override
	public Long Find(Product target)
	{
		if (target == null)
			return -1L;

		try (PreparedStatement stmt = m_connection.prepareStatement(g_findall_query);
			 ResultSet rs = stmt.executeQuery(g_findall_query))
		{			
			while (rs.next())
			{
				Product result = new Product(rs.getLong("id"), 
											 rs.getString("name"),
											 rs.getString("description"),
											 rs.getDouble("price"),
											 rs.getLong("amount"));
				
				if (Product.Compare(target, result))
					return result.GetId();
			}
		} 
		catch (Exception e) 
		{
			Utilities.ShowPopupError("Cannot find product: " + e.getMessage());
		}

		return -1L;
	}

	@Override
	public Optional<Product> Find(Long product_id)
	{
		try (PreparedStatement stmt = m_connection.prepareStatement("select * from products where id=?"))
		{
			stmt.setLong(1, product_id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next())
			{
				return Optional.of(new Product(rs.getLong("id"), 
											   rs.getString("name"),
											   rs.getString("description"),
											   rs.getDouble("price"),
											   rs.getLong("amount")));
			}

			rs.close();
		} 
		catch (Exception e) 
		{
			Utilities.ShowPopupError("Cannot find product: " + e.getMessage());
		}

		return Optional.empty();
	}

    @Override
    public void Edit(Product target) 
	{
		// product does not exists
		if (target == null || !Find(target.GetId()).isPresent())
			throw new InvalidProductObj("invalid product object");
		
		try (PreparedStatement stmt = m_connection.prepareStatement(g_update_query))
		{
			stmt.setString(1, target.GetName());
			stmt.setString(2, target.GetDescription());
			stmt.setDouble(3, target.GetPrice());
			stmt.setLong(4,   target.GetAmount());
			stmt.setLong(5,   target.GetId());
			stmt.execute();
		} 
		catch (Exception e) 
		{
			Utilities.ShowPopupError("Error to edit product: " + e.getMessage());
		}
    }

    @Override
    public void Remove(Long product_id)
	{
		// nao precisa fechar o stmt, pois como ele está em parenteses no try,
		// é fechado implicitamente pelo bloco
		try (PreparedStatement stmt = m_connection.prepareStatement(g_remove_query)) 
		{
			stmt.setLong(1, product_id);
			stmt.execute();
			if (stmt.getUpdateCount() == 0)
				throw new InvalidProductID("Invalid product id");
        } 
		catch (Exception e) 
		{
			Utilities.ShowPopupError("Error to remove an product: " + e.getMessage());
        }
    }

	@Override
    public void RemoveAll()
	{
		try (PreparedStatement stmt = m_connection.prepareStatement(g_removeall_query)) 
		{
			stmt.execute();
        } 
		catch (Exception e) 
		{
			Utilities.ShowPopupError("Error to remove all products: " + e.getMessage());
        }
	}

	@Override
    public boolean Remove(Product target)
	{
		Long id = Find(target);

		if (id == -1)
			return false;
		
		Remove(id);
		return true;
	}
}
