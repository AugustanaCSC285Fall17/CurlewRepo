package edu.augustana.csc285.gamebuilder;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import edu.augustana.csc285.game.datamodel.GameData;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
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
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import edu.augustana.csc285.game.datamodel.*;

public class PreviewPaneController {

	@FXML
	private Text slideInfoText;
	private GameData data;
	

	@FXML
	private void initialize() {
	}

	public void setData(GameData data) {
		this.data = data;
	}

	public void setStageAndSetupListeners(Stage primaryStage, GameData data) {
		this.data = data;
	}

	public void print() {
		System.out.println(3);
	}

	public void update() {
		updateSlideInfoText();
	}

	private void updateSlideInfoText() {
		slideInfoText.setText(data.printSlideInfo());
	}

	public void updateData(GameData data2) {
		this.data = data2;
		System.out.println(1);
		update();
	}
}
