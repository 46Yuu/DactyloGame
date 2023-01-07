package com.example.modele;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PartieSoloNormal {
    protected Parametre parametre;
    protected double charUtiles = 0;
    protected double charUtilesTemporaire = 0;
    protected double nbAppuiTouches = 0;
    protected Timer timer;
    protected int resPourCalculMoyenne = 0;
    protected int tempsEntreChaqueCharUtile = 0;
    protected boolean timerActive = false;
    DecimalFormat df = new DecimalFormat("0.00");
    /*La taille maximale d'élements pouvant rentrer dans la file de mots */
    private static final int tailleMaxFileDeMot = 15;
    private int tempsJeu;
    /* La file des 15 prochain mots à taper. Utiliser add() removeFirst() et isEmpty  */
    protected LinkedList<String> file;
    protected LinkedList<String> fileSnd;

    private List<Listener> listeners = new LinkedList<Listener>();
    private String beginningText;

    public PartieSoloNormal(Parametre p) {
        parametre = p;
        tempsJeu = parametre.getLimiteDeTemps()-1;
        this.initializerFiles();
    }
    
    public int getTempsJeu(){
        return tempsJeu;
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
        if(!fileSnd.isEmpty()){
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
        enleverMotEnTeteDeFile();
        s = ajoutMotALaFile();
        return s;
    }

    public void enleverMotEnTeteDeFile(){
        if(!file.isEmpty()) file.removeFirst();
    }
    
    /*
     * Initialize la file des éléments à taper
     */
    public void initializerFiles(){
        System.out.println("-----------------------------------------------------------------------");
        file = new LinkedList<String>();
        fileSnd = new LinkedList<String>();
        String[] texteSplit = parametre.getTexteATaper().split(" ");
        for (int i=0; i < texteSplit.length; i++){
            fileSnd.add(texteSplit[i]);
        }
        //Ensuite on envoie les 15 premiers mots dans la file principale
        for (int i=0; i < 15; i++){
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


    public void startTimerNormal() {
        startTimerStat();
        startTimerCompteur();
    }

    public void getStats(){
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

    public double getStatsVitesse(){
        return (charUtiles/(0.5))/5;
    }

    public double getStatsPrecision(){
        return (charUtiles/nbAppuiTouches)*100;
    }

    public double getStatsRegularite(){
        return calculEcartType();
    }

    private void startTimerStat(){
        timerActive = true;
        timer = new Timer();
        TimerTask task = new TimerTask(){ 
            @Override
            public void run(){
                getStats();
            }
        };
        timer.schedule(task,parametre.getLimiteDeTemps()*1000); 
    }

    public void startTimerCompteur(){
        TimerTask taskCounterEcartType = new TimerTask() {
            @Override
            public void run() {
                tempsEntreChaqueCharUtile++;
            }
        };
        timer.scheduleAtFixedRate(taskCounterEcartType,0*1000, 1*1000);
    }

    public void decrCountdown(){
        tempsJeu--;
    }

    public int getCountdown(){
        return tempsJeu;
    }

    public Timer getTimer(){
        return this.timer;
    }
    
    public boolean getTimerActive(){
        return timerActive;
    }

    protected double calculEcartType(){
        if(charUtiles == 0){
            return 0;
        }
        else{
            return resPourCalculMoyenne/charUtiles;
        }
    }

    public double getCharUtiles() {
        return charUtiles;
    }


    public interface Listener {
        void onChange(PartieSoloNormal partieSoloNormal);
    }

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
        setBeginningText(getStringOfFile());
        notifyObservers();
    }

    /**
     * Permet d'envoyer l'info de la modification du texte à tous les listeners du modele
     */
    private void notifyObservers() {
        listeners.stream().forEach(l -> l.onChange(this));
    }



}
