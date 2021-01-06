package social_deduction;
import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 17, 2020
 * File: SerializableFunc.java
 * Desc: An interface alias that is a functor, and can be serialized
 */

/**
 * 
 *
 */
public interface SerializableFunc<R,A> extends Serializable {
	public R run(A arg);
}
