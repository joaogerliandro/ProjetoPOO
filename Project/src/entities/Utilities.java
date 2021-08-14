package entities;
import java.util.Vector;
import java.util.List;

public final class Utilities 
{
	public static <T> Vector<T> ToVector(List<T> values)
    {
        Vector<T> container = new Vector<>(values.size());
        for (T value : values)
            container.add(value);
        return container;
    }

	public static <T> Vector<T> ToVector(T[] values)
    {
        Vector<T> container = new Vector<>(values.length);
        for (T value : values)
            container.add(value);
        return container;
    }
}
