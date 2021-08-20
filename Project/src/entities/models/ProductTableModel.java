package entities.models;

import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import entities.Product;
import entities.ProductDAO;
import entities.Utilities;
import exceptions.EmptyFieldException;
import exceptions.InvalidProductObj;

import java.util.function.Consumer;

public class ProductTableModel extends AbstractTableModel
{
	private List<Product> m_products;
	private List<String> m_header_columns;
	private ProductDAO m_prod_dao;
	
	public ProductTableModel(ProductDAO prod_dao)
	{
		m_prod_dao = prod_dao;
		UpdateProducts();
		m_header_columns = Arrays.asList("ID", "Name", "Description", "Price", "Amount");
	}

	@Override
	public int getRowCount() 
	{
		return m_products.size();
	}

	@Override
	public int getColumnCount() 
	{
		return m_header_columns.size();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) throws IllegalArgumentException
	{
		String str_value = (String) value;
		Product source = m_products.get(rowIndex);
		Product target = source;

		try 
		{
			switch (columnIndex)
			{
				case 0:
					throw new IllegalArgumentException("Cannot edit ID field: " + columnIndex);
				case 1:
					if (str_value.isEmpty())
						throw new EmptyFieldException("The product name cannot be empty");
					
					target.SetName(str_value);
					break;
				case 2:
					target.SetDescription(str_value);
					break;
				case 3:
					double price = Double.parseDouble(str_value);
					
					if (price <= 0.0)
					{
						Utilities.ShowPopupWarn("Price text field must be greater than zero");
						return;
					}

					target.SetPrice(price);
					break;
				case 4:
				    Long amount = Long.parseLong(str_value);
					
					if (amount <= 0.0)
					{
						Utilities.ShowPopupWarn("Amount text field must be greater than zero");
						return;
					}

					target.SetAmount(amount);
					break;
				default:
					throw new IllegalArgumentException("Table Out Bounds: " + columnIndex);
			}

			m_prod_dao.Edit(target);

			// Update only the altered value
			m_products.set(rowIndex, target);
		} 
		catch (NumberFormatException nfe)
		{
			Utilities.ShowPopupError("Cannot update table, reason: Invalid data format for this text field");
		}
		catch (Exception e) 
		{
			Utilities.ShowPopupError("Cannot update table, reason: " + e.getMessage());
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) throws InvalidProductObj, IllegalArgumentException
	{
		Product target = m_products.get(rowIndex);
		if (target == null)
			throw new InvalidProductObj("product object cannot be null");
		
		switch (columnIndex)
		{
			case 0:
				return target.GetId();
			case 1:
				return target.GetName();
			case 2:
				return target.GetDescription();
			case 3:
				return target.GetPrice();
			case 4:
				return target.GetAmount();
			default:
				throw new IllegalArgumentException("Table Out Bounds: " + columnIndex);
		}
	}

	@Override
	public String getColumnName(int index)
	{
		return m_header_columns.get(index);
	}

	@Override
	public boolean isCellEditable(int rows, int cols) 
	{
		// all field's except id is editable
		if (cols > 1)
			return true;
		else
			return false;
	}

	private void UpdateProducts()
	{
		m_products = m_prod_dao.GetProducts();
	}

	@Override
	public void fireTableDataChanged()
	{
		UpdateProducts();
		super.fireTableDataChanged();
	}

	public void ForEach(Consumer<? super Product> action) 
	{
        for (Product p : m_products) 
		{
            action.accept(p);
        }
    }

	public Product GetProduct(int index)
	{
		return m_products.get(index);
	}

	public void RemoveTableProduct(int index)
	{
		m_products.remove(index);
	}
}
