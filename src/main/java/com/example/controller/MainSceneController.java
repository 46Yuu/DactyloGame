package com.example.controller;

import org.fxmisc.richtext.Caret;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.NavigationActions.SelectionPolicy;

import com.example.modele.Modele;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
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
        int caretPos = ictaArea.getCaretPosition();
        System.out.println(ictaArea.getText(caretPos, caretPos+1));
        if(event.getCharacter().compareTo(ictaArea.getText(caretPos, caretPos+1)) == 0){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green;");
        }
        else{
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red;");
        }
        ictaArea.moveTo(caretPos+1);
    }

    public void setModele(Modele modele) {
        this.modele = modele;
        
        modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
    }
    

    @FXML
    void btIcta(ActionEvent event) {
        //Met la couleur rouge au texte de la position 0 à la position 10
        ictaArea.setStyle(0, 10, "-fx-fill: red;");
    }

    

}
