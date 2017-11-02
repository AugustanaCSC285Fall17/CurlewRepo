package edu.augustana.csc285.gamebuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import edu.augustana.csc285.game.datamodel.GameData;
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
         
       
        
       
       FXMLLoader loader2 = new FXMLLoader(getClass().getResource("PreviewPane.fxml"));
       Parent root2 = (Parent) loader2.load();
       Scene scene2 = new Scene(root2);
        
        
        
        Stage stage2 = new Stage();
        
        PreviewPaneController pController = (PreviewPaneController)loader2.getController();
        
        GameData data = new GameData();
        
        controller.setStageAndSetupListeners(primaryStage, data);
        controller.setPcontroler(pController);
        pController.setData(data);
        
       
        primaryStage.setTitle("Curlew's Game Builder");
        primaryStage.setScene(scene);
        primaryStage.show();       
        
        stage2.setTitle("Game Builder Info");
        stage2.setX(stage2.getX()-200);
        stage2.setScene(scene2);
        stage2.show();
        
        primaryStage.setOnCloseRequest(event -> stage2.close());
        stage2.setOnCloseRequest(event -> primaryStage.close());
        
	}

	public static void main(String[] args) {
		launch(args); 
		//PreviewPaneApp.main(args);
	}


	/**
	 * 
	 * @param file
	 *            the address of the JSON file
	 * @return a GamaData object, which is created from deserializing the JSON data
	 *         imported from the file.
	 */
	public static GameData loadGameDataFromJSONFile(File file) {
		//JsonReader reader = new JsonReader();
		
		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader (new FileInputStream(file), "UTF-8"));
//			StringBuilder sb = new StringBuilder();
//			String line = br.readLine();
//
//			while (line != null) {
//				sb.append(line);
//				sb.append("\n");
//				line = br.readLine();
//			}
			
			//JsonValue jsonData = reader.parse(new FileHandle(file));
			
			return GameData.fromJSON(new String(Files.readAllBytes(file.toPath()), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
