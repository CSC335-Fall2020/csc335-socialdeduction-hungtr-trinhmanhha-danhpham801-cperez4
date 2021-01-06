package social_deduction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 28, 2020
 * File: SFuncTest.java
 * Desc:
 */

/**
 * 
 *
 */
public class SFuncTest {
	<T extends Serializable> T byteDeserialize(T o) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(ostream);
		oos.writeObject(o);
		byte[] buf = ostream.toByteArray();
		ByteArrayInputStream istream = new ByteArrayInputStream(buf);
		ObjectInputStream ois = new ObjectInputStream(istream);
		return (T)ois.readObject();
	}
	@Test
	public void ReadWriteSFunc() throws IOException, ClassNotFoundException {
		SerializableFunc<Boolean, Integer> isOdd
		= (Integer i) -> {
			return i % 2 == 1;
		};
		assertEquals(true, isOdd.run(1));
		assertFalse(isOdd.run(0));
		SerializableFunc<Boolean, Integer> deserializedIsOdd = 
				byteDeserialize(isOdd);
		assertEquals(true, deserializedIsOdd.run(1));
	}
	@Test
	public void StatedFuncTest() throws Exception {
		class Counter implements SerializableFunc<Integer, Integer>,
		Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int counter = 0;
			@Override
			public Integer run(Integer arg) {
				Integer retval = counter;
				counter += arg;
				return retval;
			}			
		}
		Counter counter = new Counter();
		assertEquals(0, (int)counter.run(5));
		assertEquals(5, (int)counter.run(10));
		assertEquals(15, (int)counter.run(0));
		assertEquals(15, (int)counter.run(99));
		assertEquals(114, (int)counter.run(-90));
		assertEquals(24, (int)counter.run(-24));
		assertEquals(0, (int)counter.run(5));
		// Counter dCounter = byteDeserialize((Counter)counter);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(counter);
		byte[] b = bos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Counter dCounter = (Counter) ois.readObject();
		assertEquals(5, (int) dCounter.run(-5));
		assertEquals(0, (int)dCounter.run(5));
		assertEquals(5, (int)dCounter.run(10));
		assertEquals(15, (int)dCounter.run(0));
		assertEquals(15, (int)dCounter.run(99));
		assertEquals(114, (int)dCounter.run(-90));
		assertEquals(24, (int)dCounter.run(-24));
		assertEquals(0, (int)dCounter.run(5));
		
	}
}
