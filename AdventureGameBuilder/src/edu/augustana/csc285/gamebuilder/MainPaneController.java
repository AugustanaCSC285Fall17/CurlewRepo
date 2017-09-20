//used this cite to help with converting text to int https://stackoverflow.com/questions/5585779/how-to-convert-a-string-to-an-int-in-java
package edu.augustana.csc285.gamebuilder;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import edu.augustana.csc285.game.datamodel.*;

public class MainPaneController {

<<<<<<< HEAD
	@FXML private TextField showSlideAtTextField;
	@FXML private Button addSlideButton;
	@FXML private Button showSlideAtButton;
	private GameData data = new GameData();
	private int slideAtTextIndex = 0; //Note Change this later, will cause problems if list is empty
	
=======
	@FXML
	private TextField showSlideAtTextField;
	@FXML
	private Button addSlideButton;
	@FXML
	private Button showSlideListButton;
	private GameData data = new GameData();
	private int slideAtTextIndex = 0; // Note Change this later, will cause
										// problems if list is empty

>>>>>>> d2f0b2f7bfc0f0e725192fabde7ffaea29a25b7f
	// JavaFX initialize method, called after this Pane is created.
	@FXML
	private void initialize() {
		// Slide slide = new Slide();
	}

	@FXML
	private void handleShowSlideAtTextField() {
		try {
			slideAtTextIndex = Integer.parseInt(showSlideAtTextField.getText());
			if (slideAtTextIndex < 0 || slideAtTextIndex > data.getSlideListSize() - 1) {
				new Alert(AlertType.ERROR, "There is no slide at this index").showAndWait();
			} else {
				new Alert(AlertType.INFORMATION, data.getSlide(slideAtTextIndex).toString()).showAndWait();
			}
		} catch (NumberFormatException e) {
			new Alert(AlertType.ERROR, "This was not an int.").showAndWait();
		}

	}

	@FXML
	private void handleAddSlideButton() {
		data.addSlide(new Slide());
	}

	@FXML
<<<<<<< HEAD
	private void handleShowSlideAtTextField(){
		try{
		slideAtTextIndex = Integer.parseInt(showSlideAtTextField.getText());
		new Alert(AlertType.INFORMATION,data.getSlide(slideAtTextIndex).toString()).showAndWait();
		} catch (NumberFormatException e) {
			new Alert(AlertType.INFORMATION,"This was not an int.").showAndWait();
		}
	}
	
	@FXML
	private void handleAddSlideButton() {
		data.addSlide(new Slide());
	}
	@FXML
	private void handleShowSlideAtButton() {
	//	new Alert(AlertType.INFORMATION, )
=======
	private void handleShowSlideListButton() {
		String s = "";
		if (data.getSlideListSize() == 0) {
			new Alert(AlertType.INFORMATION, "There are no slides in the list").showAndWait();
		} else {
			for (int i = 0; i < data.getSlideListSize(); i++) {
				s += "Slide " + i + " is " + data.getSlide(i).getTitle() + "\n";
			}
			new Alert(AlertType.INFORMATION, s).showAndWait();
		}
>>>>>>> d2f0b2f7bfc0f0e725192fabde7ffaea29a25b7f
	}

	@FXML
	private void handleMenuFileClose() {
		// TODO: eventually offer option to save before closing?
		Platform.exit();
	}

	@FXML
	private void handleMenuHelpAbout() {
		new Alert(AlertType.INFORMATION, "Placeholder for about screen").showAndWait();
	}

}
