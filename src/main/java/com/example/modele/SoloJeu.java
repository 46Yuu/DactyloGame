package com.example.modele;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class SoloJeu extends SoloNormal{


    protected boolean bonus = false;
    protected boolean bonusActive = false;
    public static final int nombreDeMotPourChangerDeNiveau = 1;
    protected int pv = 50;
    protected int niveau = 1;
    protected int numMots;

    public SoloJeu(Parametre p) {
        super(p);
        reinitialiserNumMots();
        this.initializerFiles();
    }
    
    public void reinitialiserNumMots(){
        numMots = nombreDeMotPourChangerDeNiveau;
    }
    @Override
    public String validerMot(){
        String s = "";
        if(!file.isEmpty()) file.removeFirst();
        s = ajoutMotALaFile();
        return s;
    }

    public int getNumMots(){
        return numMots;
    }

    public void setNumMots(int numMots){
        this.numMots = numMots;
    }

    public long getVitesse(){
        return 3*Math.round((Math.pow((0.9),niveau))*1000);
        //return 3*Math.round(Math.pow((0.5),niveau))*1000;
        /*     switch(niveau){
            case 1: 
                return 1000;
            case 2:
                return 500;
            default:
                return 1000*(1/niveau);
        }*/
    }

    public boolean getBonus(){
        return bonus;
    }

    public void setBonus(Boolean newBool){
        bonus = newBool;
    }

    public boolean getBonusActive(){
        return bonusActive;
    }

    public void setBonusActive(Boolean newBool){
        bonusActive = newBool;
    }

    @Override
    public void initializerFiles(){
        System.out.println("-----------------------------------------------------------------------");
        file = new LinkedList<String>();
        fileSnd = new LinkedList<String>();
        String[] texteSplit = parametre.getTexteATaper().split(" ");
        for (int i=0; i < texteSplit.length; i++)
        {
            //System.out.println(texteSplit[i]);
            fileSnd.add(texteSplit[i]);
        }


        //Ensuite on envoie les 15 premiers mots dans la file principale
        for (int i=0; i < 8; i++)
        {
            //System.out.println(texteSplit[i]);
            ajoutMotALaFile();
        }
    }

    @Override
    public void startTimerCompteur(){
        timerActive = true;
        timer = new Timer();
        TimerTask taskCounterEcartType = new TimerTask() {
            @Override
            public void run() {
                tempsEntreChaqueCharUtile++;
            }
        };
        timer.scheduleAtFixedRate(taskCounterEcartType,0*1000, 1*1000);
    }

    public Timer getTimer(){
        return timer;
    }

    public int getNiveau(){
        return niveau;
    }

    public void incrNiveau(){
        niveau++;
    }

    public int getPv(){
        return pv;
    }

    public void ajoutPv(int val){
        pv+=val;
    }

    public static int getNombredemotpourchangerdeniveau() {
        return nombreDeMotPourChangerDeNiveau;
    }

}
