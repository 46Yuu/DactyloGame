package com.example.modele;

import java.util.LinkedList;
import java.util.List;

import com.example.vue.*;

import javafx.application.Application;
import javafx.stage.Stage;



public class Modele {
    public interface Listener {
        void onChange(Modele modele);
    }

    private List<Listener> listeners = new LinkedList<Listener>();
    private String beginningText;


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
        beginningText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus et" +
        "hendrerit tellus, fringilla rhoncus mauris. Curabitur et odio et justo mattis placerat." +
        "Quisque venenatis mi erat, ac sollicitudin quam congue ut. Interdum et malesuada fames" +
        "ac ante ipsum primis in faucibus. Nam non mauris interdum, vestibulum mi vel, condimentum" +
        "ipsum. Suspendisse vitae finibus justo. Sed imperdiet, nisl ac dictum dapibus, massa orci" +
        "molestie risus, sit amet lacinia velit neque sit amet lorem. Aliquam id velit ac erat" +
        "ultricies blandit et eget ante. Nullam mattis ultricies venenatis. Pellentesque blandit" +
        "gravida sem, quis accumsan risus venenatis eu. \nNullam condimentum interdum erat, id mattis " +
        "nunc porttitor et. Sed efficitur accumsan semper. Suspendisse rhoncus leo vel euismod varius. " +
        "Curabitur aliquam feugiat massa, ut finibus tortor vestibulum vel. Donec vitae lorem vel massa " +
        "feugiat sodales. Sed at felis id turpis efficitur feugiat. Duis erat ante, eleifend nec fermentum " +
        "in, feugiat pharetra augue. Pellentesque habitant morbi tristique senectus et netus et malesuada " +
        "fames ac turpis egestas. Nullam sapien justo, venenatis id facilisis eget, dapibus eu justo. Donec " +
        "elit nibh, egestas eu ullamcorper sit amet, cursus ac ipsum. Vestibulum pretium tellus id elit tincidunt," + 
        "at tincidunt purus suscipit.\n" + 
        
        "Praesent non sodales ex. Donec posuere vestibulum leo, vel scelerisque nibh commodo nec. Sed ultrices " + 
        "sagittis aliquam. Proin quam quam, tincidunt sit amet pulvinar id, suscipit ut lacus. Nunc eget sagittis " + 
        "arcu. Fusce vulputate scelerisque lacus non tempus. Integer cursus vulputate justo, vitae maximus tellus " + 
        "fermentum consectetur. Suspendisse vestibulum dolor et metus tincidunt fermentum. ";

        notifyObservers();
    }

    /**
     * Permet d'envoyer l'info de la modification du texte à tous les listeners du modele
     */
    private void notifyObservers() {
        listeners.stream().forEach(l -> l.onChange(this));
    }

}

