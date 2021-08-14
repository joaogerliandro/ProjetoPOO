package interfaces;

import java.util.*;
import entities.Product;

public interface IProductRepository
{
	/**
	 * Adds a product in the database
	 * @param target The product to be inserted
	 */
	public void Add(Product target);

	/**
	 * Get all products of database
	 * @return list of products extracted's of database
	 */
	public List<Product> GetProducts();

	/**
	 * Edit a product from database
	 * @param target The product to be updated
	 */
	public void Edit(Product target);

	/**
	 * Removes a product from the database from the ID
	 * @param product_id product id
	 */
	public void Remove(Long product_id);

	/**
	 * Removes a product from the database from the object Product
	 * @param target The product to be removed
	 * @return
	 */
	public boolean Remove(Product target);

	/**
	 * Remove all products of database
	 */
    public void RemoveAll();

	/**
	 * Find product in database
	 * @param target The product to be sought
	 * @return product database id
	 */
	public Long Find(Product target);

	/**
	 * Find product by id in database
	 * @param id product id
	 * @return if product is found, return product, else return Optional.empty()
	 */
	public Optional<Product> Find(Long id);
}