package com.example.modele;

import java.util.LinkedList;
import java.util.List;

import com.example.vue.*;

import javafx.application.Application;
import javafx.stage.Stage;



public class Modele {

    /**
     * TODO Utiliser une liste de paire d'entier pour savoir quelles position colorier
     */
    public interface Listener {
        void onChange(Modele modele);
    }

    private List<Listener> listeners = new LinkedList<Listener>();
    private String beginningText;


    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public String getBeginningText() {
        return beginningText;
    }

    public void setBeginningText(String beginningText) {
        this.beginningText = beginningText;
        notifyObservers();
    }

    public void initialize(){
        beginningText = "Lorem ipsum dolor sit amet.\n" + 
        
        "Praesent non sodales ex. Donec posuere vestibulum leo, vel scelerisque nibh commodo nec. Sed ultrices " + 
        "sagittis aliquam. Proin quam quam, tincidunt sit amet pulvinar id, suscipit ut lacus. Nunc eget sagittis " + 
        "arcu. Fusce vulputate scelerisque lacus non tempus. Integer cursus vulputate justo, vitae maximus tellus " + 
        "fermentum consectetur. Suspendisse vestibulum dolor et metus tincidunt fermentum. ";

        notifyObservers();
    }

    /**
     * Permet d'envoyer l'info de la modification du texte à tous les listeners du modele
     */
    private void notifyObservers() {
        listeners.stream().forEach(l -> l.onChange(this));
    }

}

