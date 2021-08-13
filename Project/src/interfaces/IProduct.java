package interfaces;

import java.util.List;
import entities.Product;

public interface IProduct
{
	public void Add(Product p);
	public List<Product> GetProducts();
    public void Edit(Product p);
    public void Remove(Long product_id);
}