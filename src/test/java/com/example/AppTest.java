package com.example;


import org.junit.jupiter.api.Test;

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
        .texteATaper("Lorem ipsum dolor sit amet, consectetur adipiscing")
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
        System.out.println(soloNormal);
    }
}
