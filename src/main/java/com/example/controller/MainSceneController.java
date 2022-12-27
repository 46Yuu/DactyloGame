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
        if (!modele.getJeu().getTimerActive()){
            modele.getJeu().startTimerNormal();
            if(modele.getJeu().getTimerActive()){System.out.println("timer is active");} else {System.out.println("timer not active");}
        }
        int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getText();
        System.out.println("Une key typed:  " + charTyped);
        System.out.println(ictaArea.getText(caretPos, caretPos+1));
        if(event.getCode() == KeyCode.SPACE){
            verificationMot(caretPos);
        }
        else if(charTyped.compareTo(ictaArea.getText(caretPos, caretPos+1)) == 0){
            charCorrecte(caretPos);
        }
        else if(event.getCode() == KeyCode.BACK_SPACE){
            backSpace(caretPos);
        }
        else if(!event.getCode().isLetterKey()){
            event.consume();
        }
        else{
            charIncorrecte(caretPos);
        }
    }

    private void charCorrecte(int caretPos){
        ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green;");
        modele.getJeu().incrCharUtilesTemporaire();
        modele.getJeu().incrNbAppuiTouches();
        modele.getJeu().ajoutTempsCharUtile();
        ictaArea.moveTo(caretPos+1);
    }

    private void charIncorrecte(int caretPos){
        ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red;");
        modele.getJeu().incrNbAppuiTouches();
        ictaArea.moveTo(caretPos+1);

    }

    private void backSpace(int caretPos){
        ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black;");
        String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
        if(previousCharStyle.compareTo("-fx-fill: green;") == 0){
            modele.getJeu().decrCharUtilesTemporaire();
        }
        ictaArea.moveTo(caretPos-1);
    }

    private void verificationMot(int caretPos){
        int memCaretPos = caretPos;
        String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
        boolean motCorrecte = true;
        while (ictaArea.getText(caretPos-1, caretPos).compareTo(" ")==1 && motCorrecte == true){
            motCorrecte = (previousCharStyle.compareTo("-fx-fill: green;") == 0)?true:false;
            caretPos--;
        }
        if(motCorrecte){
            System.out.println("Mot correcte");
            modele.getJeu().ajoutCharUtilesTemporaire();
        }
        modele.getJeu().resetCharUtilesTemporaire();
        modele.getJeu().validerMot();
        ictaArea.moveTo(memCaretPos+1);
    }

    public void setModele(Modele modele) {
        this.modele = modele;
        ictaArea.setEditable(false);
        //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
        modele.addListener(l -> {ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});

    }
    
}
