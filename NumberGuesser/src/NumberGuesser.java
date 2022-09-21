import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NumberGuesser extends Application {

	
	// for switching scenes
	// https://www.youtube.com/watch?v=SB9AnciLmsw
	private Scene mainScene;
	
	private Scene gameOverScene;
	private VBox gameOverVBox;
	
	private final static Integer minimum = 0;
	private final static Integer maximum = 100;
	private Integer higherThan;
	private Integer lowerThan;
	private Integer hiddenNumber;
	private Integer guessNumber;
	private int count = 0;
	private Integer recordScore = null;
	private final TextField guessTextField = new TextField();
	private final Text lowerThanText = new Text();
	private final Text higherThanText = new Text();
	private final Label guessessFeedback = new Label();
	private final Text feedback = new Text();
	private final Text recordText = new Text("Record: -");
	
	
	public NumberGuesser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//setting up the number and variables
		higherThan = minimum;
		lowerThan = maximum;
		
		// storing higher than and lower than variables
		higherThanText.setText(minimum.toString());
		lowerThanText.setText(maximum.toString());
		
		hiddenNumber = (int) (Math.random() * (maximum)) + minimum; // random number between maximum and minimum
		System.out.println(hiddenNumber);
		
		
		// set up the border pane with a top, left, center, right and bottom
		BorderPane border = new BorderPane();
		Pane top = new Pane();
		top.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		border.setTop(top);
		
		VBox leftVBox = new VBox();
		leftVBox.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		leftVBox.setPrefWidth(200);
		border.setLeft(leftVBox);
		
		VBox center = new VBox();
		center.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		center.setAlignment(Pos.CENTER);
		border.setCenter(center);
		
		VBox rightVBox = new VBox();
		rightVBox.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		rightVBox.setPrefWidth(200);
		border.setRight(rightVBox);
		
		// adding nodes to the bottom pane	
		HBox bottom = new HBox();
		guessessFeedback.setText("Number of guesses: " + count);
		bottom.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		bottom.setPrefHeight(50);
		border.setBottom(bottom);
		bottom.getChildren().add(guessessFeedback);
		bottom.setMargin(guessessFeedback, new Insets(10));
		bottom.setAlignment(Pos.CENTER);
		
		bottom.getChildren().add(recordText);
		
		
		
		// adding nodes to the top pane
		// heading
		HBox topHBox = new HBox(10);
		top.getChildren().add(topHBox);
		// heading
		Text heading = new Text("What number is in the box?");
		heading.setFont(new Font(20));
		topHBox.getChildren().add(heading);
		topHBox.setMargin(heading, new Insets(10));
		
		// textbox
		guessTextField.setPromptText("Guess");
		guessTextField.setPrefSize(80, 10);
		topHBox.getChildren().add(guessTextField);
		topHBox.setMargin(guessTextField, new Insets(10));	
		
		// adding nodes to the center pane
		
		// box image
		Image box = new Image(getClass().getResourceAsStream("/box.jfif"), 150, 150,true,true);
		ImageView boxView = new ImageView(box);
		center.setPadding(new Insets(50));
		center.getChildren().add(boxView);
		VBox.setVgrow(boxView, Priority.ALWAYS);
		

		// rotating transition for the box image
		// from: https://www.youtube.com/watch?v=AizCyDQbdJc
		RotateTransition rotate = new RotateTransition();
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setByAngle(720);
		rotate.setCycleCount(1);
		rotate.setDuration(Duration.millis(1000));
		rotate.setNode(boxView);
		
		
		
		
		// guess button
		Button guess = new Button("Guess");
		guess.setOnMouseClicked(new EventHandler<MouseEvent>() {
		
			@Override
			public void handle(MouseEvent e) {
				// catch user input that is not a number and return
				
				// check for a valid number and break out of method if it's not
				try {
					guessNumber = Integer.parseInt(guessTextField.getText());
					count++;
					guessessFeedback.setText("Number of guesses: " + count);
				} catch (NumberFormatException nfe) {
					feedback.setText("Invalid number: try again!");
		            return;
		        }
				
				
				// check user input is within the current guessing range and break out of method if it's not
				if (guessNumber < higherThan) {
					feedback.setText("That's too low, try again!");
					return;
				}
				
				if (guessNumber > lowerThan) {
					feedback.setText("That's too high, try again!");
					return;
				}
				
				
				// if we get this far, play the box animation and set the feedback text to null
				rotate.play();
				feedback.setText("");
				
				if (guessNumber.equals(hiddenNumber)) {
					feedback.setText("You Win!");
					switchScenes(gameOverScene);
				} else if (guessNumber < hiddenNumber) {
					higherThan = guessNumber;
					higherThanText.setText(guessNumber.toString());
					guessTextField.setPromptText("Guess Again...");
				} else if (guessNumber > hiddenNumber) {
					rotate.play();
					lowerThan = guessNumber;
					lowerThanText.setText(guessNumber.toString());
					guessTextField.setPromptText("Guess Again...");
				}
				
				
			}
			
			public void switchScenes(Scene scene) {
				primaryStage.setScene(scene);
			}
			
		});
		
		// create game over scene
		gameOverVBox = new VBox();
		gameOverScene = new Scene(gameOverVBox);
		
		//
		Text youWin = new Text("YOU WIN!");
		Text yourScore = new Text("" + count);
		Button playAgain = new Button("Plan Again");
		gameOverVBox.getChildren().addAll(youWin, yourScore, playAgain);
		
		// set up button for game over scene to take back to the main screen and reset values
		playAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				resetValues();
				switchScenes(mainScene);
			}
			
			public void switchScenes(Scene scene) {
				primaryStage.setScene(scene);
			}
			
		});
		
		
		
		
		
		
		topHBox.getChildren().add(guess);
		topHBox.setMargin(guess, new Insets(10));
	
		
		topHBox.getChildren().add(feedback);
		topHBox.setMargin(feedback, new Insets(10));
		
		
		// adding nodes to the left pane
		Label isGreaterThan = new Label("It's greater than...");
		leftVBox.getChildren().add(isGreaterThan);
		leftVBox.getChildren().add(higherThanText);
		
		
		leftVBox.setAlignment(Pos.CENTER);
		leftVBox.setMargin(isGreaterThan, new Insets(10));
		leftVBox.setMargin(higherThanText, new Insets(10));	
		
		higherThanText.setFont(new Font(30));
		isGreaterThan.setFont(new Font(20));
		
		
		// adding nodes to the right pane
		Label isLowerThan = new Label("It's lower than...");
		rightVBox.getChildren().add(isLowerThan);
		rightVBox.getChildren().add(lowerThanText);
		rightVBox.setAlignment(Pos.CENTER);	
		rightVBox.setMargin(lowerThanText, new Insets(10));	
		rightVBox.setMargin(isLowerThan, new Insets(10));
		lowerThanText.setFont(new Font(30));
		isLowerThan.setFont(new Font(20));
		
		
		// set scene and show primaryStage
		mainScene = new Scene(border);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Number Guesser");
		primaryStage.sizeToScene();
		primaryStage.show();

	}
	
	public void resetValues() {
		if (recordScore != null ) {
			if (count < recordScore) {
				recordScore = count;
			}
		} else {
			recordScore = count;
		}
		count = 0;
		higherThan = minimum;
		lowerThan = maximum;
		hiddenNumber = (int) (Math.random() * (maximum)) + minimum; // random number between maximum and minimum
		System.out.println(hiddenNumber);
		guessNumber = null;
		count = 0;
		guessTextField.clear();
		guessTextField.setPromptText("Guess");
		higherThanText.setText(minimum.toString());
		lowerThanText.setText(maximum.toString());
		guessessFeedback.setText("Number of guesses: " + count);
		feedback.setText("");
		recordText.setText("Record: " + recordScore + " guesses");
	}

	public static void main(String[] args) {
		Application.launch(args);

	}

}
