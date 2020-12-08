  import java.util.ArrayList;
  import java.util.List;
  import javafx.application.Application;
  import javafx.scene.Scene;
  import javafx.scene.control.ScrollPane;
  import javafx.scene.control.TextField;
  import javafx.scene.layout.BorderPane;
  import javafx.scene.layout.Pane;
  import javafx.scene.layout.VBox;
  import javafx.scene.text.Text;
  import javafx.stage.Stage;
  /**
   * @author Danh Pham
   * Course: CSC 335 Fall 2020
   * Created: Dec 5, 2020
   * File: GChatBox.java
   * Desc: the class will keep a log of what the group is saying.
   * 		 (the choice for a Graphical implementation)
   * 		  (class is only meant for visualizing the chat box)
   */
public class GChatBox extends Application {
	
	private Pane root = new Pane();
	private Scene scene;
	private BorderPane mainP = new BorderPane();
	private final VBox chatBox = new VBox(5);
	private List<Text> messages = new ArrayList<>();
	private ScrollPane container = new ScrollPane();
	private int index = 0;
	private TextField tf = new TextField();
	

	@Override
	public void start(Stage stage) throws Exception{
	    initChatBox();
	    root.getChildren().add(container);
	    mainP.setCenter(root);
	    mainP.setBottom(tf);
	    scene = new Scene(mainP,500,450);
	    stage.setScene(scene);
	    stage.show();
	}
	private void initChatBox(){
	    container.setPrefSize(300, 400);
	    container.setContent(chatBox); 
	    tf.setMaxSize(300, 100);
	    tf.setOnAction(evt->{
	    	Text t = new Text(tf.getText());
	    	t.setWrappingWidth(295);
	        messages.add(t);
	        chatBox.getChildren().add(messages.get(index));
	        tf.clear();
	        index++;
	    });
	}
	
	public static void main(String []args){
		launch(args);
	}
}

