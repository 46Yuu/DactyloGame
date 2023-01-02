package com.example.controller;

import java.util.Random;
import java.util.TimerTask;

import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.Caret.CaretVisibility;

import com.example.modele.ModeleSolo;
import com.example.modele.JeuSolo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class SoloJeuSceneController extends SoloNormalSceneController{

    ModeleSolo modele;
    Random rand = new Random();
    Timeline time;

   
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
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        if((ictaArea.getStyleAtPosition(caretPos+1).compareTo("-fx-fill: blue; -fx-font-size: 18px;") == 0) && !jeuSolo.getBonus()){
            jeuSolo.setBonus(true);
            jeuSolo.setBonusActive(true);
        }
    }

    @Override
    protected void verificationMot(int caretPos){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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

    
    @Override
    protected boolean charIncorrecte(int caretPos){
        if(!verificationFinDuMot(caretPos)){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red; -fx-font-size: 18px;");
            modele.getJeu().incrNbAppuiTouches();
            ictaArea.moveTo(caretPos+1);
            JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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
            JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        jeuSolo.enleverMotEnTeteDeFile();
        jeuSolo.setBonus(false);
        jeuSolo.setBonusActive(false);
        ictaArea.moveTo(newCaretPos+1);
        ictaArea.deleteText(1, ictaArea.getCaretPosition());
    }

    private void enleverPv(int erreurs){
        if(erreurs > 0){
            JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
            jeuSolo.ajoutPv(-erreurs);
            updateScene();
            if(jeuSolo.getPv()<=0){
                jeuSolo.getTimer().cancel();
                time.stop();
                jeuSolo.getStats();
            }
        }
    }

    private void testBonus(int length){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        if(jeuSolo.getBonusActive()){
            jeuSolo.ajoutPv(length);
            updateScene();
        }
        jeuSolo.setBonus(false);
        jeuSolo.setBonusActive(false);
    }

    public void startTimerJeu(){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        if (!jeuSolo.getTimerActive()){
            jeuSolo.startTimerCompteur();
            startTimerAjoutMot();
        }
    }   
    
    public void startTimerAjoutMot(){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        time = new Timeline(new KeyFrame(Duration.millis(Long.valueOf(jeuSolo.getVitesse()).doubleValue()),ae ->timerAjoutMot()));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void timerAjoutMot(){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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
    /* 
    public void startTimerAjoutMot(){
        taskAjoutMot = new TimerTask() {
            @Override
            public void run() {
                if(modele.getJeu().getFile().size() == 15){
                    verificationMot(ictaArea.getCaretPosition());
                }
                else {
                    modele.getJeu().ajoutMotALaFile();
                }
            }
        };
        modele.getJeu().getTimer().scheduleAtFixedRate(taskAjoutMot, 0*1000, modele.getJeu().getVitesse());
    }*/

    @Override
    protected void ajoutNouveauMot(int caretPos){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        jeuSolo.resetCharUtilesTemporaire();
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

    public void setModele(ModeleSolo modele) {
        this.modele = modele;
        //modele.addListener(l -> {staArea.replaceText(l.getBeginningText());ictaArea.replaceText(l.getBeginningText());ictaArea.start(SelectionPolicy.CLEAR);});
        modele.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
            ictaArea.moveTo(1);
            ictaArea.setWrapText(true);
            ictaArea.setEditable(false);
            ictaArea.setShowCaret(CaretVisibility.ON);
            ictaArea.setStyle(0,ictaArea.getLength(),"-fx-font-size: 18px;");});
    }

    public void updateScene(){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        lblDonneeNiveau.setText(jeuSolo.getNiveau()+"");
        lblDonneeVie.setText(jeuSolo.getPv()+"");
    }

    @Override
    public void initializeScene(){
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
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
        JeuSolo jeuSolo = (JeuSolo)(modele.getJeu());
        int numMots = jeuSolo.getNumMots();
        jeuSolo.setNumMots(numMots+1);
        if(numMots==3){
            jeuSolo.setNumMots(0);
            jeuSolo.incrNiveau();
            updateScene();
            time.stop();
            startTimerAjoutMot();
        }
    }


}
