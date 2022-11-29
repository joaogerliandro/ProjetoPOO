package entities;

public class Product extends IClonable<Product>
{
    private Long   m_id = 0L;
    private String m_name;
    private String m_description;
    private double m_price;
    private Long   m_amount;

    public Product()
	{
	}

	public Product(Product target) 
	{
		if (target != null) 
		{
			m_id          = target.m_id;
			m_name        = target.m_name;
			m_description = target.m_description;
			m_price       = target.m_price;
			m_amount      = target.m_amount;
        }
    }
	
	public Product Clone()
	{
		return new Product(this);
	}

    public Product(Long id, String name, 
                   String description, double price, 
                   Long amount) 
    {
        m_id          = id;
        m_name        = name;
        m_description = description;
        m_price       = price;
        m_amount      = amount;
    }

    public Product(String name, String description,
                   double price, Long amount) 
    {
        m_name        = name;
        m_description = description;
        m_price       = price;
        m_amount      = amount;
    }

    public String GetName()
    {
        return m_name;
    }

    public Product SetName(String name) 
    {
		m_name = name;
		return this;
    }

    public String GetDescription() 
    {
        return m_description;
    }

    public Product SetDescription(String description)
    {
		m_description = description;
		return this;
    }

    public double GetPrice()
    {
        return m_price;
    }

    public Product SetPrice(double price) 
    {
		m_price = price;
		return this;
    }

    public Long GetAmount() 
    {
        return m_amount;
    }

    public Product SetAmount(Long amount) 
    {
		m_amount = amount;
		return this;
    }

    public Long GetId() 
    {
        return m_id;
    }

    public Product SetId(Long id) 
    {
		m_id = id;
		return this;
    }

    @Override
    public String toString() 
    {
        return "Product [id=" + m_id + ", name=" + m_name + 
               ", desc=" + m_description + ", price=" + m_price +
               ", amount=" + m_amount + "]";
    }

    public static boolean Compare(Product src, Product dest)
    {
        if (src.GetName().compareTo(dest.GetName()) != 0)
            return false;

        if (src.GetDescription().compareTo(dest.GetDescription()) != 0)
            return false;

        if (src.GetPrice() != dest.GetPrice())
            return false;

        if (src.GetAmount() != dest.GetAmount())
            return false;

        return true;
    }
}
