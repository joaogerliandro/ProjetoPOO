package entities.models;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import entities.Product;
import entities.ProductDAO;
import exceptions.InvalidProductObj;

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
		Product target = m_products.get(rowIndex);

		try 
		{
			switch (columnIndex)
			{
				case 0:
					throw new IllegalArgumentException("Cannot edit ID field: " + columnIndex);
				case 1:
					target.SetName(str_value);
					break;
				case 2:
					target.SetDescription(str_value);
					break;
				case 3:
					target.SetPrice(Double.parseDouble(str_value));
					break;
				case 4:
					target.SetAmount(Long.parseLong(str_value));
					break;
				default:
					throw new IllegalArgumentException("Table Out Bounds: " + columnIndex);
			}

			m_prod_dao.Edit(target);
			fireTableCellUpdated(rowIndex, columnIndex);
		} 
		catch (NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(null, "Cannot update table, reason: Invalid data format for this text field", "ZéBigod's Error Popup", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot update table, reason: " + e.getMessage(), "ZéBigod's Error Popup", JOptionPane.ERROR_MESSAGE);
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
		if (cols != 0)
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
}
