package entities.frames;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JOptionPane;

import entities.ProductDAO;
import entities.Utilities;
import entities.models.ProductTableModel;

public class FrmMain extends JFrame
{
	/* Default Refresh Time */
	static final int g_refresh_time_seconds = 3;
	
	/* Panels */
	private JScrollPane m_pnlScrollTable;
	private JPanel m_pnlButtons;
	private Color m_pnlButtonsBackgroundColor = new Color(235, 235, 235);
	
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
	
	/* Child Frames */
	private FrmInsertInfo m_frmInsertInfo;
	
	public FrmMain()
	{
		super("ZéBigod's Product Manager");
		
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
		m_pnlButtons.setBackground(m_pnlButtonsBackgroundColor);
		m_pnlButtons.setLayout(new FlowLayout());
		
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
		setSize(840, 400);
		setMinimumSize(getSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				// close the connection
				ProductDAO.GetInstance().Release();
				super.windowClosing(e);
			}
		});
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
		m_tblProducts  = new JTable(new ProductTableModel());
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)m_tblProducts.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		m_tblProducts.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
				{
					DeleteTableRegister();
				}
				else if (e.getKeyCode() == KeyEvent.VK_INSERT)
				{
					CallInsertFrame();
				}
			}
		});
		
		m_tblProductsModel = (ProductTableModel) m_tblProducts.getModel();
		Utilities.CentralizeAllTableCells(m_tblProducts);
	}
	
	private void SetupTablePanel()
	{
		m_pnlScrollTable = new JScrollPane(m_tblProducts);
		m_tblProducts.setFillsViewportHeight(true);
	}
	
	private Window GetThisWindow()
	{
		return (Window) this;
	}
	
	private void SetupButtons() 
	{
		m_btnInsert = new JButton("Insert");
		
		m_btnInsert.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				CallInsertFrame();
			}
		});
		
		m_btnDelete = new JButton("Delete");
		m_btnDelete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				DeleteTableRegister();
			}
		});
		
		m_btnDeleteAll = new JButton("Clear");
		m_btnDeleteAll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to perform this operation?", "ZéBigod's Warning", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.YES_OPTION)
				{
					ProductDAO.GetInstance().RemoveAll();
					RefreshTableData();
				}
			}
		});
		
		m_btnUpdate = new JButton("Refresh");
		m_btnUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				RefreshTableData();
			}
		});
	}
	
	private void CallInsertFrame()
	{
		m_frmInsertInfo = new FrmInsertInfo(GetThisWindow());
		
		m_frmInsertInfo.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosed(WindowEvent e)
			{
				RefreshTableData();
			}
		});
	}
	
	private void SetupCheckBoxes() 
	{
		m_chkAutoUpdate = new JCheckBox("Auto Refresh", false);
		m_chkAutoUpdate.setBackground(m_pnlButtons.getBackground());
		
		m_chkAutoUpdate.addItemListener(new ItemListener() 
		{    
			public void itemStateChanged(ItemEvent e) 
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
	
	private void DeleteTableRegister() 
	{
		int[] selected_rows = m_tblProducts.getSelectedRows();
		
		if (selected_rows.length == 0)
		{
			Utilities.ShowPopupWarn("You need to select a line before deleting it");
			return;
		}
		
		for (int row : selected_rows)
		{
			ProductDAO.GetInstance().Remove((Long) m_tblProducts.getValueAt(row, 0));
		}

		RefreshTableData();
	}
}
