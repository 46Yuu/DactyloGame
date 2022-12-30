package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class SoloJeuSceneController extends SoloNormalSceneController{

    @Override
    @FXML
    void areaOnKeyPressed(KeyEvent event){
        System.out.println(" Partie solo jeu key pressed");
    }
    
}
