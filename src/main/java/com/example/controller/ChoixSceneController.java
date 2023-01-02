package com.example.controller;
import com.example.modele.SoloNormal;
import com.example.modele.SoloJeu;
import com.example.modele.ModeleNormal;
import com.example.modele.ModeleJeu;
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
            SoloJeu jeu = new SoloJeu(creationParametreSoloNormal());
            ModeleJeu modele = new ModeleJeu(jeu);
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
            SoloNormal jeu = new SoloNormal(creationParametreSoloNormal());
            ModeleNormal modele = new ModeleNormal(jeu);
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
