package com.example.controller;

import org.fxmisc.richtext.Caret;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.NavigationActions.SelectionPolicy;

import com.example.modele.Modele;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainSceneController {
    Modele modele;

    @FXML
    private InlineCssTextArea ictaArea;

    @FXML
    void areaOnKeyPressed(KeyEvent event){
        int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getText();
        System.out.println("Une key typed:  " + charTyped);
        System.out.println(ictaArea.getText(caretPos, caretPos+1));
        if(charTyped.compareTo(ictaArea.getText(caretPos, caretPos+1)) == 0){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green;");
            ictaArea.moveTo(caretPos+1);
        }
        else if(event.getCode() == KeyCode.BACK_SPACE){
            ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black;");
            ictaArea.moveTo(caretPos-1);
        }
        else if(!event.getCode().isLetterKey()){
            event.consume();
        }
        else if(event.getCode() == KeyCode.SPACE){
            //valider 
        }
        else{
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red;");
            ictaArea.moveTo(caretPos+1);
        }
    }

    @FXML
    void windowOnKeyTyped(KeyEvent event) {
        /*int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getCharacter();
        System.out.println("Une key typed:  " + charTyped);
        System.out.println(ictaArea.getText(caretPos, caretPos+1));
        if(charTyped.compareTo(ictaArea.getText(caretPos, caretPos+1)) == 0){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green;");
            ictaArea.moveTo(caretPos+1);
        }
        else{
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red;");
            ictaArea.moveTo(caretPos+1);
        }*/
        
    }

    /*@FXML
    void windowOnKeyPressed(KeyEvent event){
        int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getText();
        System.out.println("Une key typed:  " + charTyped);
        System.out.println(ictaArea.getText(caretPos, caretPos+1));
        if(charTyped.compareTo(ictaArea.getText(caretPos, caretPos+1)) == 0){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green;");
            ictaArea.moveTo(caretPos+1);
        }
        else if(event.getCode() == KeyCode.BACK_SPACE){
            ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black;");
            ictaArea.moveTo(caretPos-1);
        }
        else if(event.getCode() == KeyCode.SHIFT){
            System.out.println("shift");
        }
        else if(event.getCode() == KeyCode.SPACE){

        }
        else{
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red;");
            ictaArea.moveTo(caretPos+1);
        }
    }*/

    public void setModele(Modele modele) {
        this.modele = modele;
        ictaArea.setEditable(false);
        //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
        modele.addListener(l -> {ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});

    }
    

}
