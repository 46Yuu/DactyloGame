package com.example;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.modele.PartieSoloNormal;
import com.example.modele.Parametre;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Just a Junit test :-)
     */
    @Test
    public void testTjunit()
    {
        //System.out.println("It's working");
        assert(true);
    }

    /**
     * Test du bon fonctionnement du patron monteur des paramètres
     */
    @Test
    public void testParametres(){
        Parametre soloNormal = Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .langue("FR")
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(8).build();
        //System.out.println(soloNormal);
        assert(soloNormal.getMode().compareTo("Solo Normal") == 0);
        assert(soloNormal.getNiveau() == 8);
    }

    public Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .langue("FR")
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
    }

    public <U> void afficherListe(LinkedList<U> liste){

        liste.stream().forEach(x -> System.out.println(x));
    }

    public <U> boolean compareList(LinkedList<U> l1, LinkedList<U> l2) {
        if(l1.size() != l2.size()) return false;
        for(int i = 0; i < l1.size(); i++) {
            if(!l1.get(i).equals(l2.get(i))) {
                return false;
            }
        }
        return true;
    }


    /*
     * Test les files après une validation puis après 8 autres validation
     */
    @Test
    public void testValidationMot(){
        PartieSoloNormal jeu = new PartieSoloNormal(creationParametreSoloNormal());
        jeu.initializerFiles();
        //System.out.println("-----Validation du premier mot");
        jeu.validerMot();
        LinkedList<String> fileApresValidation = jeu.getFile();
        String aEnlever = fileApresValidation.removeFirst();
        LinkedList<String> fileSndApresValidation = jeu.getFileSnd();
        fileApresValidation.addLast(aEnlever);
        assertTrue(compareList(jeu.getFile(),fileApresValidation),"file principal incorrect apres une validation");
        assertTrue(compareList(jeu.getFileSnd(),fileSndApresValidation),"file principal incorrect apres une validation");

    }
}
