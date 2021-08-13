package main_system;

//import javax.swing.JOptionPane;

import entities.Product;
import entities.ProductDAO;

public class MainSystem 
{
    public static void main(String args[]) 
    {
        ProductDAO prod_dao = new ProductDAO();
        Product p = new Product("Cabo RJ45", 44.5, 1L, "teste");
        
        // adiciona no banco
        prod_dao.Add(p);
    }
}
