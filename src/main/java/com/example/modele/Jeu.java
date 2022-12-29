package com.example.modele;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Jeu {
    private Parametre parametre;
    private double charUtiles = 0;
    private double charUtilesTemporaire = 0;
    private double nbAppuiTouches = 0;
    private Timer timer;
    private int resPourCalculMoyenne = 0;
    private int tempsEntreChaqueCharUtile = 0;
    boolean timerActive = false;
    boolean bonus = false;
    DecimalFormat df = new DecimalFormat("0.00");
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
     * son affichage sur l'interface graphique
     */
    public String validerMot(){
        String s = "";
        if(!file.isEmpty()) file.removeFirst();
        if(parametre.getMode().compareTo("jeu")==0){
            if(file.size()<8){
                s = ajoutMotALaFile();
            }
        }
        else {
            s = ajoutMotALaFile();
        }
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

    public Parametre getParametre(){
        return parametre;
    }

    public String getStringOfFile(){
        String res = " ";
        for(int i = 0;i<file.size(); i++){
            res += file.get(i)+" ";
        }
        return res;
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
                double mpm = (charUtiles/(0.5))/5;
                System.out.println("char utiles : "+charUtiles);
                System.out.println("appuie touches : "+nbAppuiTouches);
                double precision = (charUtiles/nbAppuiTouches)*100;
                double ecartType = calculEcartType();
                System.out.println("----------STATS----------");
                System.out.println("Vitesse : "+mpm);
                System.out.println("Précision : "+df.format(precision));
                System.out.println("Regularité : "+df.format(ecartType));
                timer.cancel();
            }
        };
        timer.scheduleAtFixedRate(taskCounterEcartType,0*1000, 1*1000);
        timer.schedule(task,30*1000); 
    }

    /* 
     * TimerTask taskCounterEcartType = new TimerTask() {
            @Override
            public void run() {
                if(file.size() == 15){
                    trouver un moyen de lancer verificationMot(caretPos) d'ici , ou mettre ce timertask+schedule dans mainSceneController
                }
                else {
                    ajoutMotALaFile();
                }
            }
        };
        timer.scheduleAtFixedRate(taskAjoutMot, 0*1000, vitesse(niveau);
    */
    public long vitesse(int niveau){
        return 3*Math.round(Math.pow((0.9),niveau))*1000;
    }

    public Timer getTimer(){
        return this.timer;
    }
    public boolean getTimerActive(){
        return timerActive;
    }

    private double calculEcartType(){
        if(charUtiles == 0){
            return 0;
        }
        else{
            return resPourCalculMoyenne/charUtiles;
        }
    }

    public boolean getBonus(){
        return bonus;
    }
    public void setBonus(Boolean newBool){
        bonus = newBool;
    }
}
