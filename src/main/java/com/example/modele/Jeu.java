package com.example.modele;

import java.util.LinkedList;

public class Jeu {
    private Parametre parametre;
    /*La taille maximale d'élements pouvant rentrer dans la file de mots */
    private static final int tailleMaxFileDeMot = 15;

    /* La file des 15 prochain mots à taper. Utiliser add() removeFirst() et isEmpty  */
    private LinkedList<String> file;

    /*
     * Vérifie si la file est pleine ou non. Renvoie true si il ya encore de l'espace et false sinon
     */
    public boolean fileNonPleine(){
        return file.size() < tailleMaxFileDeMot;
    }

    /*
     * Ajoute un mot à la file si bien sur il y'a de l'espace
     */
    public void ajoutMotALaFile(String  mot){
        if(fileNonPleine()) file.add(mot);

    }

    /*
     * Supprime le mot en tête de file pour la validation
     */
    public void validerMot(){
        if(!file.isEmpty()) System.out.println(file.removeFirst());
    }

    /*
     * TODO: les test unitaires
     * voir avec Henri pour la file
     * Creer les interfaces des autres jeux
     * Remplir la file
     */
    
}
