//used this cite to help with converting text to int https://stackoverflow.com/questions/5585779/how-to-convert-a-string-to-an-int-in-java
package edu.augustana.csc285.gamebuilder;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.augustana.csc285.game.datamodel.*;

public class MainPaneController {

	@FXML
	private TextField showSlideAtTextField;
	@FXML
	private Button addSlideButton;
	@FXML
	private Button showSlideListButton;
	private GameData data = new GameData();
	private int slideAtTextIndex = 0;
	private Stage mainWindow;

	// Slide Editor Fields
	private SlideEditor se = new SlideEditor(data);
	@FXML
	private TextField selectSlideNumberTextField;
	@FXML
	private Label currentSlideLabel;
	@FXML
	private TextField changeTitleTextField;
	@FXML
	private TextArea setGameTextArea;
	@FXML
	private Button submitButton;
	@FXML
	private Button selectSlideImageButton;
	@FXML
	private Button addActionChoiceButton;
	@FXML
	private Button showSlideInfoButton;

	// ActionChoiceEditor Fields
	@FXML
	private TextField selectSlideNumberTextField1;
	@FXML
	private Label currentSlideLabel1;

	// JavaFX initialize method, called after this Pane is created.
	@FXML
	private void initialize() {
		// Slide slide = new Slide();
	}

	public void setStageAndSetupListeners(Stage primaryStage) {
		mainWindow = primaryStage;

	}

	private boolean isInputInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			new Alert(AlertType.ERROR, "This was not an int.").showAndWait();
			return false;
		}
	}

	private boolean isIndexASlide(int index) {
		if (index < 0 || index > data.getSlideListSize() - 1) {
			new Alert(AlertType.ERROR, "There is no slide at this index").showAndWait();
			return false;
		}
		return true;
	}

	@FXML
	private void handleShowSlideAtTextField() {
		if (isInputInt(showSlideAtTextField.getText())) {
			slideAtTextIndex = Integer.parseInt(showSlideAtTextField.getText());
			if (isIndexASlide(slideAtTextIndex)) {
				new Alert(AlertType.INFORMATION, data.getSlide(slideAtTextIndex).toString()).showAndWait();
			}
		}
	}

	@FXML
	private void handleAddSlideButton() {
		data.addSlide(new Slide());
	}

	@FXML

	private void handleShowSlideListButton() {
		String s = "";
		if (listIsNotEmpty()) {
			for (int i = 0; i < data.getSlideListSize(); i++) {
				s += "Slide " + i + " is " + data.getSlide(i).getTitle() + "\n";
			}
			new Alert(AlertType.INFORMATION, s).showAndWait();
		}
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

	@FXML
	private void handleSelectSlideNumberTextField() {
		changeCurrentSlide(selectSlideNumberTextField.getText());
	}

	@FXML
	private void handleSelectSlideNumberTextField1() {
		changeCurrentSlide(selectSlideNumberTextField1.getText());
	}

	private void changeCurrentSlide(String input) {
		if (isInputInt(input)) {
			int index = Integer.parseInt(input);
			if (isIndexASlide(index)) {
				se.setCurrentSlide(index);
				currentSlideLabel.setText(Integer.toString(index));
				currentSlideLabel1.setText(Integer.toString(index));
			}
		}
	}

	@FXML
	private void handleChangeTitleTextField() {
		if (listIsNotEmpty() && wasSlideSelected()) {
			se.changeTitle(changeTitleTextField.getText());
		}
	}

	@FXML
	private void handleSubmitButton() {
		if (listIsNotEmpty() && wasSlideSelected()) {
			se.setGameText(setGameTextArea.getText());
		}
	}

	// have slide index be a field as an int
	// set choice text also!!!!!
	@FXML
	private void handleAddActionChoiceButton() {
		if (wasSlideSelected()) {
			se.addActionChoice();
		}
	}

	private boolean listIsNotEmpty() {
		if (data.getSlideListSize() == 0) {
			new Alert(AlertType.INFORMATION, "There are no slides in the list").showAndWait();
			return false;
		}
		return true;
	}

	@FXML
	private void handleSelectSlideImageButton() {
		if (wasSlideSelected()) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Chose a Slide image");
			// try{
			// se.setSlideImage(fileChooser.showOpenDialog(mainWindow));
			// }catch(IOException e){
			// new Alert(AlertType.ERROR, "There was a problem").showAndWait();
			// }
		}
	}

	@FXML
	private void handleShowSlideInfoButton() {
		if (listIsNotEmpty() && wasSlideSelected()) {
			new Alert(AlertType.INFORMATION, data.getSlide(se.getCurrentSlide()).toString()).showAndWait();
		}
	}

	public boolean wasSlideSelected() {
		if (se.getCurrentSlide() == -1) {
			new Alert(AlertType.ERROR, "Please select a slide first").showAndWait();
			return false;
		}
		return true;
	}

}
