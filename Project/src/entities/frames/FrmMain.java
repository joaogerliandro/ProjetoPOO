package entities.frames;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableModel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

import entities.ProductDAO;
import entities.models.ProductTableModel;
import javafx.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmMain extends JFrame
{
	/* Data Members */
	private ProductDAO m_product_dao;

	/* Default Refresh Time */
	static final int g_refresh_time_seconds = 3;

	/* Panels */
	private JScrollPane m_pnlScrollTable;
	private JPanel m_pnlButtons;

	/* Tables */
	private JTable m_tblProducts;
	private ProductTableModel m_tblProductsModel;

	/* Buttons */
	private JButton m_btnInsert;
	private JButton m_btnDelete;
	private JButton m_btnUpdate;
	private JButton m_btnDeleteAll;

	/* Checkboxes */
	private JCheckBox m_chkAutoUpdate;

	/* Timers */
	Timer m_auto_update_timer;
	
	public FrmMain(ProductDAO product_dao)
	{
		super("ZéBigod's Product Manager");
		m_product_dao = (product_dao == null) ? new ProductDAO() : product_dao;
		
		SetupWindow();
		SetupProductTable();
		SetupTablePanel();
		SetupAutoRefresh();
		SetupToolbarPanel();
		AddComponents();
	}

	private void SetupAutoRefresh() 
	{
		// atualiza a tabela a cada "delay_in_seconds" segundos
		m_auto_update_timer = new Timer(g_refresh_time_seconds * 1000, null);
		
		ActionListener listener = new ActionListener() 
		{
			@Override 
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{
				RefreshTableData();
			}
		};

		m_auto_update_timer.addActionListener(listener);
	}

	private void SetupToolbarPanel() 
	{
		m_pnlButtons = new JPanel();
		m_pnlButtons.setLayout(new java.awt.FlowLayout());
		
		SetupButtons();
		SetupCheckBoxes();  

		m_pnlButtons.add(m_btnInsert);
		m_pnlButtons.add(m_btnDelete);
		m_pnlButtons.add(m_btnDeleteAll);
		m_pnlButtons.add(m_btnUpdate);
		m_pnlButtons.add(m_chkAutoUpdate);
	}

	private void SetupWindow() 
	{
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}

	private void AddComponents()
	{
		add(m_pnlScrollTable, BorderLayout.CENTER);
		add(m_pnlButtons, BorderLayout.SOUTH);

		// update gfx buffer
		setVisible(true);
	}

	private void SetupProductTable()
	{
		m_tblProducts = new JTable(new ProductTableModel(m_product_dao));
		m_tblProductsModel = (ProductTableModel) m_tblProducts.getModel();
	}

	private void SetupTablePanel()
	{
		m_pnlScrollTable = new JScrollPane(m_tblProducts);
		m_tblProducts.setFillsViewportHeight(true);
	}

	private void SetupButtons() 
	{
		m_btnInsert = new JButton("Insert");
		m_btnInsert.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{
				// FAZER AQ
			}
		});

		m_btnDelete = new JButton("Delete");
		m_btnDelete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{
				int[] selected_rows = m_tblProducts.getSelectedRows();
				
				if (selected_rows.length == 0)
				{
					JOptionPane.showMessageDialog(null, "You need to select a line before deleting it", "ZéBigod's Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}

				for (int row : selected_rows)
				{
					m_product_dao.Remove((Long) m_tblProducts.getValueAt(row, 0));
				}
				
				RefreshTableData();
			}
		});

		m_btnDeleteAll = new JButton("Delete All");
		m_btnDeleteAll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{
				m_product_dao.RemoveAll();
				RefreshTableData();
			}
		});
		
		m_btnUpdate = new JButton("Refresh");
		m_btnUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{
				RefreshTableData();
			}
		});
	}

	private void SetupCheckBoxes() 
	{
		m_chkAutoUpdate = new JCheckBox("Auto Refresh", false);
		m_chkAutoUpdate.addItemListener(new ItemListener() 
		{    
			public void itemStateChanged(java.awt.event.ItemEvent e) 
			{                 
				if (e.getStateChange() == 1) // checked
				{
					if (!m_auto_update_timer.isRunning())
						m_auto_update_timer.start();
				}
				else 
				{
					if (m_auto_update_timer.isRunning())
						m_auto_update_timer.stop();
				}
			}    
		});
	}

	public void SetRefreshTime(int delay_seconds)
	{
		boolean running = m_auto_update_timer.isRunning();
		if (running)
			m_auto_update_timer.stop();
		
		m_auto_update_timer.setInitialDelay(delay_seconds * 1000);
		m_auto_update_timer.setDelay(delay_seconds * 1000);

		if (running)
			m_auto_update_timer.restart();
	}

	private void RefreshTableData()
	{
		m_tblProductsModel.fireTableDataChanged();
		m_tblProducts.repaint();
	}
}
