package com.example.controller;
import com.example.modele.PartieSoloNormal;
import com.example.vue.GenerateurVue;
import com.example.vue.Vue;
import com.example.modele.PartieSoloJeu;

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
        System.out.println("Mode solo Jeu Clicked");
        Vue vuePartieSoloJeu = GenerateurVue.genererVuePartieSoloJeu();
        vuePartieSoloJeu.lancerVue();
        

    }

    @FXML
    void btnSoloNormalClicked(ActionEvent event) {
        System.out.println("Mode solo Normal Clicked");
        Vue vuePartieSoloNormal = GenerateurVue.genererVuePartieSoloNormal();
        vuePartieSoloNormal.lancerVue();

    }

}
