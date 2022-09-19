import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NumberGuesser extends Application {

	private final static Integer minimum = 0;
	private final static Integer maximum = 100;
	private Integer higherThan;
	private Integer lowerThan;
	private Integer hiddenNumber;
	private Integer guessNumber;
	private int count = 0;
	
	public NumberGuesser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//setting up the number and variables
		higherThan = minimum;
		lowerThan = maximum;
		
		// storing higher than and lower than variables
		final Text higherThanText = new Text(minimum.toString());
		final Text lowerThanText = new Text(maximum.toString());
		
		hiddenNumber = (int) (Math.random() * (maximum)) + minimum; // random number between maximum and minimum
		System.out.println(hiddenNumber);
		
		// set up the border pane with a top, center and bottom
		BorderPane border = new BorderPane();
		Pane top = new Pane();
		top.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
		border.setTop(top);
		Pane left = new Pane();
		left.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		border.setLeft(left);
		Pane center = new Pane();
		center.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null)));
		border.setCenter(center);
		Pane right = new Pane();
		right.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
		border.setRight(right);
		Pane bottom = new Pane();
		border.setBottom(bottom);
		
		// adding nodes to the top pane
		// heading
		HBox topHBox = new HBox();
		top.getChildren().add(topHBox);
		// heading
		Text heading = new Text("What number is in the box?");
		topHBox.getChildren().add(heading);
		
		// textbox
		final TextField guessTextField = new TextField ();
		guessTextField.setPromptText("Guess");
		guessTextField.setPrefSize(80, 10);
		topHBox.getChildren().add(guessTextField);
			
		
		// guess button
		Button guess = new Button("Guess");
		guess.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// catch user input that is not a number and return
				try {
					guessNumber = Integer.parseInt(guessTextField.getText());
				} catch (NumberFormatException nfe) {
		            System.out.println("NumberFormat Exception: invalid number");
		            return;
		        }
				
				// check user input is within the current guessing range 
				if (guessNumber < higherThan || guessNumber > lowerThan) {
					System.out.println("Outside Range");
					return;
				}
				
				
				if (guessNumber.equals(hiddenNumber)) {
					
				} else if (guessNumber < hiddenNumber) {
					higherThan = guessNumber;
					higherThanText.setText(guessNumber.toString());
					guessTextField.setPromptText("Guess Again...");
					
				} else if (guessNumber > hiddenNumber) {
					lowerThan = guessNumber;
					lowerThanText.setText(guessNumber.toString());
					guessTextField.setPromptText("Guess Again...");
				}
			}
		});
		
		topHBox.getChildren().add(guess);
		
		

		
		// box image
		Image box = new Image(getClass().getResourceAsStream("/box.jfif"), 50,50,true,true);
		

		
		// adding nodes to the horizontal pane
		left.getChildren().add(higherThanText);
		left.getChildren().add(new Text("<"));
		center.getChildren().add(new ImageView(box));
		left.getChildren().add(new Text("<"));
		left.getChildren().add(lowerThanText);
		
		
		
		// number of guesses feedback line
		Text numberOfGuesses = new Text("Number of guesses: " + count);
		bottom.getChildren().add(numberOfGuesses);
		
		// set scene and show primaryStage
		Scene scene = new Scene(border, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Number Guesser");
		primaryStage.sizeToScene();
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);

	}

}
