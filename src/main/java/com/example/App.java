package com.example;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.StyledTextArea;

import com.example.controller.MainSceneController;
import com.example.modele.Modele;

/**
 * Hello world!
 *
 */
public class App extends Application
{


    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.print("");
            
            //URL url = Paths.get("./src/main/resources/MainScene.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
            //Parent root = FXMLLoader.load(url);
            Parent root = loader.load();
            MainSceneController controller = loader.getController();
            Modele modele = new Modele();
            controller.setModele(modele);
            modele.initialize();

            
            Scene scene = new Scene(root);
            
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Test Scene");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
