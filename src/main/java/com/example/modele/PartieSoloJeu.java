package com.example.modele;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class PartieSoloJeu extends PartieSoloNormal{

    protected boolean bonus = false;
    protected boolean bonusActive = false;
    public static final int nombreDeMotPourChangerDeNiveau = 3;
    protected int pv = 50;
    protected int niveau = 1;
    protected int numMots;

    public PartieSoloJeu(Parametre p) {
        super(p);
        reinitialiserNumMots();
        pv = p.getVies();
        niveau = p.getNiveau();
        this.initializerFiles();
    }
    
    /**
     * Fonction pour réinitialiser le compteur du nombre de mot avant
     * le prochain niveau.
     */
    public void reinitialiserNumMots(){
        numMots = nombreDeMotPourChangerDeNiveau;
    }

    /**
     * Fonction qui permet de valider le premier mot de la file en 
     * l'enlevant et en ajoutant à la fin de la file un nouveau mot.
     * @return Le String du nouveau mot ajouté à la file.
     */
    @Override
    public String validerMot(){
        String s = "";
        if(!file.isEmpty()) file.removeFirst();
        s = ajoutMotALaFile();
        return s;
    }

    /**
     * Getter du compteur du nombre de mot avant le prochain niveau.
     * @return int avec le nombre de mot.
     */
    public int getNumMots(){
        return numMots;
    }

    /**
     * Setter du compteur du nombre de mot avant le prochain niveau/
     * @param numMots int avec le nouveau nombre de mot. 
     */
    public void setNumMots(int numMots){
        this.numMots = numMots;
    }

    /**
     * Getter de la vitesse d'apparation des mots.
     * @return long avec la vitesse.
     */
    public long getVitesse(){
        return 3*Math.round((Math.pow((0.9),niveau))*1000);
    }

    /**
     * Getter pour savoir si le mot est un mot bonus.
     * @return boolean
     */
    public boolean getBonus(){
        return bonus;
    }

    /**
     * Setter pour le boolean qui indique si c'est un mot
     * bonus.
     * @param newBool 
     */
    public void setBonus(Boolean newBool){
        bonus = newBool;
    }

    /**
     * Getter indiquant si le bonus sur le mot actuel est toujours
     * actif.
     * @return boolean 
     */
    public boolean getBonusActive(){
        return bonusActive;
    }

    /**
     * Setter pour modifier le boolean qui indique si le bonus du mot
     * actuel est actif ou non.
     * @param newBool
     */
    public void setBonusActive(Boolean newBool){
        bonusActive = newBool;
    }

    /**
     * Fonction permettant d'initialiser les files de mots 
     * et de mettre les premiers mots dans la file.
     */
    @Override
    public void initializerFiles(){
        System.out.println("-----------------------------------------------------------------------");
        file = new LinkedList<String>();
        fileSnd = new LinkedList<String>();
        String[] texteSplit = parametre.getTexteATaper().split(" ");
        for (int i=0; i < texteSplit.length; i++){
            fileSnd.add(texteSplit[i]);
        }
        //Ensuite on envoie les 15 premiers mots dans la file principale
        for (int i=0; i < 8; i++){
            ajoutMotALaFile();
        }
    }

    /**
     * Fonction qui va lancer le timer qui va compter les secondes 
     * entre chaque caractère utile tapé.
     */
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

    /**
     * Getter du timer
     * @return Timer 
     */
    public Timer getTimer(){
        return timer;
    }

    /** 
     * Getter du niveau actuel
     * @return int 
     */
    public int getNiveau(){
        return niveau;
    }

    /**
     * Fonction pour incrémenter le niveau actuel
     * de 1.
     */
    public void incrNiveau(){
        niveau++;
    }

    /**
     * Getter du nombre de points de vie actuel
     * @return int
     */
    public int getPv(){
        return pv;
    }

    /**
     * Fonction qui permet d'ajouter ou de retirer 
     * un nombre de pv. 
     * @param val
     */
    public void ajoutPv(int val){
        pv+=val;
    }

    /**
     * Getter pour récuperer le nombre de mot nécessaire au total
     * avant de changer de niveau.
     * @return int
     */
    public static int getNombredemotpourchangerdeniveau() {
        return nombreDeMotPourChangerDeNiveau;
    }

}
