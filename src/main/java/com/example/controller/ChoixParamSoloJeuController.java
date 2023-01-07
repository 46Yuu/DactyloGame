package com.example.controller;

import com.example.modele.KeyValuePair;
import com.example.vue.GenerateurVue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ChoixParamSoloJeuController {
    

    @FXML
    private Button btnValider;

    @FXML
    private ChoiceBox<KeyValuePair> cbLangue;

    @FXML
    private ChoiceBox<KeyValuePair> cbNiveau;

    @FXML
    private ChoiceBox<KeyValuePair> cbVie;

    private Stage stage;

    public void setStage(Stage s) {
        stage = s;
    }

    /**
     * Lance le mode Solo jeu en focntion des paramètres
     * @param event
     */
    @FXML
    void validation(ActionEvent event) {
        System.out.println("Validation SoloJeu");
        GenerateurVue.Vue vuePartieSoloJeu = GenerateurVue.genererVuePartieSoloJeu(
            cbLangue.getValue().getKey(),
            Integer.parseInt(cbVie.getValue().getKey()),
            Integer.parseInt(cbNiveau.getValue().getKey()));
        vuePartieSoloJeu.lancerVue(stage);
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