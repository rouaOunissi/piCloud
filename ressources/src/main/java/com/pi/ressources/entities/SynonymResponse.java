package com.pi.ressources.entities;

import java.io.Serializable;

public class SynonymResponse implements Serializable {

    private String[] synonyms;

    public String[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }

}
