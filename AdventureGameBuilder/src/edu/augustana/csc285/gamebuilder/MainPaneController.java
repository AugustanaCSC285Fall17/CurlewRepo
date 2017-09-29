//used this cite to help with converting text to int 
//https://stackoverflow.com/questions/5585779/how-to-convert-a-string-to-an-int-in-java
package edu.augustana.csc285.gamebuilder;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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

	@FXML
	private Button saveButton;
	@FXML
	private Button saveAsButton;
	@FXML 
	private Button loadButton;

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
	@FXML
	private Button removeSlideButton;

	// ActionChoiceEditor Fields
	private ActionChoiceEditor ace = new ActionChoiceEditor(data, se);
	@FXML
	private TextField selectSlideNumberTextField1;
	@FXML
	private Label currentSlideLabel1;
	@FXML
	private TextField selectActionChoiceTextField;
	@FXML
	private Label currentACLabel;
	@FXML
	private Button showAceInfoButton;
	@FXML
	private TextArea aceChoiceTextArea;
	@FXML
	private Button aceChoiceSubmitButton;
	@FXML
	private TextField aceSetDestinationSlideIndexField;
	@FXML
	private Button removeAcButton;

	// JavaFX initialize method, called after this Pane is created.
	@FXML
	private void initialize() {
		// Slide slide = new Slide();
	}

	@FXML
	private void handleSaveButton() {
		data.save();
	}

	// http://code.makery.ch/blog/javafx-dialogs-official/
	@FXML
	private void handleSaveAsButton() {
		TextInputDialog dialog = new TextInputDialog("Save As");
		dialog.setTitle("Save As");
		dialog.setContentText("Please enter desired name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			data.saveAs(result.get());
		} else {
			new Alert(AlertType.ERROR, "File not saved");
		}

	}
	
	@FXML
	private void handleLoadButton() throws IOException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chose a file to load");
		this.data=GameData.fromJSONFile(fileChooser.showOpenDialog(mainWindow));
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

	private boolean isIndexAnActionChoice(int index) {
		if (!wasSlideSelected()) {
			return false;
		}
		if (aceListIsNotEmpty()) {
			if (index < 0 || index > data.getSlide(se.getCurrentSlide()).getActionChoiceListSize() - 1) {
				new Alert(AlertType.ERROR, "There is no Action Choice at this index").showAndWait();
				return false;
			}
			return true;
		}
		return false;

	}

	@FXML
	private void handleShowSlideAtTextField() {
		String input = showSlideAtTextField.getText();
		if (isInputInt(input)) {
			slideAtTextIndex = Integer.parseInt(input);
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
		if (slideListIsNotEmpty()) {
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
		if (slideListIsNotEmpty() && wasSlideSelected()) {
			se.changeTitle(changeTitleTextField.getText());
		}
	}

	@FXML
	private void handleSubmitButton() {
		if (slideListIsNotEmpty() && wasSlideSelected()) {
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

	private boolean slideListIsNotEmpty() {
		if (data.getSlideListSize() == 0) {
			new Alert(AlertType.INFORMATION, "There are no slides in the list").showAndWait();
			return false;
		}
		return true;
	}

	private boolean aceListIsNotEmpty() {
		if (!slideListIsNotEmpty()) {
			return false;
		}
		if (data.getSlide(se.getCurrentSlide()).getActionChoiceListSize() == 0) {
			new Alert(AlertType.INFORMATION, "There are no actionChoices in the list").showAndWait();
			return false;
		}
		return true;
	}

	@FXML
	private void handleSelectSlideImageButton() {
		if (wasSlideSelected()) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Chose a Slide image");
			try {
				se.setSlideImage(fileChooser.showOpenDialog(mainWindow));
			} catch (IOException e) {
				new Alert(AlertType.ERROR, "There was a problem").showAndWait();
			}
		}
	}

	@FXML
	private void handleShowSlideInfoButton() {
		if (slideListIsNotEmpty() && wasSlideSelected()) {
			new Alert(AlertType.INFORMATION, data.getSlide(se.getCurrentSlide()).toString()).showAndWait();
		}
	}

	private boolean wasSlideSelected() {
		if (!se.wasSlideSelected()) {
			new Alert(AlertType.ERROR, "Please select a slide first").showAndWait();
			return false;
		}
		return true;
	}

	private boolean wasActionChoiceSelected() {
		if (ace.getCurrentActionChoiceIndex() == -1) {
			new Alert(AlertType.ERROR, "Please select an action choice first").showAndWait();
			return false;
		}
		return true;
	}

	@FXML
	private void handleSelectActionChoiceTextField() {
		if (this.wasSlideSelected()) {
			if (isInputInt(selectActionChoiceTextField.getText())) {
				int index = Integer.parseInt(selectActionChoiceTextField.getText());
				if (isIndexAnActionChoice(index)) {
					ace.setCurrentActionChoiceIndex(index);
					currentACLabel.setText(Integer.toString(index));
				}
			}
		}
	}

	@FXML
	private void handleShowAceInfoButton() {
		if (wasAcSelected()) {
			new Alert(AlertType.INFORMATION,
					data.getSlide(se.getCurrentSlide()).getActionChoicesAt(ace.currentActionChoiceIndex).toString())
							.showAndWait();
		}
	}

	@FXML
	private void handleAceChoiceSubmitButton() {
		if (wasAcSelected())
			ace.setChoiceText(aceChoiceTextArea.getText());
	}

	@FXML
	private void handleAceSetDestinationSlideIndexField() {
		if (wasAcSelected()) {
			String input = aceSetDestinationSlideIndexField.getText();
			if (isInputInt(input)) {
				int index = Integer.parseInt(input);
				if (isIndexASlide(index)) {
					ace.setDestinationSlideIndex(index);
				}
			}
		}
	}

	private boolean wasAcSelected() {
		if (ace.aceSelected()) {
			return true;
		} else
			new Alert(AlertType.ERROR, "Please select an action choice").showAndWait();
		return false;
	}

	@FXML
	private void handleRemoveSlideButton() {
		if (this.wasSlideSelected()) {
			se.removeSlide();
			currentSlideLabel.setText("N/A");
			currentSlideLabel1.setText("N/A");
			se.setCurrentSlide(-1);
			selectSlideNumberTextField.clear();
			selectActionChoiceTextField.clear();
			setGameTextArea.clear();
			changeTitleTextField.clear();
		}

	}

	@FXML
	private void handleRemoveAcButton() {
		if (this.wasAcSelected()) {
			ace.remove();
			this.currentACLabel.setText("N/A");
			selectActionChoiceTextField.clear();
			aceChoiceTextArea.clear();
			aceSetDestinationSlideIndexField.clear();
		}
	}

}
