package edu.augustana.csc285.gamebuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PreviewPaneApp extends Application  {


	@Override
	public void start(Stage primaryStage) throws Exception {
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("PreviewPane.fxml"));
	        Parent root = (Parent)loader.load();
	        Scene scene = new Scene(root);
	        
	        primaryStage.setTitle("PreviewPane");
	        primaryStage.setScene(scene);
	        primaryStage.show();      
	}
	
	public static void main(String[] args) {
		launch(args); 
	}

}
