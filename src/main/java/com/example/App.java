package com.example;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        System.out.print("§!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        try {
            System.out.print("§!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            URL url = Paths.get("./src/main/resources/MainScene.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url);
            //Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
            //chercher comment ajouter richtextFX

            //Label lbl = new Label("Hello world!");
            //root.setCenter(lbl);
            Scene scene = new Scene(root);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Test Scene");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
