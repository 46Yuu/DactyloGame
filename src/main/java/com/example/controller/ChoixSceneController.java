package com.example.controller;

import com.example.vue.GenerateurVue;
import com.example.modele.Parametre;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ChoixSceneController {
    private Stage stage;

    public void setStage(Stage s) {
        stage = s;
    }

    public static Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
    }

    /**
     * Fonction qui lance le mode multijoueur
     * @param event
     */
    
    @FXML
    void btnMultiJoueurClicked(ActionEvent event) {
        System.out.println("Mode multijoueur clicked");
    }

    /**
     * Fonction qui lance le choix des paramètres du mode solo jeu
     * @param event
     */
    @FXML
    void btnSoloJeuClicked(ActionEvent event) {
        System.out.println("Mode solo Jeu Clicked");
        GenerateurVue.Vue vue = GenerateurVue.genererVueParamPartieSoloJeu();
        vue.lancerVue(stage);
    }

    /**
     * Fonction qui lance le choix des paramètres du mode solo normal
     * @param event
     */
    @FXML
    void btnSoloNormalClicked(ActionEvent event) {
        System.out.println("Mode solo Normal Clicked");
        GenerateurVue.Vue vue = GenerateurVue.genererVueParamPartieSoloNormal();
        vue.lancerVue(stage);
    }

}
