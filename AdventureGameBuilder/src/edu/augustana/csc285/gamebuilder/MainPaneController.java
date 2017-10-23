//used this cite to help with converting text to int 
//https://stackoverflow.com/questions/5585779/how-to-convert-a-string-to-an-int-in-java
//http://code.makery.ch/blog/javafx-dialogs-official/
package edu.augustana.csc285.gamebuilder;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.imageio.ImageIO;

import edu.augustana.csc285.game.datamodel.*;

public class MainPaneController {

	// data and window fields
	private GameData data;
	private Stage mainWindow;
	private PreviewPaneController pController;

	// main tab fields
	@FXML
	private Button addSlideButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button saveAsButton;
	@FXML
	private Button loadButton;

	// Slide Editor Fields
	private SlideEditor se;
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
	private Button removeSlideButton;
	@FXML
	private ChoiceBox<SlideType> setSlideTypeChoiceBox;
	@FXML
	private ChoiceBox<Integer> selectSlideNumberChoiceBox;

	// ActionChoiceEditor Fields
	private ActionChoiceEditor ace;
	@FXML
	private Label currentSlideLabel1;
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
	@FXML
	private ChoiceBox<Integer> selectActionChoiceIndexChoiceBox;
	@FXML
	private ChoiceBox<String> effectChiceBox;
	@FXML
	private Button addEffectButton;
	@FXML
	private Button removeEffectButton;
	@FXML
	private ChoiceBox<String> conditionChoiceBox;
	@FXML
	private Button addConditionButton;
	@FXML
	private Button removeConditionButton;

	// Misc Editor Fields

	@FXML
	private Button addItemButton;

	@FXML
	private Button removeItemButton;

	// Starter Methods

	// JavaFX initialize method, called after this Pane is created.
	@FXML
	private void initialize() {
		createSlideTypeMenu();
		createEffectChoiceBox();
		createConditionChoiceBox();
	}

	/**
	 * Part of Constructor
	 * 
	 * @param primaryStage
	 * @param data
	 */
	public void setStageAndSetupListeners(Stage primaryStage, GameData data) {
		mainWindow = primaryStage;
		this.data = data;
		se = new SlideEditor(data);

		selectSlideNumberChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
					if (newValue != null) {
						changeCurrentSlide(newValue);
					}
				});

		selectActionChoiceIndexChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
					if (newValue != null) {
						changeActionChoice(newValue);
					}
				});
	}

	/**
	 * creates association between this class and an instance of
	 * PreviewPaneController
	 * 
	 * @param pController
	 */
	public void setPcontroler(PreviewPaneController pController) {
		this.pController = pController;
	}

	private void changeCurrentSlide(int index) {
		se.setCurrentSlide(index);
		currentSlideLabel.setText(Integer.toString(index));
		currentSlideLabel1.setText(Integer.toString(index));
		Slide slide = data.getSlide(se.getCurrentSlide());
		changeTitleTextField.setText(slide.getTitle());
		setGameTextArea.setText(slide.getGameText());
		setSlideTypeChoiceBox.setValue(slide.getSlideType());

	}

	// Checks

	/**
	 * Checks if string is an int, if not displays an error message
	 * 
	 * @param s
	 * @return
	 */
	private boolean isInputInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			new Alert(AlertType.ERROR, "This was not an int.").showAndWait();
			return false;
		}
	}

	/**
	 * checks to see if an entered index is valid, returns true if it is valid
	 * and false if not
	 * 
	 * @param index
	 * @return
	 */
	private boolean isIndexASlide(int index) {
		if (index < 0 || index > data.getSlideListSize() - 1) {
			new Alert(AlertType.ERROR, "There is no slide at this index").showAndWait();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * checks to see if the entered index is valid, returns true if it is valid
	 * and false if not
	 * 
	 * @param index
	 * @return
	 */
	private boolean isIndexAnActionChoice(int index) {
		if (!wasSlideSelected()) {
			return false;
		} else if (aceListIsNotEmpty()) {
			if (index < 0 || index > data.getSlide(se.getCurrentSlide()).getActionChoiceListSize() - 1) {
				new Alert(AlertType.ERROR, "There is no Action Choice at this index").showAndWait();
				return false;
			}
			return true;
		} else { // ace list is empty
			return false;
		}
	}

	/**
	 * 
	 * checks to see if the slide list is empty, returns true if it is not empty
	 * and false otherwise
	 * 
	 * @return
	 */
	private boolean slideListIsNotEmpty() {
		if (data.getSlideListSize() == 0) {
			new Alert(AlertType.INFORMATION, "There are no slides in the list").showAndWait();
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the action choice list is empty, returns true if it is
	 * not empty and false otherwise
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * checks to see if the user entered a slide index before editing other
	 * fields
	 * 
	 * @return
	 */
	private boolean wasSlideSelected() {
		if (!se.wasSlideSelected()) {
			new Alert(AlertType.ERROR, "Please select a slide first").showAndWait();
			return false;
		}
		return true;
	}

	/**
	 * returns true if an action choice has been selected, otherwise returns
	 * false.
	 * 
	 * @return
	 */
	private boolean wasAcSelected() {
		if (ace != null) {
			if (ace.aceSelected()) {
				return true;
			} else
				new Alert(AlertType.ERROR, "Please select an action choice").showAndWait();
			return false;
		}
		new Alert(AlertType.ERROR, "Please select an action choice").showAndWait();
		return false;
	}

	// Main tab methods

	// Save and load methods

	/**
	 * saves the data entered in the builder to a default or previously selected
	 * file
	 */
	@FXML
	private void handleSaveButton() {
		data.save();
	}

	/**
	 * http://code.makery.ch/blog/javafx-dialogs-official/ sets the save file to
	 * a new name and then saves the data to that file
	 */
	@FXML
	private void handleSaveAsButton() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Save As");
		dialog.setContentText("Please enter desired name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			data.saveAs(result.get());
		} else {
			new Alert(AlertType.ERROR, "File not saved");
		}
	}

	/**
	 * loads previously saved data from chosen file
	 * 
	 * @throws IOException
	 */
	@FXML
	private void handleLoadButton() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chose a file to load");
		File inFile = fileChooser.showOpenDialog(mainWindow);
		if (inFile == null) {

		} else {
			this.data = GameData.fromJSONFile(inFile);
			se = new SlideEditor(data);
			pController.updateData(data);
			clearSlideEditor();
			clearACE();
		}
	}

	// Other main tab methods

	/**
	 * adds a new slide to the end of the slide list in GameData class
	 */
	@FXML
	private void handleAddSlideButton() {
		data.addSlide(new Slide());
		pController.update();
		updateSlideNumberChoiceBox();
	}

	// SE methods

	/**
	 * creates the slide type menu in the slide editor tab
	 */
	private void createSlideTypeMenu() {
		ArrayList<SlideType> list = new ArrayList<SlideType>(Arrays.asList(SlideType.values()));
		ObservableList<SlideType> observableList = FXCollections.observableList(list);
		setSlideTypeChoiceBox.setItems(observableList);
	}

	/**
	 * Handles collecting all data from user on the slide editor tab
	 */
	@FXML
	private void handleSubmitButton() {
		if (wasSlideSelected()) {
			se.setGameText(setGameTextArea.getText());
			se.setSlideType((SlideType) setSlideTypeChoiceBox.getValue());
			se.changeTitle(changeTitleTextField.getText());
		}
		pController.update();
	}

	/**
	 * sets the image of the slide by calling on the slide editor class
	 */
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

	/**
	 * removes the slide that is currently being edited
	 */
	@FXML
	private void handleRemoveSlideButton() {
		if (this.wasSlideSelected()) {
			se.removeSlide();
			clearSlideEditor();
		}
		pController.update();
		updateSlideNumberChoiceBox();

	}

	/**
	 * sets slide editor tab to default
	 */
	private void clearSlideEditor() {
		currentSlideLabel.setText("N/A");
		currentSlideLabel1.setText("N/A");
		se.setCurrentSlide(-1);
		updateActionChoiceNumberChoiceBox();
		setGameTextArea.clear();
		changeTitleTextField.clear();
	}

	private void updateSlideNumberChoiceBox() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < data.getSlideListSize(); i++) {
			list.add(i);
		}
		ObservableList<Integer> observableList = FXCollections.observableList(list);
		selectSlideNumberChoiceBox.setItems(observableList);
	}

	// ACE methods

	/**
	 * adds an action choice to the end of the action choice list by calling on
	 * the slide editor class
	 */
	@FXML
	private void handleAddActionChoiceButton() {
		if (wasSlideSelected()) {
			se.addActionChoice();
		}
		pController.update();
		this.updateActionChoiceNumberChoiceBox();
	}

	public void changeActionChoice(int index) {
		ace = new ActionChoiceEditor(data.getSlide(se.getCurrentSlide()), se);
		ace.setCurrentActionChoiceIndex(index);
		currentACLabel.setText(Integer.toString(index));
		ActionChoice choice = data.getSlide(se.getCurrentSlide()).getActionChoicesAt(index);
		aceChoiceTextArea.setText(choice.getChoiceText());
		aceSetDestinationSlideIndexField.setText(Integer.toString(choice.getDestinationSlideIndex()));
	}

	/**
	 * Handles collecting all data from user on the action choice editor tab
	 */
	@FXML
	private void handleAceChoiceSubmitButton() {
		String input = aceSetDestinationSlideIndexField.getText();
		if (wasAcSelected())
			if (isInputInt(input)) {
				int index = Integer.parseInt(input);
				if (isIndexASlide(index)) {
					ace.setChoiceText(aceChoiceTextArea.getText());
					ace.setDestinationSlideIndex(index);
					pController.update();
				}
			}
	}

	/**
	 * sets Action Choice editor to default
	 */
	private void clearACE() {
		this.currentACLabel.setText("N/A");
		this.updateActionChoiceNumberChoiceBox();
		aceChoiceTextArea.clear();
		aceSetDestinationSlideIndexField.clear();
	}

	/**
	 * removes the action choice that is currently being edited
	 */
	@FXML
	private void handleRemoveAcButton() {
		if (this.wasAcSelected()) {
			clearACE();
			ace.remove();
			pController.update();
			updateActionChoiceNumberChoiceBox();
		}
	}

	/**
	 * displays information about the action choice in an alert message
	 */
	@FXML
	private void handleShowAceInfoButton() {
		if (wasAcSelected()) {
			ActionChoice choice = data.getSlide(se.getCurrentSlide()).getActionChoicesAt(ace.currentActionChoiceIndex);
			new Alert(AlertType.INFORMATION, choice.toString()).showAndWait();
		}
	}
	
	@FXML
	private void handleRemoveConditionButton(){
		
	}

	public void updateActionChoiceNumberChoiceBox() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (se.getCurrentSlide() != -1) {
			for (int i = 0; i < data.getSlide(se.getCurrentSlide()).getActionChoiceListSize(); i++) {
				list.add(i);
			}
		}
		ObservableList<Integer> observableList = FXCollections.observableList(list);
		selectActionChoiceIndexChoiceBox.setItems(observableList);
	}

	private void createEffectChoiceBox() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Gender Change Effect");
		list.add("Item Effect");
		list.add("Name Change Effect");
		ObservableList<String> observableList = FXCollections.observableList(list);
		effectChiceBox.setItems(observableList);
	}

	private void createConditionChoiceBox() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Gender Condition");
		list.add("Item Condition");
		ObservableList<String> observableList = FXCollections.observableList(list);
		conditionChoiceBox.setItems(observableList);

	}

	// TODO change to result.isPresent
	@FXML
	private void handleAddEffect() {
		if (this.wasAcSelected()) {
			if (effectChiceBox.getValue() == null) {
				new Alert(AlertType.ERROR, "Please Select an Effect Type").showAndWait();

			} else if (effectChiceBox.getValue().equals("Item Effect")) {

				if (data.getPlayer().getInventory().size() == 0) {// TODO move
																	// this
																	// check to
																	// ace
					new Alert(AlertType.ERROR, "There is nothing in the inventroy").showAndWait();
				} else {

					ArrayList<Item> inventory = data.getPlayer().getInventory();

					ArrayList<Integer> itemIndices = new ArrayList<Integer>();

					Alert itemInfo = new Alert(AlertType.INFORMATION);

					String s = "";
					for (int i = 0; i < inventory.size(); i++) {
						itemIndices.add(i);
						s += "Item " + inventory.get(i).toString() + " has index " + i + "\n";
					}
					itemInfo.setContentText(s);
					ChoiceDialog<Integer> effectDialog = new ChoiceDialog<Integer>(null, itemIndices);
					effectDialog.setContentText("Which item will be affected? Consult alert for refference");

					itemInfo.setX(90);
					itemInfo.setY(150);
					itemInfo.show();
					Optional<Integer> itemOptional = effectDialog.showAndWait();
					itemInfo.close();

					// TODO change to itemOptional.isPresent

					try {
						int itemChoice = itemOptional.get();

						if (ace.hasItemEffect(inventory.get(itemChoice))) {
							new Alert(AlertType.ERROR, "There is already an effect with that item").showAndWait();
						} else {
							////////// *************************************///////////////////////////////
							// method for this is below. Check to see if this is
							////////// what we want
							int effectChoiceSize = getChoiceSize("Effect");

							ace.addItemEffect(inventory.get(itemChoice), effectChoiceSize);
						}
					} catch (NumberFormatException e) {
						new Alert(AlertType.ERROR, "Must be a number").showAndWait();
					} catch (NoSuchElementException e1) {
						new Alert(AlertType.ERROR, "Effect not added").showAndWait();

					}
				}
			} else if (effectChiceBox.getValue().equals("Gender Change Effect")) {
				if (ace.hasGenderEffect()) {
					new Alert(AlertType.ERROR, "There is already a gender change effect for this action choice")
							.showAndWait();
				} else {
					Alert genderAlert = new Alert(AlertType.CONFIRMATION);
					genderAlert.setContentText("Which gender should the player be changed to?");

					// As of now only supports binary gender, as that is all
					// that is
					// required by history students and I don't want to confuse
					// them
					// if they use the builder
					ButtonType maleButton = new ButtonType("Male");
					ButtonType femaleButton = new ButtonType("Female");
					ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
					genderAlert.getButtonTypes().setAll(maleButton, femaleButton, cancelButton);

					genderAlert.setTitle("New Effect Specs");

					Optional<ButtonType> genderOptional = genderAlert.showAndWait();
					if (genderOptional.get().equals(maleButton)) {
						ace.addGenderChangeEffect(Gender.MALE);
					} else if (genderOptional.get().equals(femaleButton)) {
						ace.addGenderChangeEffect(Gender.FEMALE);
					} else {
						new Alert(AlertType.ERROR, "New Effect Canceled.");
					}
				}
			} else if (effectChiceBox.getValue().equals("Name Change Effect")) {
				if (ace.hasNameChangeEffect()) {
					new Alert(AlertType.ERROR, "There is already a name change effect for this action choice.")
							.showAndWait();
				} else {
					TextInputDialog nameDialog = new TextInputDialog();
					nameDialog.setContentText("Enter the new name.");
					nameDialog.setTitle("New Effect Specs");

					Optional<String> nameOptional = nameDialog.showAndWait();

					if (nameOptional.isPresent()) {
						String name = nameOptional.get();
						ace.addNameChangeEffect(name);
					} else {
						new Alert(AlertType.ERROR, "New Effect Canceled.").showAndWait();
					}
				}
			}
		}
		pController.update();
	}

	// ***** abstracted method from above ^^^^
	public int getChoiceSize(String EorC) throws NumberFormatException, NoSuchElementException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New" + EorC + "Specs");
		dialog.setHeaderText("Enter" + EorC + "Number");
		dialog.setContentText("Use positive numbers for adding and negitive numbers for subtracting");
		Optional<String> choiceSizeOptional = dialog.showAndWait();
		return Integer.parseInt(choiceSizeOptional.get());
	}

	public Item getItemFromUser() {
		ArrayList<Item> inventory = data.getPlayer().getInventory();

		ArrayList<Integer> itemIndices = new ArrayList<Integer>();

		Alert itemInfo = new Alert(AlertType.INFORMATION);

		String s = "";
		for (int i = 0; i < inventory.size(); i++) {
			itemIndices.add(i);
			s += "Item " + inventory.get(i).toString() + " has index " + i + "\n";
		}
		itemInfo.setContentText(s);
		ChoiceDialog<Integer> effectDialog = new ChoiceDialog<Integer>(null, itemIndices);
		effectDialog.setContentText("Which item? Consult alert for refference");

		itemInfo.setX(90);
		itemInfo.setY(150);
		itemInfo.show();
		Optional<Integer> itemOptional = effectDialog.showAndWait();
		itemInfo.close();

		if (itemOptional.isPresent()) {
			int i = itemOptional.get();
			return inventory.get(i);
		}
		new Alert(AlertType.ERROR, "Action not completed");
		return null;
	}

	@FXML

	public void handleRemoveEffectButton() {
		if (this.wasAcSelected()) {
			ArrayList<Effect> effects = ace.getEffects();

			ArrayList<Integer> effectIndices = new ArrayList<Integer>();

			Alert effectInfo = new Alert(AlertType.INFORMATION);

			String s = "";
			for (int i = 0; i < effects.size(); i++) {
				effectIndices.add(i);
				s += "Effect " + effects.get(i).toString() + " has index " + i + "\n";
			}
			effectInfo.setContentText(s);
			ChoiceDialog<Integer> effectDialog = new ChoiceDialog<Integer>(null, effectIndices);
			effectDialog.setContentText("Which effect will be removed? Consult alert for refference");

			effectInfo.setX(90);
			effectInfo.setY(150);
			effectInfo.show();
			Optional<Integer> effectOptional = effectDialog.showAndWait();
			effectInfo.close();

			if (effectOptional.isPresent()) {
				ace.removeEffect(effectOptional.get());
			} else {
				new Alert(AlertType.ERROR, "Effect not removed");
			}
		}
		pController.update();

	}
	// Misc Editor Methods

	// provides a series of dialoges to get info on new item
	@FXML
	public void handleAddItemButton() {
		TextInputDialog nameDialog = new TextInputDialog();
		nameDialog.setContentText("Enter the name of the Item");
		Optional<String> nameOptional = nameDialog.showAndWait();
		if (nameOptional.isPresent()) {
			String name = nameOptional.get();

			Alert visibleAlert = new Alert(AlertType.CONFIRMATION);
			visibleAlert.setContentText("Should this item be visible to the player?");
			ButtonType yesButton = new ButtonType("Yes");
			ButtonType noButton = new ButtonType("No");
			ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			visibleAlert.getButtonTypes().setAll(yesButton, noButton, buttonTypeCancel);
			Optional<ButtonType> visibleOptional = visibleAlert.showAndWait();
			Boolean visible;
			if (visibleOptional.get() == noButton) {
				visible = false;
				data.getPlayer().getInventory().add(new Item(name, visible));

			} else if (visibleOptional.get() == yesButton) {
				visible = true;

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Chose an image for the item");
				File inFile = fileChooser.showOpenDialog(mainWindow);
				if (inFile != null) {
					String path = "assets/" + inFile.getName();
					try {
						Files.copy(inFile.toPath(), (new File(path)).toPath(), StandardCopyOption.REPLACE_EXISTING);

						data.getPlayer().getInventory().add(new Item(name, visible, path));

					} catch (IOException e) {
						// should never happen, checked before, needed for
						// compile
						e.printStackTrace();
					}

				} else {
					new Alert(AlertType.ERROR, "No image was selected, item not created").showAndWait();
				}
			} else {
				new Alert(AlertType.ERROR, "Item not created").showAndWait();
			}

		} else {
			new Alert(AlertType.ERROR, "No name was entered").showAndWait();
		}
		pController.update();
	}

	@FXML
	// TODO change to strings instead of ints?
	public void handleRemoveItemButton() {
		ArrayList<Item> inventory = data.getPlayer().getInventory();
		Item item = getItemFromUser();

		if (data.itemUsed(item)) {
			Optional<ButtonType> inUseResponse = new Alert(AlertType.CONFIRMATION,
					"This item is in use. Removing it remove all effects and conditions using this item. Are you sure?")
							.showAndWait();
			// TODO support conditions removal

			if (inUseResponse.get() == ButtonType.OK) {
				data.removeItem(item);
			}
		} else {

			data.getPlayer().getInventory().remove(item);
		}
		pController.update();

	}

	@FXML
	public void handleAddConditionButton() {
		if (wasAcSelected()) {
			if (conditionChoiceBox.getValue() != null) {
				if (!(conditionChoiceBox.getValue().equals("Item Condition")// checks
																			// if
																			// inventory
																			// is
																			// empty
						&& data.getPlayer().getInventory().size() == 0)) {
					Alert conditionAlert = new Alert(AlertType.CONFIRMATION);
					conditionAlert.setContentText("Should this be a visibility or feasibility condition");
					ButtonType visibleButton = new ButtonType("Visibility");
					ButtonType feasibilityButton = new ButtonType("Feasibility");

					conditionAlert.getButtonTypes().setAll(visibleButton, feasibilityButton, ButtonType.CANCEL);
					Optional<ButtonType> conditionTypeOptional = conditionAlert.showAndWait();

					Boolean wasTypeSelected = true;
					int conditionType = -1;
					if (conditionTypeOptional.get() == visibleButton) {
						conditionType = ActionChoice.VISIBILITY;
					} else if (conditionTypeOptional.get() == feasibilityButton) {
						conditionType = ActionChoice.FEASIBILITY;
					} else {
						wasTypeSelected = false;
					}

					if (conditionChoiceBox.getValue().equals("Gender Condition") && wasTypeSelected) {
						Alert genderAlert = new Alert(AlertType.CONFIRMATION);
						genderAlert.setContentText("Should male or female be checked?");

						ButtonType maleButton = new ButtonType("Male");
						ButtonType femaleButton = new ButtonType("Female");
						// TODO once again only support binary gender for time
						// reasons

						genderAlert.getButtonTypes().setAll(maleButton, femaleButton, ButtonType.CANCEL);

						Optional<ButtonType> genderOptional = genderAlert.showAndWait();

						if (genderOptional.get() == maleButton) {
							ace.addGenderCondition(Gender.MALE, conditionType);
						} else if (genderOptional.get() == femaleButton) {
							ace.addGenderCondition(Gender.FEMALE, conditionType);
						}
					} else if (conditionChoiceBox.getValue().equals("Item Condition") && wasTypeSelected) {
						Item item = getItemFromUser();
						if (item != null) {

							try {
								int choiceSize = getChoiceSize("Condition");

								ArrayList<String> roList = new ArrayList<String>();
								roList.add("Less Than");
								roList.add("Less Than or Equal");
								roList.add("Greater Than");
								roList.add("Greater Than or Equal");
								roList.add("Equal");
								roList.add("Not Equal");

								ChoiceDialog<String> roDialog = new ChoiceDialog<String>(null, roList);
								Optional<String> roOptional = roDialog.showAndWait();

								if (roOptional.isPresent()) {
									RelationalOperator ro = null;
									if (roOptional.get().equals("Less Than")) {
										ro = RelationalOperator.LESS_THAN;
									} else if (roOptional.get().equals("Less Than or Equal")) {
										ro = RelationalOperator.LESS_THAN_OR_EQUAL;
									} else if (roOptional.get().equals("Greater Than")) {
										ro = RelationalOperator.GREATER_THAN;
									} else if (roOptional.get().equals("Greater Than or Equal")) {
										ro = RelationalOperator.GREATER_THAN_OR_EQUAL;
									} else if (roOptional.get().equals("Equal")) {
										ro = RelationalOperator.EQUAL;
									} else if (roOptional.get().equals("Not Equal")) {
										ro = RelationalOperator.NOT_EQUAL;
									}
									ace.addItemCondition(item, ro, choiceSize, conditionType);

								}
								// if(roOptional.isPresent())

							} catch (NumberFormatException e) {
								new Alert(AlertType.ERROR, "Must be a number").showAndWait();
							} catch(NoSuchElementException e){
								new Alert(AlertType.ERROR, "No number sumbited").showAndWait();
							}
						}else{
							new Alert(AlertType.ERROR, "No item selected").showAndWait();
						}
					}
				} else {
					new Alert(AlertType.ERROR, "Nothing in inventory").showAndWait();
				}

				// TODO set up so only one condition can be applied (where
				// applicable)

				// TODO change remove item in gameData to also remove all item
				// conditions of that item

				// TODO change default slide index to 0 so doesn't break;
				//TODO fix file path of that image thing that I didn't know where to put before
			} else {
				new Alert(AlertType.ERROR, "Please select a condition").showAndWait();
			}
		}
		pController.update();
	}
	// File Menu Methods

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

}
