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
    
    /**
     * Getter pour le temps de jeu restant.
     * @return int
     */
    public int getTempsJeu(){
        return tempsJeu;
    }

    /**
     * Vérifie si la file est pleine ou non.
     * @return boolean true si la file n'est pas pleine.
     * @return boolean false si la file est pleine.
     */
    public boolean fileNonPleine(){
        return file.size() < tailleMaxFileDeMot;
    }

    /**
     * Ajoute le premier mot de la seconde file qui correspond
     * aux nouveaux mots à ajouter à la file principale.
     * @return String du mot ajouté
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

    /**
     * Fonction qui permet de valider le premier mot de la file en 
     * l'enlevant et en ajoutant à la fin de la file un nouveau mot.
     * @return Le String du nouveau mot ajouté à la file.
     */
    public String validerMot(){
        String s = "";
        enleverMotEnTeteDeFile();
        s = ajoutMotALaFile();
        return s;
    }

    /**
     * Fonction qui enleve le premier élément de la file
     * principale.
     */
    public void enleverMotEnTeteDeFile(){
        if(!file.isEmpty()) file.removeFirst();
    }
    
    /**
     * Fonction permettant d'initialiser les files de mots 
     * et de mettre les premiers mots dans la file.
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

    /**
     * Getter pour la file principale de mots.
     * @return LikedList<String> 
     */
    public LinkedList<String> getFile() {
        return file;
    }

    /**
     * Getter pour les parametres de jeu.
     * @return Parametre
     */
    public Parametre getParametre(){
        return parametre;
    }

    /**
     * Fonction qui permet d'obtenir un String correspondant à
     * tous les mots qui sont présent dans la file de mots
     * principale.
     * @return String
     */
    public String getStringOfFile(){
        String res = " ";
        for(int i = 0;i<file.size(); i++){
            res += file.get(i)+" ";
        }
        return res;
    }

    /**
     * Getter de la file de mots contenant tout 
     * les mots qui seront ajouté a la file principale.
     * @return LinkedList<String>
     */
    public LinkedList<String> getFileSnd() {
        return fileSnd;
    }
    
    /**
     * Fonction qui augmente la variable du nombre de 
     * caractères utile par 1.  
     */
    public void incrCharUtilesTemporaire() {
        charUtilesTemporaire++;
    }

    /**
     * Fonction qui réduit la variable du nombre de 
     * caractères utile par 1.  
     */
    public void decrCharUtilesTemporaire() {
        charUtilesTemporaire--;
    }

    /**
     * Fonction qui augmente la variable du nombre 
     * d'appui de touches par 1.
     */
    public void incrNbAppuiTouches() {
        nbAppuiTouches++;
    }

    /**
     * Fonction qui ajoute le nombres de caractère
     * utile écrit dans le mot qui vient d'être validé.
     */
    public void ajoutCharUtilesTemporaire(){
        charUtiles += charUtilesTemporaire;
    }

    /**
     * Fonction qui remet à 0 la variable charUtilesTemporaire.
     */
    public void resetCharUtilesTemporaire(){
        charUtilesTemporaire = 0;
    }

    /**
     * Ajoute le temps écoulé entre deux charUtiles à la variable 
     * resPourCalculMoyenne et remet le temps écoulé à 0.
     */
    public void ajoutTempsCharUtile(){
        resPourCalculMoyenne+=tempsEntreChaqueCharUtile;
        tempsEntreChaqueCharUtile = 0;
    }

    /**
     * Lance les fonctions qui permettent d'intialiser et 
     * lancer les timers de la partie.
     */
    public void startTimerNormal() {
        startTimerStat();
        startTimerCompteur();
    }

    /**
     * Fonction qui permet de terminer le timer.
     */
    public void endTimer(){
        timer.cancel();
    }

    /**
     * Calcule la vitesse du joueur en mots par 
     * minutes et la renvoie.
     * @return double
     */
    public double getStatsVitesse(){
        return (charUtiles/(0.5))/5;
    }

    /**
     * Calcule la précision du joueur et la renvoie.
     * @return double
     */
    public double getStatsPrecision(){
        return (charUtiles/nbAppuiTouches)*100;
    }

    /**
     * Calcule la régularité du joueur , c'est à dire
     * le temps moyen entre deux touches utiles. 
     * @return double
     */
    public double getStatsRegularite(){
        return calculEcartType();
    }

    /**
     * Lance le timer des stats qui permettra de le terminer
     * à la fin du temps limité.
     */
    private void startTimerStat(){
        timerActive = true;
        timer = new Timer();
        TimerTask task = new TimerTask(){ 
            @Override
            public void run(){
                endTimer();
            }
        };
        timer.schedule(task,parametre.getLimiteDeTemps()*1000); 
    }

    /**
     * Lance le timer qui permet de calculer le temps écoulé
     * entre deux touches utiles.
     */
    public void startTimerCompteur(){
        TimerTask taskCounterEcartType = new TimerTask() {
            @Override
            public void run() {
                tempsEntreChaqueCharUtile++;
            }
        };
        timer.scheduleAtFixedRate(taskCounterEcartType,0*1000, 1*1000);
    }

    /**
     * Réduit de 1 l'affichage du timer de jeu. 
     */
    public void decrCountdown(){
        tempsJeu--;
    }

    /**
     * Getter du temps de jeu restant.
     * @return int 
     */
    public int getCountdown(){
        return tempsJeu;
    }

    /**
     * Getter du timer du temps de jeu.
     * @return Timer 
     */
    public Timer getTimer(){
        return this.timer;
    }
    
    /**
     * Getter pour savoir si le timer du temps de jeu
     * est actif ou pas.
     * @return boolean
     */
    public boolean getTimerActive(){
        return timerActive;
    }

    /**
     * Fonction qui permet de faire le calcul de 
     * l'écart type entre chaque charactère utile 
     * et le temps entre eux.
     * @return double
     */
    protected double calculEcartType(){
        if(charUtiles == 0){
            return 0;
        }
        else{
            return resPourCalculMoyenne/charUtiles;
        }
    }

    /**
     * Getter pour le nombre total de charactères
     * utiles.
     * @return double
     */
    public double getCharUtiles() {
        return charUtiles;
    }

    /**
     * L'interface fonctionelle des listeners sur notre objet PartieSoloNormal
     */
    public interface Listener {
        /**
         * Methode à executer à chaque modification de l'objet c'est à dire
         * chaque appel à notifyobservers
         * @param partieSoloNormal
         */
        void onChange(PartieSoloNormal partieSoloNormal);
    }

    /**
     * Ajoute un listener au mode Solo Normal.
     * @param listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Getter pour le texte de départ.
     * @return String
     */
    public String getBeginningText() {
        return beginningText;
    }

    /**
     * Setter pour le texte de départ.
     * @param beginningText
     */
    public void setBeginningText(String beginningText) {
        this.beginningText = beginningText;
        notifyObservers();
    }

    /**
     * Modifie beginning text en y mettant les mots qu'il ya dans file
     * et il previens les listeners
     */
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
