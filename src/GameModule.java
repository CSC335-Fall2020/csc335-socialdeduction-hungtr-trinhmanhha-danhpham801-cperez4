import javafx.scene.layout.Pane;

/**
 * @author Hung Tran
 * <p>
 * Created: Dec 22, 2020
 * File: GameModule.java
 * Desc:
 */

/**
 * 
 *
 */
public interface GameModule {
	/**
	 * Attaches rendering widgets into 'rendererPane'
	 * @param rendererPane the main Pane that will be rendered
	 * by the GameRenderer (or viewer)
	 * @return a message type if necessary.
	 */
	public Object attachPane(Pane rendererPane);
}
