import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Client extends Application {

    public void start(Stage primaryStage){
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainScreen.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public  static  void  main(String[] args){
        launch(args);
    }
}
