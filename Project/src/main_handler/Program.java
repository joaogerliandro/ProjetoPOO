package main_handler;

import entities.*;
import entities.frames.*;

import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class Program
{
	public static void AddDefaultProducts()
	{
		final Product GraphicsCardModel = new Product("", "graphics card for pc", 1000.0, 1L);
		
		Product Rtx3060TI = GraphicsCardModel.Clone()
											 .SetName("Rtx 3060 TI")
											 .SetPrice(2500);
						 
		ProductDAO.GetInstance().Add(new Product("SSD ORICO 1TB USB 3.2", "State-Solid-Drive", 1250.0, 1L));
		ProductDAO.GetInstance().Add(new Product("Xiaomi Airdots 3 Pro", "Intra-auricular headphone", 350.0, 2L));
        ProductDAO.GetInstance().Add(new Product("Microfone Gamer QuadCast", "Super bom", 350.0, 2L));
	    ProductDAO.GetInstance().Add(new Product("Teclado PiscaPisca Pichau ARGBX", "Keypad Gamer", 750, 1L));
        ProductDAO.GetInstance().Add(new Product("Mouse Sem Fio Mamba HyperFlux, Chroma, 16000DPI", "Mouse Gamer", 1000.0, 1L));
		ProductDAO.GetInstance().Add(new Product("Monitor Dell UltraSharp de 31.5 8K UP3218K", "", 32000.0, 1L));
		ProductDAO.GetInstance().Add(Rtx3060TI);
	}

    public static void main(String args[]) 
    {
        // Clear all registers in database
        ProductDAO.GetInstance().RemoveAll();
		
		AddDefaultProducts();
		SetSkinTheme();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new FrmMain();
            }
		});
    }

	private static void SetSkinTheme() 
	{
		final String frmMainClassName = FrmMain.class.getName();

		try 
		{
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
                if (info.getName().equals("Nimbus")) 
				{
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
		catch (ClassNotFoundException ex) 
		{
            Logger.getLogger(frmMainClassName).log(Level.SEVERE, null, ex);
        } 
		catch (InstantiationException ex) 
		{
            Logger.getLogger(frmMainClassName).log(Level.SEVERE, null, ex);
        } 
		catch (IllegalAccessException ex) 
		{
            Logger.getLogger(frmMainClassName).log(Level.SEVERE, null, ex);
        } 
		catch (UnsupportedLookAndFeelException ex) 
		{
            Logger.getLogger(frmMainClassName).log(Level.SEVERE, null, ex);
        }
	}
}
