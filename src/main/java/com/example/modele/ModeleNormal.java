package com.example.modele;

import java.util.LinkedList;
import java.util.List;



public class ModeleNormal {

    /**
     * TODO Utiliser une liste de paire d'entier pour savoir quelles position colorier
     */

    protected SoloNormal jeu;

    

    public ModeleNormal(SoloNormal jeu) {
        this.jeu = jeu;
    }




    public interface Listener {
        void onChange(ModeleNormal modele);
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
        setBeginningText(jeu.getStringOfFile());

        notifyObservers();
    }

    /**
     * Permet d'envoyer l'info de la modification du texte à tous les listeners du modele
     */
    private void notifyObservers() {
        listeners.stream().forEach(l -> l.onChange(this));
    }

    public SoloNormal getJeu(){
        return jeu;
    }
}

