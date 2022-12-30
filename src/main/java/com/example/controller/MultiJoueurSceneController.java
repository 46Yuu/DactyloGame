package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class MultiJoueurSceneController extends SoloNormalSceneController{
    @Override
    @FXML
    void areaOnKeyPressed(KeyEvent event){
        System.out.println(" Partie multijoueur key pressed");
    }
    
}
