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

        public Builder(){}

        public Builder vitesse(double val){
            vitesse = val;
            return this;
        }

        public Builder frequenceBonus(double val){
            frequenceBonus = val;
            return this;
        }
        /* 
        public Builder texteATaper(String val){
            texteATaper = val;
            return this;
        }*/

        public Builder texteATaper(){
            try {
                BufferedReader read = new BufferedReader(new FileReader("./src/main/resources/txt/francais1k.txt"));
                String ligne = read.readLine();
                ArrayList<String> listeMots = new ArrayList<String>();
                while(ligne != null){
                    listeMots.add(ligne);
                    ligne = read.readLine();
                }
                Random rand = new Random();
                for(int i = 0; i < 500; i++){
                    texteATaper += listeMots.get(rand.nextInt(listeMots.size()))+" ";
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
    }

    @Override
    public String toString() {
        return "Parametre [vitesse=" + vitesse + ", frequenceBonus=" + frequenceBonus + ", texteATaper=" + texteATaper
                + ", mode=" + mode + ", vies=" + vies + ", niveau=" + niveau + "]";
    }

    public String getTexteATaper() {
        return texteATaper;
    }
    
    public String getMode() {
        return mode;
    }
    
}
