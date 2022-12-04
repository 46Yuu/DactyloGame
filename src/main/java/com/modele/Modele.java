package com.modele;

import com.vue.*;
/*import richtext.StyleClassedTextArea;*/

import javafx.application.Application;
import javafx.stage.Stage;



public class Modele {
    private Vue vue;
    Modele(Vue vue){
        this.vue = vue;
    }
    public static void main( String[] args )
    {
        Modele mod = new Modele(new Vue());
        mod.vue.launching(args);
    }

}

