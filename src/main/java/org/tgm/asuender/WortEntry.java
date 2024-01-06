package org.tgm.asuender;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.URL;

/**
 * Saves an entry consisting of a word and a corresponding URL.
 * @author Andreas Sünder
 * @version 01-06-2024
 */
public class WortEntry {
    private String word;
    URL url;

    /**
     * Initializes an empty word entry.
     * @param word the word to be saved, must be at least 2 characters long
     * @param url the URL to be saved, must not be null
     */
    public WortEntry(String word, URL url) {
        if (word == null || word.trim().length() < 2) {
            throw new IllegalArgumentException("Das Wort muss mind. 2 Buchstaben lang sein!");
        }
        if (url == null) throw new NullPointerException("Die URL darf kein Nullobjekt sein!");

        this.word = word;
        this.url = url;
    }

    /**
     * Returns the saved word.
     * @return the saved word
     */
    public String getWort() {
        return word;
    }

    /**
     * Sets the word to be saved. The word must be at least 2 characters long.
     * @param word the word to be saved
     */
    public void setWord(String word) {
        if (word == null || word.trim().length() < 2) {
            throw new IllegalArgumentException("Das Wort muss mind. 2 Buchstaben lang sein!");
        }

        this.word = word;
    }

    /**
     * Returns the saved URL.
     * @return the saved URL
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Returns the saved URL as a String.
     * This method is ignored by Jackson for serialization.
     * @return the saved URL as a String
     */
    @JsonIgnore
    public String getUrlString() {
        return url.toString();
    }

    /**
     * Sets the URL to be saved. The URL must not be null.
     * @param url the URL to be saved
     */
    public void setUrl(URL url) {
        if (url == null) throw new NullPointerException("Die URL darf kein Nullobjekt sein!");

        this.url = url;
    }

    /**
     * Returns the String representation of the current object in the format
     * "[word];[url]".
     * @return String representation
     */
    @Override
    public String toString() {
        return String.format("%s;%s", this.word, this.url);
    }
}
