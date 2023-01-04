package com.example.controller;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.TimerTask;

import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.Caret.CaretVisibility;


import com.example.modele.PartieSoloJeu;
import com.example.modele.PartieSoloNormal;

import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class SoloJeuSceneController extends SoloNormalSceneController{

    //ModeleJeu modele;

    PartieSoloJeu jeu;
    Random rand = new Random();
    Timeline time;
    private static final int nombreMotPourAugmenterNiveau = 100;

   
    @FXML
    private Label lblDonneeNiveau;


    @FXML
    private Label lblDonneeVie;


    @FXML
    private Label lblTexteNiveau;


    @FXML
    private Label lblTexteVie;

    
    @Override
    @FXML
    void areaOnKeyPressed(KeyEvent event){
        startTimerJeu();
        int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getText();
        isMotBonus(caretPos);
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

    private void isMotBonus(int caretPos){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if((ictaArea.getStyleAtPosition(caretPos+1).compareTo("-fx-fill: blue; -fx-font-size: 18px;") == 0) && !jeuSolo.getBonus()){
            jeuSolo.setBonus(true);
            jeuSolo.setBonusActive(true);
        }
    }

    @Override
    protected void verificationMot(int caretPos){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        int erreurs = 0;
        int length = 0;
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
                        erreurs++;
                    }
                }
                else{
                    if(previousCharStyle.compareTo("-fx-fill: red; -fx-font-size: 18px;") == 0){
                        erreurs++;
                    }
                }
                length++;
                caretPos--;
            }
            if(motCorrecte){
                jeuSolo.ajoutCharUtilesTemporaire();
            }
            else {
                enleverPv(erreurs);
            }
            testBonus(length);
            execNumMots();
            ajoutNouveauMot(memCaretPos);
            updateScene();
        }
    }

    @Override
    protected boolean charCorrecte(int caretPos){
        if(!verificationFinDuMot(caretPos)){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: green; -fx-font-size: 18px;");
            jeu.incrCharUtilesTemporaire();
            jeu.incrNbAppuiTouches();
            jeu.ajoutTempsCharUtile();
            ictaArea.moveTo(caretPos+1);
            return false;
        }
        else {
            return true;
        }
    }

    
    @Override
    protected boolean charIncorrecte(int caretPos){
        if(!verificationFinDuMot(caretPos)){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red; -fx-font-size: 18px;");

            ictaArea.moveTo(caretPos+1);
            jeu.incrNbAppuiTouches();
            PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
            jeuSolo.setBonusActive(false);
            return false;
        }
        else {
            return true;
        }
        
    }

    @Override
    protected boolean backSpace(int caretPos){
        if(!verificationDebutDuMot(caretPos)){
            PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
            ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black; -fx-font-size: 18px;");
            String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
            if(previousCharStyle.compareTo("-fx-fill: green; -fx-font-size: 18px;") == 0){
                jeuSolo.decrCharUtilesTemporaire();
            }
            ictaArea.moveTo(caretPos-1);
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void moveToNextMot(int caretPos){
        int erreurs = 0;
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red; -fx-font-size: 18px;");
            erreurs++;
            newCaretPos++;
        }
        enleverPv(erreurs);
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        jeuSolo.setBonus(false);
        jeuSolo.setBonusActive(false);
        ajoutNouveauMot(newCaretPos);
    }

    protected void moveToNextMotSansAjoutNouveauMot(int caretPos){
        int erreurs = 0;
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red; -fx-font-size: 18px;");
            erreurs++;
            newCaretPos++;
        }
        enleverPv(erreurs);
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        jeuSolo.enleverMotEnTeteDeFile();
        jeuSolo.setBonus(false);
        jeuSolo.setBonusActive(false);
        ictaArea.moveTo(newCaretPos+1);
        ictaArea.deleteText(1, ictaArea.getCaretPosition());
    }

    private void enleverPv(int erreurs){
        if(erreurs > 0){
            PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
            jeuSolo.ajoutPv(-erreurs);
            updateScene();
            if(jeuSolo.getPv()<=0){
                ictaArea.setDisable(true);
                jeuSolo.getTimer().cancel();
                time.stop();
                jeuSolo.getStats();
                affichageDonneeFinDeJeu();
            }
        }
    }

    private void testBonus(int length){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if(jeuSolo.getBonusActive()){
            jeuSolo.ajoutPv(length);
            updateScene();
        }
        jeuSolo.setBonus(false);
        jeuSolo.setBonusActive(false);
    }

    public void startTimerJeu(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if (!jeuSolo.getTimerActive()){
            jeuSolo.startTimerCompteur();
            startTimerAjoutMot();
        }
    }   
    
    public void startTimerAjoutMot(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        time = new Timeline(new KeyFrame(Duration.millis(Long.valueOf(jeuSolo.getVitesse()).doubleValue()),ae ->timerAjoutMot()));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void timerAjoutMot(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if(jeuSolo.getFile().size() >= 15){
            moveToNextMotSansAjoutNouveauMot(ictaArea.getCaretPosition());
            System.out.println(jeuSolo.getFile().size());
        }
        else {
            ajoutNouveauMotTimer(ictaArea.getCaretPosition());
            System.out.println(jeuSolo.getFile().size());
            ictaArea.moveTo(ictaArea.getCaretPosition()-1);
        }
    }

    @Override
    protected void ajoutNouveauMot(int caretPos){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        jeuSolo.resetCharUtilesTemporaire();
        if(jeuSolo.getFile().size()<8){
            String nouveauMot = jeuSolo.validerMot();
            ictaArea.appendText(nouveauMot+" ");
            ictaArea.moveTo(caretPos+1);
            ictaArea.deleteText(1, ictaArea.getCaretPosition());
            ictaArea.setStyle(ictaArea.getLength()-nouveauMot.length()-1, ictaArea.getLength(), "-fx-font-size: 18px;");
            if(rand.nextInt(100)<10){
                ajoutMotBonus(nouveauMot);
            }
        }
        else {
            ictaArea.moveTo(caretPos+1);
            ictaArea.deleteText(1, ictaArea.getCaretPosition());
            jeuSolo.enleverMotEnTeteDeFile();
        }
        
    }

    
    protected void ajoutNouveauMotTimer(int caretPos){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        //A voir
        //jeuSolo.resetCharUtilesTemporaire();

        String nouveauMot = jeuSolo.ajoutMotALaFile();
        ictaArea.appendText(nouveauMot+" ");
        ictaArea.setStyle(ictaArea.getLength()-nouveauMot.length()-1, ictaArea.getLength(), "-fx-font-size: 18px;");
        if(rand.nextInt(100)<10){
            ajoutMotBonus(nouveauMot);
        }
        ictaArea.moveTo(caretPos+1);
    }

    

    private void ajoutMotBonus(String mot){
        int length = mot.length()+1;
        int ictaLength = ictaArea.getLength();
        ictaArea.setStyle(ictaLength-length, ictaLength-1, "-fx-fill: blue; -fx-font-size: 18px;");
    }

    // public void setModele(ModeleJeu modele) {
    //     this.modele = modele;
    //     //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
    //     modele.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
    //         ictaArea.moveTo(1);
    //         ictaArea.setWrapText(true);
    //         ictaArea.setEditable(false);
    //         ictaArea.setShowCaret(CaretVisibility.ON);
    //         ictaArea.setStyle(0,ictaArea.getLength(),"-fx-font-size: 18px;");});
    // }

    public void setJeu(PartieSoloJeu jeu) {
        this.jeu = jeu;
        //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
        jeu.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
            ictaArea.moveTo(1);
            ictaArea.setWrapText(true);
            ictaArea.setEditable(false);
            ictaArea.setShowCaret(CaretVisibility.ON);
            ictaArea.setStyle(0,ictaArea.getLength(),"-fx-font-size: 18px;");});
    }

    public void updateScene(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        lblDonneeNiveau.setText(jeuSolo.getNiveau()+"");
        lblDonneeVie.setText(jeuSolo.getPv()+"");
    }

    @Override
    public void initializeScene(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        lblTextePrecision.setVisible(false);
        lblTexteRegularite.setVisible(false);
        lblTexteVitesse.setVisible(false);
        lblDonneePrecision.setVisible(false);
        lblDonneeRegularite.setVisible(false);
        lblDonneeVitesse.setVisible(false);
        lblDonneeNiveau.setText(jeuSolo.getNiveau()+"");
        lblDonneeVie.setText(jeuSolo.getPv()+"");

    }

    public void execNumMots(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        int numMots = jeuSolo.getNumMots();
        jeuSolo.setNumMots(numMots-1);
        if(numMots==0){
            //jeuSolo.setNumMots(1);
            jeuSolo.reinitialiserNumMots();
            jeuSolo.incrNiveau();
            updateScene();
            time.stop();
            startTimerAjoutMot();
        }
    }

    @Override
    public void affichageDonneeFinDeJeu(){

        lblTexteVie.setVisible(false);
        lblDonneeVie.setVisible(false);
        lblTexteNiveau.setVisible(false);
        lblDonneeNiveau.setVisible(false);
        
        lblTextePrecision.setVisible(true);
        lblTexteRegularite.setVisible(true);
        lblTexteVitesse.setVisible(true);
        lblDonneePrecision.setVisible(true);
        lblDonneeRegularite.setVisible(true);
        lblDonneeVitesse.setVisible(true);

        DecimalFormat df = new DecimalFormat("0.00");
        lblDonneePrecision.setText(df.format(jeu.getStatsPrecision())+"");
        lblDonneeRegularite.setText(df.format(jeu.getStatsRegularite())+"");
        lblDonneeVitesse.setText(df.format(jeu.getStatsVitesse())+"");
        

        

    }


}
