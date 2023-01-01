package com.example.controller;
import com.example.modele.Jeu;
import com.example.modele.JeuSolo;
import com.example.modele.Modele;
import com.example.modele.ModeleSolo;
import com.example.modele.Parametre;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChoixSceneController {

    public static Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
    }

    @FXML
    void btnMultiJoueurClicked(ActionEvent event) {
        System.out.println("Mode multijoueur clicked");
        

    }

    @FXML
    void btnSoloJeuClicked(ActionEvent event) {
        System.out.println("Mode solo Normal Clicked");
        try {
            Stage stage = new Stage();

            //URL url = Paths.get("./src/main/resources/MainScene.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloJeuScene.fxml"));
            //Parent root = FXMLLoader.load(url);
            Parent root = loader.load();
            // SoloNormalController controller = new SoloNormalController();
            //loader.setController(controller);
            SoloJeuSceneController controller = loader.getController();
            JeuSolo jeu = new JeuSolo(creationParametreSoloNormal());
            ModeleSolo modele = new ModeleSolo(jeu);
            controller.setModele(modele);
            modele.initialize();
            controller.initializeScene();
            

            stage.setScene(new Scene(root));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSoloNormalClicked(ActionEvent event) {
        System.out.println("Mode solo Normal Clicked");
        try {
            Stage stage = new Stage();

            //URL url = Paths.get("./src/main/resources/MainScene.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloNormalScene.fxml"));
            //Parent root = FXMLLoader.load(url);
            Parent root = loader.load();
            // SoloNormalController controller = new SoloNormalController();
            //loader.setController(controller);
            SoloNormalSceneController controller = loader.getController();
            Jeu jeu = new Jeu(creationParametreSoloNormal());
            Modele modele = new Modele(jeu);
            controller.setModele(modele);
            modele.initialize();
            controller.initializeScene();

            stage.setScene(new Scene(root));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
