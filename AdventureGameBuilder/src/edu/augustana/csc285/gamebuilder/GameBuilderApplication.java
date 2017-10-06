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
        controller.setStageAndSetupListeners(primaryStage); 
        
        
        primaryStage.setTitle("Curlew's Game Builder");
        primaryStage.setScene(scene);
        primaryStage.show();       
        
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("PreviewPane.fxml"));
        Scene scene2 = new Scene(loader2.load());
        
        
        
        Stage stage2 = new Stage();
        
        PreviewPaneController pController = (PreviewPaneController)loader2.getController();
        controller.setStageAndSetupListeners(stage2);
        
        stage2.setTitle("Game Builder Info");
        stage2.setX(stage2.getX()-200);
        stage2.setScene(scene2);
        stage2.show();
        
	}

	public static void main(String[] args) {
		launch(args); 
		//PreviewPaneApp.main(args);
	}
}
