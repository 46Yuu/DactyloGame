package com.example.controller;

import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.Caret.CaretVisibility;

import com.example.modele.PartieSoloNormal;

import java.text.DecimalFormat;
import java.util.Random;

import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SoloNormalSceneController {
    //ModeleNormal modele;
    Random rand = new Random();
    Timeline time;
    protected PartieSoloNormal jeu;

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


    /**
     * Fonction appellé à chaque appui d'une touche du clavier 
     * qui va lancer une fonction par rapport a la touche appuyé.
     * @param event Si la touche est 'espace', elle va valider le mot.
     * @param event Si la touche est 'backspace', elle va supprimer le
     * charactère précédent écrit.
     * @param event Si c'est une touche alphanumérique, elle va vérifier
     * le charactère à la position du curseur et appeller la fonction charCorrect
     * si elles correspondent.
     * @param event Si c'est une touche alphanumérique, mais qu'elle ne 
     * correspond pas au charactère, on va appeller la fonction charIncorrect.
     * @param event Sinon, on va bloquer la touche pour ne rien écrire.
     */ 
    @FXML
    void areaOnKeyPressed(KeyEvent event){
        startTimer();
        int caretPos = ictaArea.getCaretPosition();
        String charTyped = event.getText();
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

    /** 
     * Fonction permettant de lancer toutes les fonctions nécessaires 
     * pour les différents timers du mode solo normal. Et affiche sur
     * l'interface graphique le timer du temps restant. 
     */
    private void startTimer(){
        if (!jeu.getTimerActive()){
            updateTimer();
            jeu.startTimerNormal();
            lblTexteTime.setVisible(true);
            lblDonneeTime.setVisible(true);
        }
    }

    /**
     * Fonction qui execute tout ce qui est nécessaire quand on 
     * écrit un caractère correcte. 
     * @param caretPos Position actuelle du curseur 
     * @return true si le caractère a la position du curseur 
     * est l'espace après la fin du mot, false sinon.
     */
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

    /**
     * Fonction qui execute tout ce qui est nécessaire quand on 
     * écrit un caractère incorrecte.
     * @param caretPos Position actuelle du curseur
     * @return true si le caractère a la position du curseur 
     * est l'espace après la fin du mot, false sinon.
     */
    protected boolean charIncorrecte(int caretPos){
        if(!verificationFinDuMot(caretPos)){
            ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red; -fx-font-size: 18px;");
            jeu.incrNbAppuiTouches();
            ictaArea.moveTo(caretPos+1);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Fonction qui execute tout ce qui est nécessaire quand on
     * veut supprimer le dernier caractère écrit.
     * @param caretPos Position actuelle du curseur
     * @return true si le caractère a la position du curseur 
     * est l'espace avant le début du mot , false sinon.
     */
    protected boolean backSpace(int caretPos){
        if(!verificationDebutDuMot(caretPos)){
            ictaArea.setStyle(caretPos-1, caretPos, "-fx-fill: black; -fx-font-size: 18px;");
            String previousCharStyle = ictaArea.getStyleAtPosition(caretPos);
            if(previousCharStyle.compareTo("-fx-fill: green; -fx-font-size: 18px;") == 0){
                jeu.decrCharUtilesTemporaire();
            }
            ictaArea.moveTo(caretPos-1);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Fonction qui va s'occuper de la vérification du mot quand on 
     * appuie sur 'espace'. Si le mot n'est pas fini , la fonction 
     * 'moveToNextMot' sera appellé, sinon on vérifie que chaque 
     * charactère du mot est correcte puis ajoute le mot suivant 
     * à la file.
     * @param caretPos Position actuelle du curseur
     */
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
                jeu.ajoutCharUtilesTemporaire();
            }
            ajoutNouveauMot(memCaretPos);
        }
    }

    /**
     * Verifie si le charactère suivant à partir de la position
     * du curseur est un 'espace' ou non. 
     * @param caretPos Position actuelle du curseur
     * @return true si c'est la fin du mot , false sinon.
     */
    protected boolean verificationFinDuMot(int caretPos){
        return ictaArea.getText(caretPos,caretPos+1).compareTo(" ") == 0;
    }

    /**
     * Verifie si le charactère précédent à partir de la position
     * du curseur est un 'espace' ou non. 
     * @param caretPos Position actuelle du curseur
     * @return true si c'est le début du mot , false sinon.
     */
    protected boolean verificationDebutDuMot(int caretPos){
        return ictaArea.getText(caretPos-1,caretPos).compareTo(" ") == 0;
    }

    /**
     * Fini le mot actuelle en comptant le reste des charactères du mot 
     * comme incorrectes puis en ajoutant un nouveau mot. 
     * @param caretPos Position actuelle du curseur
     */
    protected void moveToNextMot(int caretPos){
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red; -fx-font-size: 18px;");
            newCaretPos++;
        }
        ajoutNouveauMot(newCaretPos);
    }

    /**
     * Ajoute un nouveau mot et mets a jour les stats de la partie.
     * @param caretPos Position actuelle du curseur
     */
    protected void ajoutNouveauMot(int caretPos){
        jeu.resetCharUtilesTemporaire();
        String nouveauMot = jeu.validerMot();
        ictaArea.appendText(nouveauMot+" ");
        ictaArea.setStyle(ictaArea.getLength()-nouveauMot.length(), ictaArea.getLength()-1, "-fx-font-size: 18px;");
        ictaArea.moveTo(caretPos+1);
    }

    /**
     * Mets a jour le timer du temps restant.
     */
    protected void updateTimer(){
        time = new Timeline(new KeyFrame(Duration.millis(1000),ae ->updateCountdown()));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    /**
     * Mets a jour l'affichage du timer restant.
     */
    protected void updateCountdown(){
        if(jeu.getCountdown()>0){
            jeu.decrCountdown();
            lblDonneeTime.setText(jeu.getCountdown()+"");   
        }
        else {
            time.stop();
            //afficher stats
            affichageDonneeFinDeJeu();
        }
    }


    /**
     * Initialise la scene d'affichage.
     */
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

    /**
     * Affiche les statistiques à la fin de la partie.
     */
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
        lblDonneePrecision.setText(df.format(jeu.getStatsPrecision())+"");
        lblDonneeRegularite.setText(df.format(jeu.getStatsRegularite())+"");
        lblDonneeVitesse.setText(df.format(jeu.getStatsVitesse())+"");
    }


    /**
     * Initialise le modèle pour le controlleur.
     * @param jeu mode Normal 
     */
    public void setJeu(PartieSoloNormal jeu) {
        this.jeu = jeu;

        /**
         * Ajout du listener qui va modifier l'affichage à chaque changement c'est-à-dire
         * à chaque appel de notify observers du jeu
         */
        jeu.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
            ictaArea.moveTo(1);
            ictaArea.setWrapText(true);
            ictaArea.setEditable(false);
            ictaArea.setShowCaret(CaretVisibility.ON);
            ictaArea.setStyle(0,ictaArea.getLength(),"-fx-font-size: 18px;");});
    }
}
