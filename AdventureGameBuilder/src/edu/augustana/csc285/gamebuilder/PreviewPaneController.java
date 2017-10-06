package edu.augustana.csc285.gamebuilder;

import edu.augustana.csc285.game.datamodel.GameData;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;

public class PreviewPaneController {

	@FXML
	private Text slideInfoText;
	private GameData data;

	@FXML
	private void initialize() {
	}

	public void setData(GameData data) {
		this.data = data;
		System.out.println(1);
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
}
