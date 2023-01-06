package com.example.vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.example.modele.*;

public class App extends Application
{
    public static void main( String[] args){
        launch(args);
    }

    public Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.print("");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChoixScene.fxml"));
            Parent root = loader.load();      
            Scene scene = new Scene(root); 
            primaryStage.setScene(scene);
            primaryStage.setTitle("Choix");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
