package entities.frames;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Product;
import entities.ProductDAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

public class FrmInsertInfo extends JFrame
{
	private ProductDAO m_productDAO;
	private JPanel m_pnlContent;

	private JLabel m_lblName;
	private JLabel m_lblDesc;
	private JLabel m_lblPrice;
	private JLabel m_lblAmount;

	private JTextField m_txtName;
	private JTextField m_txtDesc;
	private JTextField m_txtPrice;
	private JTextField m_txtAmount;

	private JButton m_btnInsert;

	public FrmInsertInfo(ProductDAO product_dao) 
	{
		super("ZéBigod's Insert Information");
		m_productDAO = product_dao;
		
		SetupWindow();
		SetupLabels();
		SetupTextFields();
		SetupButtons();

		AddComponents();
	}

	private void SetupWindow() 
	{
		setDefaultLookAndFeelDecorated(true);
		pack();

		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
		toFront();
	}

	private void AddComponents()
	{
		m_pnlContent = new JPanel();
		m_pnlContent.setSize(400, 300);
		m_pnlContent.setLayout(new BoxLayout(m_pnlContent, BoxLayout.Y_AXIS));

		m_pnlContent.add(Box.createRigidArea(new Dimension(0, 20)));
		m_pnlContent.add(m_lblName);
		m_pnlContent.add(m_txtName);
		m_pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));

		m_pnlContent.add(m_lblDesc);
		m_pnlContent.add(m_txtDesc);
		m_pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));

		m_pnlContent.add(m_lblPrice);
		m_pnlContent.add(m_txtPrice);
		m_pnlContent.add(Box.createRigidArea(new Dimension(0, 5)));

		m_pnlContent.add(m_lblAmount);
		m_pnlContent.add(m_txtAmount);

		m_pnlContent.add(Box.createRigidArea(new Dimension(68, 20)));
		m_pnlContent.add(m_btnInsert);

		add(m_pnlContent);

		// update gfx buffer
		//setVisible(true);
	}

	private void SetupLabels()
	{
		m_lblName = new JLabel("Name: ");
		m_lblDesc = new JLabel("Description: ");
		m_lblPrice = new JLabel("Price: ");
		m_lblAmount = new JLabel("Amount: ");
	}

	private void SetupTextFields()
	{
		m_txtName = new JTextField(15);
		m_txtDesc = new JTextField(15);
		m_txtPrice = new JTextField(15);
		m_txtAmount = new JTextField(15);
	}

	private void SetupButtons() 
	{
		m_btnInsert = new JButton("Insert");
		m_btnInsert.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				Product p = new Product(m_txtName.getText(),
										m_txtDesc.getText(),
										Double.parseDouble(m_txtPrice.getText()),
										Long.parseLong(m_txtAmount.getText()));
				
				m_productDAO.Add(p);
				
				// Destroy the JFrame object
				setVisible(false);

				m_txtName.setText("");
				m_txtDesc.setText("");
				m_txtPrice.setText("");
				m_txtAmount.setText("");
			}
		});
	}
}
