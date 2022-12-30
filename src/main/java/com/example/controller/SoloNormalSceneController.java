package com.example.controller;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.fxmisc.richtext.Caret;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.Caret.CaretVisibility;
import org.fxmisc.richtext.NavigationActions.SelectionPolicy;

import com.example.modele.Modele;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SoloNormalSceneController {
    Modele modele;
    Random rand = new Random();

    @FXML
    private InlineCssTextArea ictaArea;

    @FXML
    void areaOnKeyPressed(KeyEvent event){
        startTimer();
        int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getText();
        isMotBonus(caretPos);
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

    private void isMotBonus(int caretPos){
        if(ictaArea.getStyleAtPosition(caretPos+1).compareTo("-fx-fill: blue;") == 0){
            modele.getJeu().setBonus(true);
        }
    }

    private void startTimer(){
        if (!modele.getJeu().getTimerActive()){
            modele.getJeu().startTimerNormal();
            //showtimer
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
        modele.getJeu().setBonus(false);
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
        int erreurs = 0;
        int memCaretPos = caretPos;
        if(!verificationFinDuMot(caretPos)){
            moveToNextMot(caretPos);
        }
        else{
            boolean motCorrecte = true;
            while (!(ictaArea.getText(caretPos-1, caretPos).compareTo(" ")==0)){
                String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
                if(motCorrecte){
                    if(!(previousCharStyle.compareTo("-fx-fill: green;") == 0)){
                        motCorrecte = false;
                        erreurs++;
                    }
                }
                else{
                    if(previousCharStyle.compareTo("-fx-fill: red;") == 0){
                        erreurs++;
                    }
                }
                caretPos--;
            }
            if(motCorrecte){
                modele.getJeu().ajoutCharUtilesTemporaire();
            }
            testBonus();
            ajoutNouveauMot(memCaretPos);
        }
    }

    private void testBonus(){
        if(modele.getJeu().getBonus()){
            //heal
        }
        modele.getJeu().setBonus(false);
    }

    private boolean verificationFinDuMot(int caretPos){
        return ictaArea.getText(caretPos,caretPos+1).compareTo(" ") == 0;
    }

    private void moveToNextMot(int caretPos){
        int erreurs = 0;
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red;");
            erreurs++;
            modele.getJeu().incrNbAppuiTouches();
            newCaretPos++;
        }
        testBonus();
        ajoutNouveauMot(newCaretPos);
    }

    private void ajoutNouveauMot(int caretPos){
        modele.getJeu().resetCharUtilesTemporaire();
        String nouveauMot = modele.getJeu().validerMot();
        ictaArea.appendText(nouveauMot+" ");
        if(modele.getJeu().getParametre().getMode().compareTo("Jeu")==0){
            if(rand.nextInt(100)<10){
                ajoutMotBonus(nouveauMot);
            }
        }
        ictaArea.moveTo(caretPos+1);
    }

    private void ajoutMotBonus(String mot){
        
        int length = mot.length()+1;
        int ictaLength = ictaArea.getLength();
        ictaArea.setStyle(ictaLength-length, ictaLength-1, "-fx-fill: blue;");
    }

    public void setModele(Modele modele) {
        this.modele = modele;
        //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
        modele.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
            ictaArea.moveTo(1);
            ictaArea.setWrapText(true);
            ictaArea.setEditable(false);
            ictaArea.setShowCaret(CaretVisibility.ON);});
    }
    
}
