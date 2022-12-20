package com.example.controller;

import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyleClassedTextArea;

import com.example.modele.Modele;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainSceneController {
    Modele modele;

    @FXML
    private InlineCssTextArea ictaArea;

    @FXML
    private StyleClassedTextArea staArea;


    @FXML
    void btnSta(ActionEvent event) {
        /*Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);*/
        modele.setBeginningText("Texte à l'appui du bouton");

    }

    @FXML
    void areaOnKeyTyped(KeyEvent event) {
        //System.out.println("\nUne key typed:  " + event.getCharacter());

    }

    @FXML
    void windowOnKeyTyped(KeyEvent event) {
        System.out.println("Une key typed:  " + event.getCharacter());

    }

    public void setModele(Modele modele) {
        this.modele = modele;
        modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());});
    }

    @FXML
    void btIcta(ActionEvent event) {
        //Met la couleur rouge au texte de la position 0 à la position 10
        ictaArea.setStyle(0, 10, "-fx-fill: red;");
        
    }

    

}
