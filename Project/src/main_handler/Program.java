package main_handler;

import javax.swing.SwingUtilities;
import entities.*;
import entities.frames.*;

public class Program
{
    public static void main(String args[]) 
    {
        ProductDAO dao = new ProductDAO();

        // Clear all registers in database
        dao.RemoveAll();

        for (int i = 0; i < 50; ++i)
        {
            dao.Add(new Product("Monitor LG - LG29WK600", "A PC Monitor", 1800.0, 1L));
        }

		SetSkinTheme();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new FrmMain(dao);
            }
        });
    }

	private static void SetSkinTheme() 
	{
		try 
		{
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
                if ("Nimbus".equals(info.getName())) 
				{
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
		catch (ClassNotFoundException ex) 
		{
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
		catch (InstantiationException ex) 
		{
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
		catch (IllegalAccessException ex) 
		{
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
		catch (javax.swing.UnsupportedLookAndFeelException ex) 
		{
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
	}
}
