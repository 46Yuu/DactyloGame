package com.example.modele;

public class KeyValuePair {
    private final String key;
    private final String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }
 
    public String getKey(){    
        return key;
    }
 
    public String toString(){    
        return value;
    }
}