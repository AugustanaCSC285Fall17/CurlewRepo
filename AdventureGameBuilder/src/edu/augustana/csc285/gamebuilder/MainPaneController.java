//used this cite to help with converting text to int 
//https://stackoverflow.com/questions/5585779/how-to-convert-a-string-to-an-int-in-java
package edu.augustana.csc285.gamebuilder;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	private GameData data;
	private int slideAtTextIndex = 0;
	@FXML
	private TextField showSlideAtTextField;
	@FXML
	private Button addSlideButton;
	@FXML
	private Button showSlideListButton;

	private Stage mainWindow;
	private Stage secondWindow;
	private PreviewPaneController pController;
	
	
	@FXML
	private Button saveButton;
	@FXML
	private Button saveAsButton;
	@FXML
	private Button loadButton;
	@FXML
	private Button startPreviewButton;

	// Slide Editor Fields
	private SlideEditor se;
	@FXML
	private TextField selectSlideNumberTextField;
	@FXML
	private Label currentSlideLabel;
	@FXML
	private TextField changeTitleTextField;
	@FXML
	private TextField changeSlideTypeTextField;
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
		
	}

	// saves the data entered in the builder
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

	// loads previously saved data
	@FXML
	private void handleLoadButton() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chose a file to load");
		File inFile = fileChooser.showOpenDialog(mainWindow);
		if (inFile == null) {

		} else {
			this.data = GameData.fromJSONFile(inFile);
			se = new SlideEditor(data);
			ace = new ActionChoiceEditor(data, se);
		}
	}

	public void setStageAndSetupListeners(Stage primaryStage, GameData data) {
		mainWindow = primaryStage;
		this.data = data;
		se = new SlideEditor(data);
	}
	
	public void setPcontroler(PreviewPaneController pController){
		this.pController = pController;
	}

	// checks to see if the input is an integer
	private boolean isInputInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			new Alert(AlertType.ERROR, "This was not an int.").showAndWait();
			return false;
		}
	}

	// https://stackoverflow.com/questions/15041760/javafx-open-new-window
	@FXML
	private void handleStartPreviewButton() {
//		PreviewPaneApp.main(args);
	}

	// checks to see if an entered index is valid, returns true if it
	// is valid and false if not
	private boolean isIndexASlide(int index) {
		if (index < 0 || index > data.getSlideListSize() - 1) {
			new Alert(AlertType.ERROR, "There is no slide at this index").showAndWait();
			return false;
		}
		return true;
	}

	// checks to see if the entered index is valid, returns true if it is valid
	// and false if not
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

	// outputs the information of a specific slide in an alert message
	@FXML
	private void handleShowSlideAtTextField() {
		String input = showSlideAtTextField.getText();
		if (isInputInt(input)) {
			slideAtTextIndex = Integer.parseInt(input);
			if (isIndexASlide(slideAtTextIndex)) {
				new Alert(AlertType.INFORMATION, data.getSlide(slideAtTextIndex).toString()).showAndWait();
			}
		}
		pController.update();
	}

	// adds a new slide to the end of the slide list in GameData class
	@FXML
	private void handleAddSlideButton() {
		data.addSlide(new Slide());
		pController.update();
	}

	// outputs the list of slides and their titles in an alert message
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

	// closes the game builder
	@FXML
	private void handleMenuFileClose() {
		// TODO: eventually offer option to save before closing?
		Platform.exit();
	}

	// does nothing now, but could display a help message or about message
	@FXML
	private void handleMenuHelpAbout() {
		new Alert(AlertType.INFORMATION, "Placeholder for about screen").showAndWait();
	}

	// calls the changeCurrentSlide method and passes in the text
	// that was put into the selectSlideNumberTextField
	@FXML
	private void handleSelectSlideNumberTextField() {
		changeCurrentSlide(selectSlideNumberTextField.getText());
	}

	// calls the changeCurrentSlide method and passes in the text
	// that was put into the selectSlideNumberTextField1
	@FXML
	private void handleSelectSlideNumberTextField1() {
		changeCurrentSlide(selectSlideNumberTextField1.getText());
	}

	// changes the current slide label on both the slide editor tab
	// and also the action choice editor tab
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

	// changes the title of the slide at the specified index by calling
	// the slide editor class
	@FXML
	private void handleChangeTitleTextField() {
		if (slideListIsNotEmpty() && wasSlideSelected()) {
			se.changeTitle(changeTitleTextField.getText());
		}
		pController.update();
	}

	// sets the game text of the slide that is entered into the
	// setGameTextArea text area
	@FXML
	private void handleSubmitButton() {
		if (slideListIsNotEmpty() && wasSlideSelected()) {
			se.setGameText(setGameTextArea.getText());
		}
		pController.update();

	}

	// adds an action choice to the end of the action choice list by calling on
	// the
	// slide editor class
	@FXML
	private void handleAddActionChoiceButton() {
		if (wasSlideSelected()) {
			se.addActionChoice();
		}
	}

	// checks to see if the slide list is empty, returns true if it is not empty
	// and
	// false otherwise
	private boolean slideListIsNotEmpty() {
		if (data.getSlideListSize() == 0) {
			new Alert(AlertType.INFORMATION, "There are no slides in the list").showAndWait();
			return false;
		}
		return true;
	}

	// checks to see if the action choice list is empty, returns true if it is
	// not empty and
	// false otherwise
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

	// sets the image of the slide by calling on the slide editor class
	@FXML
	private void handleSelectSlideImageButton() {
		if (wasSlideSelected()) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Chose a Slide image");
			try {
				File inFile = fileChooser.showOpenDialog(mainWindow);
				if (inFile == null) {

				} else {
					se.setSlideImage(inFile);
				}
			} catch (IOException e) {
				new Alert(AlertType.ERROR, "There was a problem").showAndWait();
			}
		}
		
		pController.update();

	}

	// outputs the information of a slide in a alert message
	@FXML
	private void handleShowSlideInfoButton() {
		if (slideListIsNotEmpty() && wasSlideSelected()) {
			new Alert(AlertType.INFORMATION, data.getSlide(se.getCurrentSlide()).toString()).showAndWait();
		}
	}

	@FXML
	private void handleChangeSlideTypeTextField() {
		String input = changeSlideTypeTextField.getText();
		se.setSlideType(Integer.parseInt(input));
	}

	// checks to see if the user entered a slide index before editing other
	// fields
	private boolean wasSlideSelected() {
		if (!se.wasSlideSelected()) {
			new Alert(AlertType.ERROR, "Please select a slide first").showAndWait();
			return false;
		}
		return true;
	}

	// checks to see if the user entered a action choice index before editing
	// other fields
	private boolean wasActionChoiceSelected() {
		if (ace.getCurrentActionChoiceIndex() == -1) {
			new Alert(AlertType.ERROR, "Please select an action choice first").showAndWait();
			return false;
		}
		return true;
	}

	// takes the input of the text field, changes the label to match, and also
	// changes
	// the current index to match
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

	// displays information about the action choice in an alert message
	@FXML
	private void handleShowAceInfoButton() {
		if (wasAcSelected()) {
			new Alert(AlertType.INFORMATION,
					data.getSlide(se.getCurrentSlide()).getActionChoicesAt(ace.currentActionChoiceIndex).toString())
							.showAndWait();
		}
	}

	// changes the action choice text by calling on the ActionChoiceEditor class
	@FXML
	private void handleAceChoiceSubmitButton() {
		if (wasAcSelected())
			ace.setChoiceText(aceChoiceTextArea.getText());
	}

	// sets the destination slide index of an action choice
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

	// checks to see if the user selected an action choice index
	private boolean wasAcSelected() {
		if (ace.aceSelected()) {
			return true;
		} else
			new Alert(AlertType.ERROR, "Please select an action choice").showAndWait();
		return false;
	}

	// removes the slide that is currently being edited
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
		
		pController.update();


	}

	// removes the action choice that is currently being edited
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
