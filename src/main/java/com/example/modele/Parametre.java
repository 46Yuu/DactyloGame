package com.example.modele;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Parametre {
    private double vitesse;
    private double frequenceBonus;
    private String texteATaper;
    private String mode;
    private int vies;
    private int niveau;
    private int limiteDeTemps;
    private String langue;
    private static final String textFr = "src/main/resources/txt/francais10k.txt";
    private static final String textEn = "src/main/resources/txt/anglais10k.txt";

    /*
     * Retourne le monteur des paramètres
     */
    public static Builder builder(){
        return new Builder();
        
    }

    public static class Builder{
        private double vitesse;
        private double frequenceBonus;
        private String texteATaper = "";
        private String mode;
        private int vies;
        private int niveau;
        private int limiteDeTemps;
        private String langue;

        public Builder(){}

        public Builder vitesse(double val){
            vitesse = val;
            return this;
        }

        public Builder frequenceBonus(double val){
            frequenceBonus = val;
            return this;
        }

        public Builder texteATaper(){
            try {
                String path = getPath(langue);
                BufferedReader read = new BufferedReader(new FileReader(path));
                String ligne = read.readLine();
                ArrayList<String> listeMots = new ArrayList<String>();
                while(ligne != null){
                    listeMots.add(ligne);
                    ligne = read.readLine();
                }
                Random rand = new Random();
                for(int i = 0; i < listeMots.size(); i++){
                    int numRandom = rand.nextInt(listeMots.size());
                    texteATaper += listeMots.get(numRandom)+" ";
                    listeMots.remove(numRandom);
                }
                read.close();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            } 
            catch (IOException e){
                e.printStackTrace();
            }
            return this;
        }

        public Builder mode(String val){
            mode = val;
            return this;
        }

        public Builder vies(int val){
            vies = val;
            return this;
        }

        public Builder niveau(int val){
            niveau = val;
            return this;
        }

        public Builder limiteDeTemps(int val){
            limiteDeTemps = val;
            return this;
        }

        public Builder langue(String val){
            langue = val;
            return this;
        }

        public Parametre build(){
            return new Parametre(this);
        }
    }

    private Parametre(Builder builder){
        vitesse = builder.vitesse;
        frequenceBonus = builder.frequenceBonus;
        texteATaper = builder.texteATaper;
        mode = builder.mode;
        vies = builder.vies;
        niveau = builder.niveau;
        limiteDeTemps = builder.limiteDeTemps;
        langue = builder.langue;
    }

    /**
     * Renvoie la liste des parametres de la partie
     * en String pour pouvoir l'afficher.
     * @return String
     */
    @Override
    public String toString() {
        return "Parametre [vitesse=" + vitesse + ", frequenceBonus=" + frequenceBonus + ", texteATaper=" + texteATaper
                + ", mode=" + mode + ", vies=" + vies + ", niveau=" + niveau + ", limiteDeTemps=" +limiteDeTemps + ", langue=" + langue + "]";
    }

    /**
     * Getter pour obtenir le path qui permettra de
     * récupérer la liste de mots à utiliser.
     * @param langue
     * @return String selon le choix du joueur.
     */
    private static String getPath(String langue){
        if(langue.compareTo("FR") == 0){
            return textFr;
        }
        if(langue.compareTo("EN") == 0){
            return textEn;
        }
        else {
            return textFr;
        }
    }

    /**
     * Getter du texte à taper.
     * @return String
     */
    public String getTexteATaper() {
        return texteATaper;
    }
    
    /**
     * Getter de la limite de temps.
     * @return int
     */
    public int getLimiteDeTemps() {
        return limiteDeTemps;
    }

    /**
     * Getter du mode de jeu.
     * @return String
     */
    public String getMode() {
        return mode;
    }

    /**
     * Getter du nombre de points de vie.
     * @return int
     */
    public int getVies() {
        return vies;
    }

    /**
     * Getter du niveau de départ.
     * @return int
     */
    public int getNiveau() {
        return niveau;
    }
}
