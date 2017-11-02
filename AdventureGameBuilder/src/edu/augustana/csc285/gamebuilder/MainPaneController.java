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
import javafx.scene.control.CheckBox;
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
	private Boolean saved = true;

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
	@FXML
	private Button toggleGameOverButton;

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

	// Item Editor Fields

	private ItemEditor ie = new ItemEditor();

	@FXML
	private Button removeItemButton;

	@FXML
	private Button additemButton;
	@FXML
	private TextField itemNameTextField;
	@FXML
	private ChoiceBox<Item> itemChoiceBox;
	@FXML
	private Label currentItemLabel;
	@FXML
	private CheckBox itemVisibleCheckBox;
	@FXML
	private Label itemImageLabel;
	@FXML
	private Button selectAnItemImageButton;
	@FXML
	private Label currentSellPriceLabel;
	@FXML
	private Label currentBuyPriceLabel;
	@FXML
	private CheckBox sellableCheckBox;
	@FXML
	private CheckBox buyableCheckBox;
	@FXML
	private Button setSellPriceButton;
	@FXML
	private Button setBuyPriceButton;

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
						this.updateActionChoiceNumberChoiceBox();
					}
				});

		selectActionChoiceIndexChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
					if (newValue != null) {
						changeActionChoice(newValue);
					}
				});
		itemChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Item> observable, Item oldItem, Item newItem) -> {
					if (newItem != null) {
						changeItem(newItem);
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

	/**
	 * 
	 * @return whether the game data object has been saved by the user
	 */
	public Boolean isSaved() {
		return saved;
	}

	// Checks

	
	private boolean isInputInt(String s) {
		try {
			int i = Integer.parseInt(s); //if fails, will trip the try/catch
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


	private boolean wasSlideSelected() {
		if (!se.wasSlideSelected()) {
			new Alert(AlertType.ERROR, "Please select a slide first").showAndWait();
			return false;
		}
		return true;
	}

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


	@FXML
	private void handleSaveButton() {
		data.save();
		saved = true;
	}


	@FXML
	private void handleSaveAsButton() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Save As");
		dialog.setContentText("Please enter desired name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			data.saveAs(result.get());
			saved = true;
		} else {
			new Alert(AlertType.ERROR, "File not saved");
		}
	}


	@FXML
	private void handleLoadButton() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chose a file to load");
		File inFile = fileChooser.showOpenDialog(mainWindow);
		if (inFile == null) {

		} else {
			this.data = GameBuilderApplication.loadGameDataFromJSONFile(inFile);
			se = new SlideEditor(data);
			pController.updateData(data);
			clearSlideEditor();
			clearACE();
			updateSlideNumberChoiceBox();
			updateActionChoiceNumberChoiceBox();
			updateItemChoiceBox();
			saved = true;

		}
	}

	// Other main tab methods


	@FXML
	private void handleAddSlideButton() {
		data.addSlide(new Slide());
		pController.update();
		updateSlideNumberChoiceBox();
		saved = false;
	}

	// SE methods

	private void createSlideTypeMenu() {
		ArrayList<SlideType> list = new ArrayList<SlideType>(Arrays.asList(SlideType.values()));
		ObservableList<SlideType> observableList = FXCollections.observableList(list);
		setSlideTypeChoiceBox.setItems(observableList);
	}

	
	@FXML
	private void handleSubmitButton() {
		if (wasSlideSelected()) {
			se.setGameText(setGameTextArea.getText());
			se.setSlideType((SlideType) setSlideTypeChoiceBox.getValue());
			se.changeTitle(changeTitleTextField.getText());
			saved = false;
		}
		pController.update();
	}

	
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
					saved = false;
				}
			} catch (IOException e) {
				new Alert(AlertType.ERROR, "There was a problem").showAndWait();
			}
		}
		pController.update();
	}

	
	@FXML
	private void handleRemoveSlideButton() {
		if (this.wasSlideSelected()) {
			se.removeSlide();
			clearSlideEditor();
			saved = false;
		}
		pController.update();
		updateSlideNumberChoiceBox();

	}

	
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

	@FXML
	private void handleToggleGameOverButton() {
		if (wasSlideSelected()) {
			se.setGameOver(!se.isGameOver());
			if (se.isGameOver()) {
				new Alert(AlertType.INFORMATION, "This slide is now the game over slide").showAndWait();
			} else {
				new Alert(AlertType.INFORMATION, "This slide is no longer the game over slide").showAndWait();
			}
			saved = false;
			pController.update();
		}
	}

	// ACE methods


	@FXML
	private void handleAddActionChoiceButton() {
		if (wasSlideSelected()) {
			se.addActionChoice();
			saved = false;
		}
		pController.update();
		updateActionChoiceNumberChoiceBox();
	}

	private void changeActionChoice(int index) {
		ace = new ActionChoiceEditor(data.getSlide(se.getCurrentSlide()));
		ace.setCurrentActionChoiceIndex(index);
		currentACLabel.setText(Integer.toString(index));
		ActionChoice choice = data.getSlide(se.getCurrentSlide()).getActionChoiceAt(index);
		aceChoiceTextArea.setText(choice.getChoiceText());
		aceSetDestinationSlideIndexField.setText(Integer.toString(choice.getDestinationSlideIndex()));
	}


	@FXML
	private void handleAceChoiceSubmitButton() {
		String input = aceSetDestinationSlideIndexField.getText();
		if (wasAcSelected())
			if (isInputInt(input)) {
				int index = Integer.parseInt(input);
				if (isIndexASlide(index)) {
					ace.setChoiceText(aceChoiceTextArea.getText());
					ace.setDestinationSlideIndex(index);
					saved = false;
					pController.update();
				}
			}
	}

	
	private void clearACE() {
		this.currentACLabel.setText("N/A");
		this.updateActionChoiceNumberChoiceBox();
		aceChoiceTextArea.clear();
		aceSetDestinationSlideIndexField.clear();
	}

	
	@FXML
	private void handleRemoveAcButton() {
		if (this.wasAcSelected()) {
			clearACE();
			ace.remove();
			pController.update();
			updateActionChoiceNumberChoiceBox();
			saved = false;
		}
	}

	
	@FXML
	private void handleShowAceInfoButton() {
		if (wasAcSelected()) {
			ActionChoice choice = data.getSlide(se.getCurrentSlide()).getActionChoiceAt(ace.currentActionChoiceIndex);
			new Alert(AlertType.INFORMATION, choice.toString()).showAndWait();
			saved = false;
		}
	}

	private void updateActionChoiceNumberChoiceBox() {
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

	private void updateItemChoiceBox() {

		ArrayList<Item> list = new ArrayList<Item>();
		for (Item item : data.getPlayer().getInventory()) {
			list.add(item);
		}
		ObservableList<Item> observableList = FXCollections.observableList(list);
		itemChoiceBox.setItems(observableList);

	}

	@FXML
	private void handleAddEffect() {
		if (this.wasAcSelected()) {
			if (effectChiceBox.getValue() == null) {
				new Alert(AlertType.ERROR, "Please Select an Effect Type").showAndWait();
			} else if (effectChiceBox.getValue().equals("Item Effect")) {
				addItemEffect();
			} else if (effectChiceBox.getValue().equals("Gender Change Effect")) {
				if (ace.hasGenderEffect()) {
					new Alert(AlertType.ERROR, "There is already a gender change effect for this action choice")
							.showAndWait();
				} else {
					Gender gender = getGenderFromUser();
					if (gender != null) {
						ace.addGenderChangeEffect(gender);
						saved = false;
					} else {
						new Alert(AlertType.ERROR, "New Effect Canceled.");
					}
				}
			} else if (effectChiceBox.getValue().equals("Name Change Effect")) {
				if (ace.hasNameChangeEffect()) {
					new Alert(AlertType.ERROR, "There is already a name change effect for this action choice.")
							.showAndWait();
				} else {
					String name = getNameFromUser();
					if (name != null) {
						ace.addNameChangeEffect(name);
						saved = false;
					} else {
						new Alert(AlertType.ERROR, "New Effect Canceled.").showAndWait();
					}
				}
			}
		}
		pController.update();
	}

	private void addItemEffect() {
		if (data.getPlayer().getInventory().size() == 0) {
			new Alert(AlertType.ERROR, "There is nothing in the inventroy").showAndWait();
		} else {
			Item item = getItemFromUser();
			if (item != null) {
				try {
					if (ace.hasItemEffect(item)) {
						new Alert(AlertType.ERROR, "There is already an effect with that item").showAndWait();
					} else {
						int effectChoiceSize = getChoiceSize("Effect");
						ace.addItemEffect(item, effectChoiceSize);
						saved = false;
					}
				} catch (NumberFormatException e) {
					new Alert(AlertType.ERROR, "Must be a number").showAndWait();
				} catch (NoSuchElementException e1) {
					new Alert(AlertType.ERROR, "Effect not added").showAndWait();
				}
			}
		}
	}

	private Gender getGenderFromUser() {
		Alert genderAlert = new Alert(AlertType.CONFIRMATION);
		genderAlert.setContentText("Which gender should be used?");
		ButtonType maleButton = new ButtonType("Male");
		ButtonType femaleButton = new ButtonType("Female");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		genderAlert.getButtonTypes().setAll(maleButton, femaleButton, cancelButton);
		genderAlert.setTitle("New Effect Specs");
		Optional<ButtonType> genderOptional = genderAlert.showAndWait();
		if (genderOptional.isPresent()) {
			if (genderOptional.get().equals(maleButton)) {
				return Gender.MALE;
			} else if (genderOptional.get().equals(femaleButton)) {
				return Gender.FEMALE;
			}
		}
		return null;
	}

	private String getNameFromUser() {
		TextInputDialog nameDialog = new TextInputDialog();
		nameDialog.setContentText("Enter the name.");
		Optional<String> nameOptional = nameDialog.showAndWait();
		if (nameOptional.isPresent()) {
			return nameOptional.get();
		} else {
			return null;
		}
	}

	private int getChoiceSize(String EorC) throws NumberFormatException, NoSuchElementException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New" + EorC + "Specs");
		dialog.setHeaderText("Enter" + EorC + "Number");
		dialog.setContentText("Use positive numbers for adding and negitive numbers for subtracting");
		Optional<String> choiceSizeOptional = dialog.showAndWait();
		return Integer.parseInt(choiceSizeOptional.get());
	}

	private Item getItemFromUser() {
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
	private void handleRemoveEffectButton() {
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
				saved = false;
			} else {
				new Alert(AlertType.ERROR, "Effect not removed");
			}
		}
		pController.update();

	}

	// Item Editor Methods

	private File getItemImageFromUser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chose an image for the item");
		return fileChooser.showOpenDialog(mainWindow);
	}

	@FXML
	private void handleRemoveItemButton() {
		if (isieSelected()) {
			if (data.itemUsed(ie.getCurrentItem())) {
				Optional<ButtonType> inUseResponse = new Alert(AlertType.CONFIRMATION,
						"This item is in use. Removing it remove all effects and conditions using this item. Are you sure?")
								.showAndWait();

				if (inUseResponse.get() == ButtonType.OK) {
					data.removeItem(ie.getCurrentItem());
					clearie();
					saved = false;
				}
			} else {

				data.getPlayer().getInventory().remove(ie.getCurrentItem());
				clearie();
				saved = false;
			}
		}
		pController.update();
	}

	@FXML
	private void handleAddConditionButton() {
		if (wasAcSelected()) {
			if (conditionChoiceBox.getValue() != null) {
				if (!(conditionChoiceBox.getValue().equals("Item Condition")
						&& data.getPlayer().getInventory().size() == 0)) {// checks
																			// if
																			// inventory
																			// is
																			// empty
																			// and
																			// item
																			// condition
																			// was
																			// selected
					int conditionType = getConditionType();
					Boolean wasTypeSelected = true;
					if (!(conditionType == -1)) {
						if (conditionChoiceBox.getValue().equals("Gender Condition") && wasTypeSelected) {
							Gender gender = getGenderFromUser();
							if (gender != null) {
								ace.addGenderCondition(gender, conditionType);
								saved = false;
							} else {
								new Alert(AlertType.ERROR, "Condition Canceled");
							}
						} else if (conditionChoiceBox.getValue().equals("Item Condition") && wasTypeSelected) {
							Item item = getItemFromUser();
							if (item != null) {
								try {
									int choiceSize = getChoiceSize("Condition");
									RelationalOperator ro = getRelationalOperatorFromUser();
									ace.addItemCondition(item, ro, choiceSize, conditionType);
									saved = false;
								} catch (NumberFormatException e) {
									new Alert(AlertType.ERROR, "Must be a number").showAndWait();
								} catch (NoSuchElementException e) {
									new Alert(AlertType.ERROR, "No number sumbited").showAndWait();
								}
							} else {
								new Alert(AlertType.ERROR, "No item selected").showAndWait();
							}
						}
					}
				} else {
					new Alert(AlertType.ERROR, "Nothing in inventory").showAndWait();
				}
			
		} else {
			new Alert(AlertType.ERROR, "Please select a condition").showAndWait();
		}
		}
		pController.update();
	}

	private RelationalOperator getRelationalOperatorFromUser() {
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
			return ro;
		}
		return null;
	}

	private int getConditionType() {
		Alert conditionAlert = new Alert(AlertType.CONFIRMATION);
		conditionAlert.setContentText("Should this be a visibility or feasibility condition");
		ButtonType visibleButton = new ButtonType("Visibility");
		ButtonType feasibilityButton = new ButtonType("Feasibility");

		conditionAlert.getButtonTypes().setAll(visibleButton, feasibilityButton, ButtonType.CANCEL);
		Optional<ButtonType> conditionTypeOptional = conditionAlert.showAndWait();

		int conditionType = -1;
		if (conditionTypeOptional.get() == visibleButton) {
			conditionType = ActionChoice.VISIBILITY;
		} else if (conditionTypeOptional.get() == feasibilityButton) {
			conditionType = ActionChoice.FEASIBILITY;
		}
		return conditionType;
	}

	@FXML
	private void handleRemoveConditionButton() {
		if (this.wasAcSelected()) {
			int conditionType = getConditionType();
			if (!(conditionType == -1)) {
				ArrayList<Condition> conditions = null;
				if (conditionType == ActionChoice.FEASIBILITY) {
					conditions = ace.getFConditions();
				} else if (conditionType == ActionChoice.VISIBILITY) {
					conditions = ace.getVConditions();
				}
				ArrayList<Integer> conditionIndices = new ArrayList<Integer>();
				Alert conditionInfo = new Alert(AlertType.INFORMATION);
				String s = "";
				for (int i = 0; i < conditions.size(); i++) {
					conditionIndices.add(i);
					s += "Condition " + conditions.get(i).printEffectInfo() + " has index " + i + "\n";
				}
				ChoiceDialog<Integer> conditionDialog = new ChoiceDialog<Integer>(null, conditionIndices);
				conditionDialog.setContentText("Which condition will be removed? Consult alert for refference");
				conditionInfo.setContentText(s);
				conditionInfo.setX(200);
				conditionInfo.show();
				Optional<Integer> conditionOptional = conditionDialog.showAndWait();
				conditionInfo.close();
				if (conditionOptional.isPresent()) {
					int conditionInt = conditionOptional.get();
					ace.removeCondition(conditionInt, conditionType);
					saved = false;
				} else {
					new Alert(AlertType.ERROR, "No Condition Selected");
				}
			} else {
				new Alert(AlertType.ERROR, "Must have condition type");
			}
		}
		pController.update();
	}

	@FXML
	private void handleAdditemButton() {
		if (itemNameTextField.getText().equals("")) {
			new Alert(AlertType.ERROR, "Please Enter a name for the item.").showAndWait();
		} else {
			data.getPlayer().addItem(new Item(itemNameTextField.getText()));
			saved = false;
		}
		updateItemChoiceBox();
		pController.update();
	}

	private void changeItem(Item item) {
		ie.setCurrentItem(item);
		currentItemLabel.setText(item.getItemName());
		itemVisibleCheckBox.setSelected(item.isVisible());
		itemImageLabel.setText(item.getImageAddress());
		currentSellPriceLabel.setText(Integer.toString(item.getSellPrice()));
		currentBuyPriceLabel.setText(Integer.toString(item.getBuyPrice()));
		buyableCheckBox.setSelected(item.canBuy());
		sellableCheckBox.setSelected(item.canSell());
	}

	@FXML
	private void handleItemVisibleCheckBox() {
		if (isieSelected()) {
			ie.setVisibility(itemVisibleCheckBox.isSelected());
			pController.update();
			saved = false;
		}
	}

	@FXML
	private void handleSelectAnItemImageButton() {
		if (isieSelected()) {
			File inFile = getItemImageFromUser();

			if (inFile != null) {
				String path = "assets/art/icons/" + inFile.getName();
				try {
					Files.copy(inFile.toPath(), (new File(path)).toPath(), StandardCopyOption.REPLACE_EXISTING);
					ie.setImagePath(inFile.getName());
					itemImageLabel.setText(inFile.getName());
					saved = false;

				} catch (IOException e) {
					// should never happen, checked before, needed for
					// compile
					e.printStackTrace();
				}
				pController.update();
			}
		}
	}

	@FXML
	private void handleSellableCheckBox() {
		if (isieSelected()) {
			ie.setSellable(sellableCheckBox.isSelected());
			saved = false;
		}
		pController.update();
	}

	@FXML
	private void handleBuyableCheckBox() {
		if (ie.isItemSelected()) {
			ie.setBuyable(buyableCheckBox.isSelected());
			saved = false;
		}
		pController.update();
	}

	private boolean isieSelected() {
		if (ie.isItemSelected()) {
			return true;
		} else {
			new Alert(AlertType.ERROR, "Please select an item").showAndWait();
			return false;
		}
	}

	private void clearie() {
		ie.setCurrentItem(null);
		currentItemLabel.setText("No item selected.");
		itemVisibleCheckBox.setSelected(false);
		buyableCheckBox.setSelected(false);
		sellableCheckBox.setSelected(false);
		itemImageLabel.setText("No image selected.");
		updateItemChoiceBox();
		itemChoiceBox.setValue(null);
		currentSellPriceLabel.setText("N/A");
		currentBuyPriceLabel.setText("N/A");

	}

	@FXML
	private void handleSetSellPriceButton() {
		if (isieSelected()) {
			if (ie.canSell()) {
				int price = getPriceFromUser();
				if (price != -1) {
					ie.setSellPrice(price);
					saved = false;
				}
			} else {
				new Alert(AlertType.ERROR, "Item is not sellable").showAndWait();
			}
		}
		pController.update();
	}

	@FXML
	private void handleSetBuyPriceButton() {
		if (isieSelected()) {
			if (ie.canBuy()) {
				int price = getPriceFromUser();
				if (price != -1) {
					ie.setBuyPrice(price);
					saved = false;
				}
			} else {
				new Alert(AlertType.ERROR, "Item is not buyable").showAndWait();
			}
		}
		pController.update();
	}

	private int getPriceFromUser() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setContentText("Enter price: ");
		Optional<String> dialogOptional = dialog.showAndWait();
		if (dialogOptional.isPresent()) {
			String priceString = dialogOptional.get();
			if (isInputInt(priceString)) {
				int price = Integer.parseInt(priceString);
				if (price < 0) {
					new Alert(AlertType.ERROR, "Price can not be negitive").showAndWait();
					return -1;
				} else {
					return price;
				}
			} else {
				return -1;
			}
		} else {
			new Alert(AlertType.ERROR, "No price given.").showAndWait();
			return -1;
		}
	}

	@FXML
	private void handleMenuHelpAbout() {
		Alert aboutAlert = new Alert(AlertType.INFORMATION, "To use this builder you can start many different ways."
				+ "\n1. If you have an existing project that has been saved, then you can load it with the load button. "
				+ "If you would like to start from scratch, then you can add as many slides as you would like. "
				+ "It may also be a good idea to go to the Misc editor tab and add any inventory items you may want.  "
				+ "This will make it easier to add effects and conditions later."
				+ "\n2. Next go to the slide editor tab.  "
				+ "Here you must select a slide index to edit.  Now you can add a title and game text.  "
				+ "To save your changes press the submit button.  "
				+ "At the bottom of the screen, you can change the slide type, select a slide image, add action choices to a slide, and even remove a slide."
				+ "\n3. Next go to the action choice editor tab. Select an action choice index that you would like to edit.  "
				+ "Now you can set the choice text and destination slide index, using the submit button to save any changes made.  "
				+ "To add action choices and conditions,  first select a type from the drop down menu and then press the “add” button.");

		aboutAlert.show();
		aboutAlert.setWidth(1000);
	}

}
