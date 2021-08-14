package entities;

public class Product
{
    private Long   m_id = 0L;
    private String m_name;
    private String m_description;
    private double m_price;
    private Long   m_amount;

    public Product()
    {
    }

    public Product(Long id, String name, 
                   String description, double price, 
                   Long amount) 
    {
        m_id = id;
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

    public void SetName(String name) 
    {
        m_name = name;
    }

    public String GetDescription() 
    {
        return m_description;
    }

    public void SetDescription(String description)
    {
        m_description = description;
    }

    public double GetPrice()
    {
        return m_price;
    }

    public void SetPrice(double price) 
    {
        m_price = price;
    }

    public Long GetAmount() 
    {
        return m_amount;
    }

    public void SetAmount(Long amount) 
    {
        m_amount = amount;
    }

    public Long GetId() 
    {
        return m_id;
    }

    public void SetId(Long id) 
    {
        m_id = id;
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