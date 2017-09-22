package edu.augustana.csc285.gamebuilder;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameBuilderApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		//Parent root = FXMLLoader.load(getClass().getResource("MainPane.fxml"));
       
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPane.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
    
        MainPaneController controller = (MainPaneController)loader.getController();
        controller.setStageAndSetupListeners(primaryStage); // or what you want to do
        
        
        primaryStage.setTitle("Curlew's Game Builder");
        primaryStage.setScene(scene);
        primaryStage.show();       

	}

	public static void main(String[] args) {
		launch(args); 
	}
}
