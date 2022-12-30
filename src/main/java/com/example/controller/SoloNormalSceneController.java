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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SoloNormalSceneController {
    Modele modele;
    Random rand = new Random();

    @FXML
    protected InlineCssTextArea ictaArea;
    @FXML
    protected Label lblDonneePrecision;

    @FXML
    protected Label lblDonneeRegularite;

    @FXML
    private Label lblDonneeTime;

    @FXML
    protected Label lblDonneeVitesse;

    @FXML
    protected Label lblTextePrecision;

    @FXML
    protected Label lblTexteRegularite;

    @FXML
    private Label lblTexteTime;

    @FXML
    protected Label lblTexteVitesse;

    @FXML
    void areaOnKeyPressed(KeyEvent event){
        startTimer();
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


    private void startTimer(){
        if (!modele.getJeu().getTimerActive()){
            modele.getJeu().startTimerNormal();
            //showtimer
        }
    }

    protected void charCorrecte(int caretPos){
        ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green; -fx-font-size: 18px;");
        modele.getJeu().incrCharUtilesTemporaire();
        modele.getJeu().incrNbAppuiTouches();
        modele.getJeu().ajoutTempsCharUtile();
        ictaArea.moveTo(caretPos+1);
    }

    protected void charIncorrecte(int caretPos){
        ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red; -fx-font-size: 18px;");
        modele.getJeu().incrNbAppuiTouches();
        ictaArea.moveTo(caretPos+1);
    }

    protected void backSpace(int caretPos){
        ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black; -fx-font-size: 18px;");
        String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
        if(previousCharStyle.compareTo("-fx-fill: green; -fx-font-size: 18px;") == 0){
            modele.getJeu().decrCharUtilesTemporaire();
        }
        ictaArea.moveTo(caretPos-1);
    }

    protected void verificationMot(int caretPos){
        int memCaretPos = caretPos;
        if(!verificationFinDuMot(caretPos)){
            moveToNextMot(caretPos);
        }
        else{
            boolean motCorrecte = true;
            while (!(ictaArea.getText(caretPos-1, caretPos).compareTo(" ")==0)){
                String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
                if(motCorrecte){
                    if(!(previousCharStyle.compareTo("-fx-fill: green; -fx-font-size: 18px;") == 0)){
                        motCorrecte = false;
                    }
                }
                else{
                    if(previousCharStyle.compareTo("-fx-fill: red; -fx-font-size: 18px;") == 0){
                    }
                }
                caretPos--;
            }
            if(motCorrecte){
                modele.getJeu().ajoutCharUtilesTemporaire();
            }
            ajoutNouveauMot(memCaretPos);
        }
    }

    protected boolean verificationFinDuMot(int caretPos){
        return ictaArea.getText(caretPos,caretPos+1).compareTo(" ") == 0;
    }

    protected void moveToNextMot(int caretPos){
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red; -fx-font-size: 18px;");
            modele.getJeu().incrNbAppuiTouches();
            newCaretPos++;
        }
        ajoutNouveauMot(newCaretPos);
    }

    protected void ajoutNouveauMot(int caretPos){
        modele.getJeu().resetCharUtilesTemporaire();
        String nouveauMot = modele.getJeu().validerMot();
        ictaArea.appendText(nouveauMot+" ");
        ictaArea.setStyle(ictaArea.getLength()-nouveauMot.length(), ictaArea.getLength()-1, "-fx-font-size: 18px;");
        ictaArea.moveTo(caretPos+1);
    }

    public void setModele(Modele modele) {
        this.modele = modele;
        //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
        modele.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
            ictaArea.moveTo(1);
            ictaArea.setWrapText(true);
            ictaArea.setEditable(false);
            ictaArea.setShowCaret(CaretVisibility.ON);
            ictaArea.setStyle(0,ictaArea.getLength(),"-fx-font-size: 18px;");});
    }

    public void initializeScene(){
        lblTexteTime.setVisible(false);
        lblDonneeTime.setVisible(false);
        lblTextePrecision.setVisible(false);
        lblTexteRegularite.setVisible(false);
        lblTexteVitesse.setVisible(false);
        lblDonneePrecision.setVisible(false);
        lblDonneeRegularite.setVisible(false);
        lblDonneeVitesse.setVisible(false);

    }
    
}
