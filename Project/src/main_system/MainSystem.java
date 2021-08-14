package main_system; // mudar isso aqui

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

import entities.*;
import entities.frames.*;

public class MainSystem 
{
    public static void main(String args[]) 
    {
        /* Obs: NAO TA PRONTO AINDA, TA QUASE... */

        ProductDAO dao = new ProductDAO();
        dao.RemoveAll();

        for (int i = 0; i < 5; ++i)
        {
            dao.Add(new Product("Mija Tool", "Screw", 467.56, 1L));
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

    public static void PrintProducts(ProductDAO dao)
    {
        for (Product p1 : dao.GetProducts())
            System.out.println(p1.toString());
    }

    public static void TestDAO()
    {
        ProductDAO prod_dao = new ProductDAO();
        
        // remove all registers
        prod_dao.RemoveAll();

        Product p = new Product("Cabo AAA", "teste", 12.5, 2L);
        
        prod_dao.Add(p);
        prod_dao.Add(new Product("Cabo FLAT", "teste", 243.5, 1L));
        prod_dao.Add(new Product("Cabo Par trancado", "teste", 22.5, 1L));

        PrintProducts(prod_dao);
        
        // remove
        prod_dao.Remove(new Product("Cabo FLAT", "teste", 243.5, 1L));
        PrintProducts(prod_dao);

        prod_dao.Edit(new Product(p.GetId(), "Cabo AAA Plus", "", 66.5, 1L));
        PrintProducts(prod_dao);

        System.out.println("\nIndex: " + 
        prod_dao.Find(new Product("Cabo Par trancado", "teste", 22.5, 1L)) + "\n");
    }
}
