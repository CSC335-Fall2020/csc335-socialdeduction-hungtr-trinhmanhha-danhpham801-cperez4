package social_deduction;
import java.io.Serializable;
import java.util.Optional;

/**
 * The abstraction for a game message command. This 
 * makes writing handlers for different messages and parsing
 * easier by generalizing some procedures (parsing, help/info
 * of command)
 * 
 * Author: Hung Tran
 * 
 *
 */
public abstract class GameMessageCommand<A extends Serializable> {
	/**
	 * Parses param 'args' into runtime object type
	 * @param args
	 * @return
	 */
	public abstract A parse(Optional<Object> args);
	public StringBuilder addHelp(StringBuilder cs) {
		return cs;
	}
	
}
