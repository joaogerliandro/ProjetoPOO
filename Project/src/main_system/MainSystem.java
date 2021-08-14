package main_system; // mudar isso aqui

import entities.*;

public class MainSystem 
{
    public static void main(String args[]) 
    {
        TestDAO();
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
