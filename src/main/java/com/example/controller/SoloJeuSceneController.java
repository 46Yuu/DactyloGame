package com.example.controller;

import java.util.Random;

import org.fxmisc.richtext.InlineCssTextArea;

import com.example.modele.Modele;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SoloJeuSceneController extends SoloNormalSceneController{

    Modele modele;
    Random rand = new Random();

   
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
        System.out.println(" Partie solo jeu key pressed");
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
        if(ictaArea.getStyleAtPosition(caretPos+1).compareTo("-fx-fill: blue; -fx-font-size: 18px;") == 0){
            modele.getJeu().setBonus(true);
        }
    }

    @Override
    protected void charIncorrecte(int caretPos){
        ictaArea.setStyle(caretPos, caretPos+1, "-fx-fill: red; -fx-font-size: 18px;");
        modele.getJeu().incrNbAppuiTouches();
        ictaArea.moveTo(caretPos+1);
        modele.getJeu().setBonus(false);
    }

    @Override
    protected void verificationMot(int caretPos){
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
                caretPos--;
            }
            if(motCorrecte){
                modele.getJeu().ajoutCharUtilesTemporaire();
            }
            testBonus();
            ajoutNouveauMot(memCaretPos);
        }
    }

    @Override
    protected void moveToNextMot(int caretPos){
        int erreurs = 0;
        int newCaretPos = caretPos;
        while(!verificationFinDuMot(newCaretPos)){
            ictaArea.setStyle(newCaretPos, newCaretPos+1, "-fx-underline: true; -fx-fill: red; -fx-font-size: 18px;");
            erreurs++;
            modele.getJeu().incrNbAppuiTouches();
            newCaretPos++;
        }
        testBonus();
        ajoutNouveauMot(newCaretPos);
    }

    private void testBonus(){
        if(modele.getJeu().getBonus()){
            //heal
        }
        modele.getJeu().setBonus(false);
    }

    @Override
    protected void ajoutNouveauMot(int caretPos){
        modele.getJeu().resetCharUtilesTemporaire();
        String nouveauMot = modele.getJeu().validerMot();
        ictaArea.appendText(nouveauMot+" ");
        ictaArea.setStyle(ictaArea.getLength()-nouveauMot.length(), ictaArea.getLength()-1, "-fx-font-size: 18px;");
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
        ictaArea.setStyle(ictaLength-length, ictaLength-1, "-fx-fill: blue; -fx-font-size: 18px;");
    }

    @Override
    public void initializeScene(){
        lblTextePrecision.setVisible(false);
        lblTexteRegularite.setVisible(false);
        lblTexteVitesse.setVisible(false);
        lblDonneePrecision.setVisible(false);
        lblDonneeRegularite.setVisible(false);
        lblDonneeVitesse.setVisible(false);

    }
}
