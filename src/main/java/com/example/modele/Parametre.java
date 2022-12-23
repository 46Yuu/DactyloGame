package com.example.modele;

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
        private String texteATaper;
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

        public Builder texteATaper(String val){
            texteATaper = val;
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
    
    
}
