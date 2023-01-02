package com.example.controller;

import java.text.DecimalFormat;
import java.util.Random;
import org.fxmisc.richtext.Caret;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.Caret.CaretVisibility;

import com.example.modele.SoloNormal;
import com.example.modele.ModeleNormal;

import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SoloNormalSceneController {
    ModeleNormal modele;
    Random rand = new Random();
    Timeline time;

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
            if(charCorrecte(caretPos)){
                event.consume();
            }
        }
        else if(event.getCode() == KeyCode.BACK_SPACE){
            if(backSpace(caretPos)){
                event.consume();
            }
        }
        else if(!event.getCode().isLetterKey()){
            event.consume();
        }
        else{
            if(charIncorrecte(caretPos)){
                event.consume();
            }
        }
    }

    // public void startTimerCountTime(){
    //     TimerTask taskCountTime = new TimerTask() {
    //         @Override
    //         public void run() {
    //             lblDonneeTime.setText(tempsJeu+"");
    //             tempsJeu --;
    //         }
    //     };
    //     modele.getJeu().getTimer().scheduleAtFixedRate(taskCountTime,0*1000, 1*1000);
    // }

    // public void enleverUneSecondeDeLAffichage(){
    //     tempsJeu --;
    //     System.out.println("******************************************************************************************");
    //     lblDonneeTime.setText(tempsJeu+"");
    // }

    // public void updateTempsDeJeuRestant(int temps){
    //     lblDonneeTime.setText(temps+"");

    // }

    private void startTimer(){
        if (!modele.getJeu().getTimerActive()){
            modele.getJeu().startTimerNormal();
            lblTexteTime.setVisible(true);
            lblDonneeTime.setVisible(true);
            updateTimer();
            //startTimerCountTime();
            
            //lblDonneeTime.setText(tempsJeu+"");
        }
    }

    protected boolean charCorrecte(int caretPos){
        if(!verificationFinDuMot(caretPos)){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green; -fx-font-size: 18px;");
            modele.getJeu().incrCharUtilesTemporaire();
            modele.getJeu().incrNbAppuiTouches();
            modele.getJeu().ajoutTempsCharUtile();
            ictaArea.moveTo(caretPos+1);
            return false;
        }
        else {
            return true;
        }
    }

    protected boolean charIncorrecte(int caretPos){
        if(!verificationFinDuMot(caretPos)){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red; -fx-font-size: 18px;");
            modele.getJeu().incrNbAppuiTouches();
            ictaArea.moveTo(caretPos+1);
            return false;
        }
        else {
            return true;
        }
    }

    protected boolean backSpace(int caretPos){
        if(!verificationDebutDuMot(caretPos)){
            ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black; -fx-font-size: 18px;");
            String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
            if(previousCharStyle.compareTo("-fx-fill: green; -fx-font-size: 18px;") == 0){
                modele.getJeu().decrCharUtilesTemporaire();
            }
            ictaArea.moveTo(caretPos-1);
            return false;
        }
        else {
            return true;
        }
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

    protected boolean verificationDebutDuMot(int caretPos){
        return ictaArea.getText(caretPos-1,caretPos).compareTo(" ") == 0;
    }

    protected void moveToNextMot(int caretPos){
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red; -fx-font-size: 18px;");
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

    protected void updateTimer(){
        time = new Timeline(new KeyFrame(Duration.millis(1000),ae ->updateCountdown()));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    protected void updateCountdown(){
        if(modele.getJeu().getCountdown()>0){
            modele.getJeu().decrCountdown();
            lblDonneeTime.setText(modele.getJeu().getCountdown()+"");   
        }
        else {
            time.stop();
            //afficher stats
            affichageDonneeFinDeJeu();
        }
    }

    public void setModele(ModeleNormal modele) {
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

    public void affichageDonneeFinDeJeu(){
        ictaArea.setDisable(true);
        lblTexteTime.setVisible(false);
        lblDonneeTime.setVisible(false);
        lblTextePrecision.setVisible(true);
        lblTexteRegularite.setVisible(true);
        lblTexteVitesse.setVisible(true);
        lblDonneePrecision.setVisible(true);
        lblDonneeRegularite.setVisible(true);
        lblDonneeVitesse.setVisible(true);

        DecimalFormat df = new DecimalFormat("0.00");
        lblDonneePrecision.setText(df.format(modele.getJeu().getStatsPrecision())+"");
        lblDonneeRegularite.setText(df.format(modele.getJeu().getStatsRegularite())+"");
        lblDonneeVitesse.setText(df.format(modele.getJeu().getStatsVitesse())+"");
        

        

    }
    
}
