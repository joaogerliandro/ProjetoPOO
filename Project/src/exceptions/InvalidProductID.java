package exceptions;

public class InvalidProductID extends RuntimeException 
{
	public InvalidProductID() 
	{
	}

	public InvalidProductID(String msg)
	{
		super(msg);
	}
}
