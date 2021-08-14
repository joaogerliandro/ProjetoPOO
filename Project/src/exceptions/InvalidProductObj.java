package exceptions;

public class InvalidProductObj extends RuntimeException 
{
	public InvalidProductObj() 
	{
	}

	public InvalidProductObj(String msg)
	{
		super(msg);
	}
}
