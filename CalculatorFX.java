//package application;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/*
 * Wyatt Hoodes
 * +Assignment11, -displayResultsHandle, -operatorHandle
 * Core concept: ability to handle a javafx event
 * Date: 3/15/18
 */
public class CalculatorFX extends Application {
	
	//Global value from the firstField and secondField, result to be displayed, and numerical operator.
	private double firstValue;
	private double secondValue;
	private double displayableResult;
	private String operator;
	
	//Global TextFields to hold the users input values.
	private TextField firstField = new TextField();
	private TextField secondField = new TextField();
	
	//Global empty label designated to hold displayableResult.
	private Label result = new Label("");
	
	//Global operator buttons.
	private Button add = new Button("+");
	private Button subtract = new Button("-");
	private Button multiply = new Button("*");
	private Button divide = new Button("/");
	private Button equals = new Button("=");
	
	//Setting the stage.
	public void start(Stage stage) {

		//3 HBox's for all the rows except the result.
		HBox operatorBox = new HBox(5);
		HBox firstNumberBox = new HBox(29);
		HBox secondNumberBox = new HBox(20);
		
		//GridPane as the root node. Tweaked to desired specs.
		GridPane root = new GridPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setHgap(20);
		root.setVgap(15);
		root.setStyle("-fx-background-color: white;");
		
		//Place strings in the labels and adjust the font size of result.
		Label firstNumber = new Label("First number");
		Label secondNumber = new Label("Second number");
		result.setStyle("-fx-font-size: 15;");
		
		//Place the firstNumber and firstField in the firstBox, align, and add to root.
		firstNumberBox.getChildren().add(firstNumber);
		firstNumberBox.getChildren().add(firstField);
		firstNumberBox.setAlignment(Pos.BASELINE_RIGHT);
		root.add(firstNumberBox, 0, 0);

		/*
		 * Create a button array. In the for loop:
		 * Tweak the buttons, adjust color and border radius, etc...
		 * Add all of the operator buttons except "equals".
		 * For all of the buttons in the array, add events for:
		 * mouse entered, mouse exited, mouse pressed, and mouse released.
		 * 
		 * operatorHandle is set on action.
		 */
		Button[] buttons = {add, subtract, multiply, divide};
		
		for(Button button : buttons) {

			button.setStyle("-fx-padding: 15px 25px; -fx-background-radius: 20; -fx-background-color: white;"
					+ "-fx-border-color: white; -fx-border-radius: 20;");
			operatorBox.getChildren().add(button);
			
			button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					button.setEffect(new DropShadow());
				}
			});
			
			button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					button.setEffect(null);
				}
			});
			
			button.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					button.setEffect(new InnerShadow());
				}
			});
			
			button.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					button.setEffect(null);
				}
			});
			
			button.setOnAction(new operatorHandle());
			
		}
		
		//Add the operator box and position to center.
		root.add(operatorBox, 0, 1);
		operatorBox.setAlignment(Pos.CENTER);
		
		//Add the secondNumber and secondField to the secondNumberBox. Align and add to the root.
		secondNumberBox.getChildren().add(secondNumber);
		secondNumberBox.getChildren().add(secondField);
		secondNumberBox.setAlignment(Pos.BASELINE_RIGHT);
		root.add(secondNumberBox, 0, 2);
		
		//Equals button is the odd ball button child, so properties are applied uniquely.
		root.add(equals, 0, 3);
		equals.setStyle("-fx-padding: 15px 100px; -fx-background-radius: 20; -fx-background-color: white;"
				+ "-fx-border-color: white; -fx-border-radius: 20;");
		
		equals.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				equals.setEffect(new DropShadow());
			}
		});
		
		equals.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				equals.setEffect(null);
			}
		});
		
		equals.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				equals.setEffect(new InnerShadow());
			}
		});
		
		equals.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				equals.setEffect(null);
			}
		});
		
		//Align the "equals" button, add the result node to root, displayableResultHandle is set on action.
		GridPane.setHalignment(equals, HPos.CENTER);
		root.add(result, 0, 4);
		equals.setOnAction(new displayResultsHandle());
		
		//Set the scene with root, no resizing.
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setTitle("Simple Calculator 1.0");
		stage.setScene(scene);
		stage.show();
		
	}
	
	//Event handler for displaying the result of pressing the equals button.
	private class displayResultsHandle implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e) {
			
			try {
				
				//Capture the value in the second textField
				double secondNumber = Double.parseDouble(secondField.getText());
				secondValue = secondNumber;
				
				//If the first field is empty throw an exception.
				if(firstField.getText().isEmpty())
					throw new Exception();
				
				//Perform operation based on operator string value.
				if(operator == "add") {
					displayableResult = firstValue + secondValue;
				}else if(operator == "sub") {
					displayableResult = firstValue - secondValue;
				}else if(operator == "multiply") {
					displayableResult = firstValue * secondValue;
				}else if(operator == "div") {
					displayableResult = firstValue / secondValue;
				}
				
				//Display the result.
				result.setText(Double.toString(displayableResult));
				
			}catch(Exception ex) {
				result.setText("Oops..");
			}
		}
	}
	
	//EventHandler for mathematical operation buttons.
	private class operatorHandle implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent e) {
			
			try {
				
				//Capture the value in the first textField. Assign the global value to the number.
				double firstNumber = Double.parseDouble(firstField.getText());
				firstValue = firstNumber;
				
				//if-then block determining the operation.
				if(e.getSource() == add) 
					operator = "add";
				else if(e.getSource() == subtract) 
					operator = "sub";
				else if(e.getSource() == multiply)
					operator = "multiply";
				else if(e.getSource() == divide)
					operator = "div";
				
			}catch(Exception ex) {
				result.setText("Oops..");
			}
		}
	}
	
}
