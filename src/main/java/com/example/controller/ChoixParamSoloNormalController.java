package com.example.controller;

import com.example.modele.KeyValuePair;
import com.example.vue.GenerateurVue;
import com.example.vue.Vue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ChoixParamSoloNormalController {

    @FXML
    private Button btnValider;

    @FXML
    private ChoiceBox<KeyValuePair> cbLangue;

    @FXML
    private ChoiceBox<KeyValuePair> cbTemps;

    @FXML
    void validation(ActionEvent event) {
        System.out.println("Validation SoloNormal");
        Vue vuePartieSoloNormal = GenerateurVue.genererVuePartieSoloNormal(cbLangue.getValue().getKey(),Integer.parseInt(cbTemps.getValue().getKey()) );

        vuePartieSoloNormal.lancerVue();

    }


    /*
     * On remplis les choicebox pour les choix
     */
    public void initialize() {
        cbLangue.getItems().add(new KeyValuePair("FR", "Français"));
        cbLangue.getItems().add(new KeyValuePair("EN", "Anglais"));
        cbLangue.setValue(new KeyValuePair("FR", "Français"));

        cbTemps.getItems().add(new KeyValuePair("30", "30 sec"));
        cbTemps.getItems().add(new KeyValuePair("60", "1 min"));
        cbTemps.getItems().add(new KeyValuePair("90", "1 min 30 sec"));
        cbTemps.setValue(new KeyValuePair("30", "30 sec"));

    }

}