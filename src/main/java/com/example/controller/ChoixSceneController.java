package com.example.controller;

import com.example.vue.GenerateurVue;
import com.example.vue.Vue;
import com.example.modele.Parametre;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
        Vue vue = GenerateurVue.genererVueParamPartieSoloJeu();
        vue.lancerVue();
    }

    @FXML
    void btnSoloNormalClicked(ActionEvent event) {
        System.out.println("Mode solo Normal Clicked");
        Vue vue = GenerateurVue.genererVueParamPartieSoloNormal();
        vue.lancerVue();
    }

}
