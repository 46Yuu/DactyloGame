package com.example;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.modele.SoloNormal;
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
        System.out.println("It's working");
    }

    /**
     * Test du bon fonctionnement du patron monteur des paramètres
     */
    @Test
    public void testParametres(){
        Parametre soloNormal = Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
        System.out.println(soloNormal);
    }

    public Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
    }

    public <U> void afficherListe(LinkedList<U> liste){

        liste.stream().forEach(x -> System.out.println(x));
    }

    @Test
    public void testInitialisationJeu(){
        SoloNormal jeu = new SoloNormal(creationParametreSoloNormal());
        jeu.initializerFiles();
        System.out.println("Voici la liste secondaire");
        afficherListe(jeu.getFileSnd());
        System.out.println("Voici la liste principale");
        afficherListe(jeu.getFile());
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
        SoloNormal jeu = new SoloNormal(creationParametreSoloNormal());
        jeu.initializerFiles();
        //System.out.println("-----Validation du premier mot");
        jeu.validerMot();
        LinkedList<String> fileApresValidation = new LinkedList<String>(List.of("ipsum","dolor","sit","amet",
        "consectetur","adipiscing","erzzer","rzerze","rezrez","rzerzer","rzrer","erzerz","erzre","erezr","sdfsfd"));
        LinkedList<String> fileSndApresValidation = new LinkedList<String>(List.of("fdsfs","fsdfesd","fsdfds"));
        assertTrue(compareList(jeu.getFile(),fileApresValidation),"file principal incorrect apres une validation");
        assertTrue(compareList(jeu.getFileSnd(),fileSndApresValidation),"file principal incorrect apres une validation");

        jeu.validerMot();
        jeu.validerMot();
        jeu.validerMot();
        jeu.validerMot();
        jeu.validerMot();
        jeu.validerMot();
        jeu.validerMot();
        jeu.validerMot();
        LinkedList<String> fileApres8Validation = new LinkedList<String>(List.of("rezrez","rzerzer","rzrer","erzerz","erzre","erezr","sdfsfd","fdsfs","fsdfesd","fsdfds"));
        LinkedList<String> fileSndApres8Validation = new LinkedList<String>(List.of());
        assertTrue(compareList(jeu.getFile(),fileApres8Validation),"file principal incorrect apres une validation");
        assertTrue(compareList(jeu.getFileSnd(),fileSndApres8Validation),"file principal incorrect apres une validation");

        // System.out.println("***Voici la liste secondaire de taille "+jeu.getFileSnd().size());
        // afficherListe(jeu.getFileSnd());
        // System.out.println("***Voici la liste principalede taille "+jeu.getFile().size());
        // afficherListe(jeu.getFile());

        // System.out.println("-----Validation de 8 mots");
        // jeu.validerMot();
        // jeu.validerMot();
        // jeu.validerMot();
        // jeu.validerMot();
        // jeu.validerMot();
        // jeu.validerMot();
        // jeu.validerMot();
        // jeu.validerMot();
        // System.out.println("***Voici la liste secondaire de taille "+jeu.getFileSnd().size());
        // afficherListe(jeu.getFileSnd());
        // System.out.println("***Voici la liste principalede taille "+jeu.getFile().size());
        // afficherListe(jeu.getFile());
    }
}
