package com.example.modele;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Jeu {
    private Parametre parametre;
    private int charUtiles = 0;
    private int charUtilesTemporaire = 0;
    private int nbAppuiTouches = 0;
    private Timer timer;
    private int resPourCalculMoyenne = 0;
    private int tempsEntreChaqueCharUtile = 0;
    boolean timerActive = false;
    /*La taille maximale d'élements pouvant rentrer dans la file de mots */
    private static final int tailleMaxFileDeMot = 15;

    /* La file des 15 prochain mots à taper. Utiliser add() removeFirst() et isEmpty  */
    private LinkedList<String> file;
    private LinkedList<String> fileSnd;

    public Jeu(Parametre p) {
        parametre = p;
        this.initializerFiles();
    }

    /*
     * Vérifie si la file est pleine ou non. Renvoie true si il ya encore de l'espace et false sinon
     */
    public boolean fileNonPleine(){
        return file.size() < tailleMaxFileDeMot;
    }

    /*
     * Ajoute le prochain mot à la file
     */
    public String ajoutMotALaFile(){
        //On enleve le premier élement de la fileTemp qu'on ajoute à la fin de la file
        if(!fileSnd.isEmpty())
        {
            String s = fileSnd.removeFirst();
            file.add(s);
            return s;
        }
        return "";

    }

    /*
     * Supprime le mot en tête de file pour la validation et rajoute le prochain puis le retourne pour
     * son affichage sur l'nterface graphique
     */
    public String validerMot(){
        if(!file.isEmpty()) file.removeFirst();
        String s = ajoutMotALaFile();
        return s;
    }

    
    /*
     * Initialize la file des éléments à taper
     */
    public void initializerFiles(){
        file = new LinkedList<String>();
        fileSnd = new LinkedList<String>();
        String[] texteSplit = parametre.getTexteATaper().split(" ");
        for (int i=0; i < texteSplit.length; i++)
        {
            //System.out.println(texteSplit[i]);
            fileSnd.add(texteSplit[i]);
        }


        //Ensuite on envoie les 15 premiers mots dans la file principale
        for (int i=0; i < 15; i++)
        {
            //System.out.println(texteSplit[i]);
            ajoutMotALaFile();
        }
    }

    public LinkedList<String> getFile() {
        return file;
    }

    public LinkedList<String> getFileSnd() {
        return fileSnd;
    }
    
    public void incrCharUtilesTemporaire() {
        charUtilesTemporaire++;
    }
    public void decrCharUtilesTemporaire() {
        charUtilesTemporaire--;
    }

    public void incrNbAppuiTouches() {
        nbAppuiTouches++;
    }

    public void ajoutCharUtilesTemporaire(){
        charUtiles += charUtilesTemporaire;
    }

    public void resetCharUtilesTemporaire(){
        charUtilesTemporaire = 0;
    }

    public void ajoutTempsCharUtile(){
        resPourCalculMoyenne+=tempsEntreChaqueCharUtile;
        tempsEntreChaqueCharUtile = 0;
    }

    public void startTimerNormal(){
        timerActive = true;
        timer = new Timer();
        TimerTask taskCounterEcartType = new TimerTask() {
            @Override
            public void run() {
                tempsEntreChaqueCharUtile++;
            }
        };
        TimerTask task = new TimerTask(){ 
            @Override
            public void run(){
                int mpm = (charUtiles/1)/5;
                int precision = (charUtiles/nbAppuiTouches)*100;
                int ecartType = resPourCalculMoyenne/charUtiles;
                System.out.println("----------STATS----------");
                System.out.println("Vitesse : "+mpm);
                System.out.println("Précision : "+precision);
                System.out.println("Regularité : "+ecartType);
                timer.cancel();
            }
        };
        timer.scheduleAtFixedRate(taskCounterEcartType,0*1000, 1*1000);
        timer.schedule(task,60*1000); 
    }

    public boolean getTimerActive(){
        return timerActive;
    }
}
