package entities.frames;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import entities.Utilities;
import entities.Product;
import entities.ProductDAO;
public class FrmInsertInfo extends JDialog
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

	public FrmInsertInfo(Window owner, ProductDAO product_dao) 
	{
		super(owner, "ZéBigod's Insert Information");
		m_productDAO = product_dao;
		
		SetupWindow();
		SetupLabels();
		SetupTextFields();
		SetupButtons();

		AddComponents();
	}

	private void SetupWindow() 
	{
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		setVisible(true);
	}

	private void SetupLabels()
	{
		m_lblName   = new JLabel("Name: ");
		m_lblDesc   = new JLabel("Description: ");
		m_lblPrice  = new JLabel("Price: ");
		m_lblAmount = new JLabel("Amount: ");
	}

	private void SetupTextFields()
	{
		m_txtName   = new JTextField(15);
		m_txtDesc   = new JTextField(15);
		m_txtPrice  = new JTextField(15);
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
				String name = "";
				String desc = "";
				Double price = 0.0;
				Long amount = 0L;

				try 
				{
					name = m_txtName.getText();

					// the description could be empty
					desc = m_txtDesc.getText();

					if (name.isEmpty())
					{
						Utilities.ShowPopupWarn("Name text field cannot be empty");
						return;
					}

					String str_price = m_txtPrice.getText();
					if (str_price.isEmpty())
					{
						Utilities.ShowPopupWarn("Price text field cannot be empty");
						return;
					}

					String str_amount = m_txtAmount.getText();
					if (str_amount.isEmpty())
					{
						Utilities.ShowPopupWarn("Amount text field cannot be empty");
						return;
					}

					price = Double.parseDouble(str_price);
					amount = Long.parseLong(str_amount);
				}
				catch (NumberFormatException nfe)
				{
					Utilities.ShowPopupError("Invalid data format for text field: " + nfe.getMessage());
					return;
				}
				catch (Exception ee)
				{
					Utilities.ShowPopupError("Unknown Error Ocurred: " + ee.getMessage());
					return;
				}
				
				m_productDAO.Add(new Product(name, desc, price, amount));
				Utilities.ShowPopupInfo("Inserido com sucesso !");

				CloseThisWindow();
			}
		});
	}

	private void CloseThisWindow() 
	{
		//Window this_window = SwingUtilities.getWindowAncestor(m_txtName);
		WindowEvent CloseWindowEvent = new WindowEvent((Window) this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(CloseWindowEvent);
	}

}
