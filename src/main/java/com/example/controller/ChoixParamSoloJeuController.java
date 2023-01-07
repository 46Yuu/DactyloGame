package com.example.controller;

import com.example.modele.KeyValuePair;
import com.example.vue.GenerateurVue;
import com.example.vue.Vue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ChoixParamSoloJeuController {

    @FXML
    private Button btnValider;

    @FXML
    private ChoiceBox<KeyValuePair> cbLangue;

    @FXML
    private ChoiceBox<KeyValuePair> cbNiveau;

    @FXML
    private ChoiceBox<KeyValuePair> cbVie;

    /**
     * Valide les paramètres choisis par le 
     * joueur.
     * @param event
     */
    @FXML
    void validation(ActionEvent event) {
        System.out.println("Validation SoloJeu");
        Vue vuePartieSoloJeu = GenerateurVue.genererVuePartieSoloJeu(
            cbLangue.getValue().getKey(),
            Integer.parseInt(cbVie.getValue().getKey()),
            Integer.parseInt(cbNiveau.getValue().getKey()));
        vuePartieSoloJeu.lancerVue();
    }

    /*
     * On remplis les choicebox pour les choix
     */
    public void initialize() {
        cbLangue.getItems().add(new KeyValuePair("FR", "Français"));
        cbLangue.getItems().add(new KeyValuePair("EN", "Anglais"));
        cbLangue.setValue(new KeyValuePair("FR", "Français"));
        cbNiveau.getItems().add(new KeyValuePair("1", "Facile"));
        cbNiveau.getItems().add(new KeyValuePair("5", "Moyen"));
        cbNiveau.getItems().add(new KeyValuePair("8", "Difficile"));
        cbNiveau.setValue(new KeyValuePair("1", "Facile"));
        cbVie.getItems().add(new KeyValuePair("30", "30"));
        cbVie.getItems().add(new KeyValuePair("50", "50"));
        cbVie.getItems().add(new KeyValuePair("100", "100"));
        cbVie.setValue(new KeyValuePair("50", "50"));
    }
}