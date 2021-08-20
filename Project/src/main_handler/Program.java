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

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new FrmMain(dao);
            }
        });
    }
}
