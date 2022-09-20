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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
		
		
		// set up the border pane with a top, left, center, right and bottom
		BorderPane border = new BorderPane();
		Pane top = new Pane();
		top.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		border.setTop(top);
		
		HBox leftHBox = new HBox();
		leftHBox.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		leftHBox.setPrefWidth(200);
		border.setLeft(leftHBox);
		
		VBox center = new VBox();
		center.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		center.setAlignment(Pos.CENTER);
		border.setCenter(center);
		
		HBox rightHBox = new HBox();
		rightHBox.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		rightHBox.setPrefWidth(200);
		border.setRight(rightHBox);
		
		// adding nodes to the bottom pane	
		Label bottom = new Label("Number of guesses: " + count);
		bottom.setPrefHeight(50);
		border.setBottom(bottom);
		
		
		// adding nodes to the top pane
		// heading
		HBox topHBox = new HBox(10);
		top.getChildren().add(topHBox);
		// heading
		Text heading = new Text("What number is in the box?");
		topHBox.getChildren().add(heading);
		topHBox.setMargin(heading, new Insets(10));
		
		// textbox
		final TextField guessTextField = new TextField ();
		guessTextField.setPromptText("Guess");
		guessTextField.setPrefSize(80, 10);
		topHBox.getChildren().add(guessTextField);
		topHBox.setMargin(guessTextField, new Insets(10));	
		
		// guess button
		Button guess = new Button("Guess");
		guess.setOnMouseClicked(new EventHandler<MouseEvent>() {
		
			@Override
			public void handle(MouseEvent e) {
				// catch user input that is not a number and return
				try {
					guessNumber = Integer.parseInt(guessTextField.getText());
					count++;
					bottom.setText("Number of guesses: " + count);
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
		topHBox.setMargin(guess, new Insets(10));
	
		
		// adding nodes to the left pane
		leftHBox.getChildren().add(higherThanText);
		Label isLowerThan = new Label("<");
		leftHBox.getChildren().add(isLowerThan);
		leftHBox.setAlignment(Pos.CENTER);
		leftHBox.setMargin(higherThanText, new Insets(10));	
		leftHBox.setMargin(isLowerThan, new Insets(10));
		higherThanText.setFont(new Font(30));
		isLowerThan.setFont(new Font(30));
		
		
		// adding nodes to the center pane
		
		// box image
		Image box = new Image(getClass().getResourceAsStream("/box.jfif"), 150, 150,true,true);
		ImageView boxView = new ImageView(box);
		center.setPadding(new Insets(50));
		center.getChildren().add(boxView);
		VBox.setVgrow(boxView, Priority.ALWAYS);
		
		
		
		// adding nodes to the right pane
		Label isLowerThan2 = new Label("<");
		rightHBox.getChildren().add(isLowerThan2);
		rightHBox.getChildren().add(lowerThanText);
		rightHBox.setAlignment(Pos.CENTER);	
		rightHBox.setMargin(lowerThanText, new Insets(10));	
		rightHBox.setMargin(isLowerThan2, new Insets(10));
		lowerThanText.setFont(new Font(30));
		isLowerThan2.setFont(new Font(30));
		
		
		// set scene and show primaryStage
		Scene scene = new Scene(border);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Number Guesser");
		primaryStage.sizeToScene();
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);

	}

}
