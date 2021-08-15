package exceptions;

public class EmptyFieldException extends RuntimeException 
{
	public EmptyFieldException() 
	{
	}

	public EmptyFieldException(String msg)
	{
		super(msg);
	}
}
