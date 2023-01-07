package com.example.controller;

import org.fxmisc.richtext.Caret.CaretVisibility;

import com.example.modele.PartieSoloJeu;

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
import javafx.stage.Stage;


public class SoloJeuSceneController extends SoloNormalSceneController{

    PartieSoloJeu jeu;
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

    private Stage stage;

    public void setStage(Stage s) {
        stage = s;
    }
    

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

    /**
     * Met à jour les variables de mot bonus si le mot
     * actuel en est un.
     * @param caretPos
     */
    private void isMotBonus(int caretPos){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if((ictaArea.getStyleAtPosition(caretPos+1).compareTo("-fx-fill: blue; -fx-font-size: 18px;") == 0) && !jeuSolo.getBonus()){
            jeuSolo.setBonus(true);
            jeuSolo.setBonusActive(true);
        }
    }

    /**
     * Fonction qui va s'occuper de la vérification du mot quand on 
     * appuie sur 'espace'. Si le mot n'est pas fini , la fonction 
     * 'moveToNextMot' sera appellé, sinon on vérifie que chaque 
     * charactère du mot est correcte puis ajoute le mot suivant 
     * à la file. Appelle à la fin testBonus() pour verifier 
     * l'état des booleans.
     * @param caretPos Position actuelle du curseur
     */
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
                execNumMots();
            }
            else {
                enleverPv(erreurs);
            }
            testBonus(length);
            ajoutNouveauMot(memCaretPos);
            updateScene();
        }
    }

    /**
     * Fonction qui execute tout ce qui est nécessaire quand on 
     * écrit un caractère correcte. 
     * @param caretPos Position actuelle du curseur 
     * @return true si le caractère a la position du curseur 
     * est l'espace après la fin du mot, false sinon.
     */
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
    
    /**
     * Fonction qui execute tout ce qui est nécessaire quand on 
     * écrit un caractère incorrecte.
     * @param caretPos Position actuelle du curseur
     * @return true si le caractère a la position du curseur 
     * est l'espace après la fin du mot, false sinon.
     */
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

    /**
     * Fonction qui execute tout ce qui est nécessaire quand on
     * veut supprimer le dernier caractère écrit.
     * @param caretPos Position actuelle du curseur
     * @return true si le caractère a la position du curseur 
     * est l'espace avant le début du mot , false sinon.
     */
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

    /**
     * Fini le mot actuelle en comptant le reste des charactères du mot 
     * comme incorrectes puis en ajoutant un nouveau mot. 
     * @param caretPos Position actuelle du curseur
     */
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

    /**
     * Fini le mot actuelle à cause du timer qui fait que la file
     * principale soit pleine et compte le reste des charactères du mot 
     * comme incorrectes puis en ajoutant un nouveau mot.
     * @param caretPos Position actuelle du curseur
     */
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

    /**
     * Fonction qui enleve un nombre de pv équivalent au
     * nombre d'erreurs dans le mot qui vient d'être validé.
     * @param erreurs
     */
    private void enleverPv(int erreurs){
        if(erreurs > 0){
            PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
            jeuSolo.ajoutPv(-erreurs);
            updateScene();
            if(jeuSolo.getPv()<=0){
                ictaArea.setDisable(true);
                time.stop();
                jeuSolo.endTimer();
                affichageDonneeFinDeJeu();
            }
        }
    }

    /**
     * Fonctio qui vérifie que le bonus actuel est
     * toujours actif et si il l'est , soigne le joueur
     * un chiffre égale à la longueur du mot.
     * @param length
     */
    private void testBonus(int length){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if(jeuSolo.getBonusActive()){
            jeuSolo.ajoutPv(length);
            updateScene();
        }
        jeuSolo.setBonus(false);
        jeuSolo.setBonusActive(false);
    }

    /**
     * Fonction qui lance les timers du jeu solo.
     */
    public void startTimerJeu(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        if (!jeuSolo.getTimerActive()){
            jeuSolo.startTimerCompteur();
            startTimerAjoutMot();
        }
    }   
    
    /**
     * Fonction qui lance le timer qui va ajouter des
     * des mots au joueur régulièrement.
     */
    public void startTimerAjoutMot(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        time = new Timeline(new KeyFrame(Duration.millis(Long.valueOf(jeuSolo.getVitesse()).doubleValue()),ae ->timerAjoutMot()));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    /**
     * Fonction appartenant au timer d'ajout de mot
     * qui va s'occuper d'ajouter ceux ci selon la 
     * taille de la file principale.
     */
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

    /**
     * Ajoute un nouveau mot et mets a jour les stats de la partie.
     * @param caretPos Position actuelle du curseur
     */
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

    /**
     * Ajoute un nouveau mot sans enlever le premier mot
     * de la file principal. 
     * @param caretPos Position actuelle du curseur
     */
    protected void ajoutNouveauMotTimer(int caretPos){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        String nouveauMot = jeuSolo.ajoutMotALaFile();
        ictaArea.appendText(nouveauMot+" ");
        ictaArea.setStyle(ictaArea.getLength()-nouveauMot.length()-1, ictaArea.getLength(), "-fx-font-size: 18px;");
        if(rand.nextInt(100)<10){
            ajoutMotBonus(nouveauMot);
        }
        ictaArea.moveTo(caretPos+1);
    }

    /**
     * Fonction qui va changer l'affichage du dernier mot
     * ajouté en bleu pour indiquer que c'est un mot bonus.
     * @param mot
     */
    private void ajoutMotBonus(String mot){
        int length = mot.length()+1;
        int ictaLength = ictaArea.getLength();
        ictaArea.setStyle(ictaLength-length, ictaLength-1, "-fx-fill: blue; -fx-font-size: 18px;");
    }

    /**
     * Fonction qui initialise l'affichage de la
     * partie.
     * @param jeu
     */
    public void setJeu(PartieSoloJeu jeu) {
        this.jeu = jeu;
        jeu.addListener(l -> {ictaArea.replaceText(l.getBeginningText());
            ictaArea.moveTo(1);
            ictaArea.setWrapText(true);
            ictaArea.setEditable(false);
            ictaArea.setShowCaret(CaretVisibility.ON);
            ictaArea.setStyle(0,ictaArea.getLength(),"-fx-font-size: 18px;");});
    }

    /**
     * Mets a jour l'affichage.
     */
    public void updateScene(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        lblDonneeNiveau.setText(jeuSolo.getNiveau()+"");
        lblDonneeVie.setText(jeuSolo.getPv()+"");
    }

    /**
     * Initialise l'affichage de base de la partie.
     */
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

    /** 
     * Augmente le nombre de mots avant d'augmenter
     * de niveau , et augmente le niveau si le nombre
     * de mots nécessaire est atteint.
     */
    public void execNumMots(){
        PartieSoloJeu jeuSolo = (PartieSoloJeu)(jeu);
        int numMots = jeuSolo.getNumMots();
        jeuSolo.setNumMots(numMots-1);
        if(numMots==0){
            jeuSolo.reinitialiserNumMots();
            jeuSolo.incrNiveau();
            updateScene();
            time.stop();
            startTimerAjoutMot();
        }
    }

    /**
     * Affiche les stats a la fin de la partie.
     */
    @Override
    public void affichageDonneeFinDeJeu(){
        DecimalFormat df = new DecimalFormat("0.00");
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
        lblDonneePrecision.setText(df.format(jeu.getStatsPrecision())+"");
        lblDonneeRegularite.setText(df.format(jeu.getStatsRegularite())+"");
        lblDonneeVitesse.setText(df.format(jeu.getStatsVitesse())+"");
    }
}
