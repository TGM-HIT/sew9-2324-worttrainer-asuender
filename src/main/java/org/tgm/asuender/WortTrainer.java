package org.tgm.asuender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import org.tgm.asuender.persistence.GenericStorage;

import java.util.*;

/**
 * Saves a list of word entries and provides methods to check the user's input and to show statistics.
 * @author Andreas Sünder
 * @version 01-06-2024
 */
public class WortTrainer {

    @JacksonXmlElementWrapper
    private List<WortEntry> entries;
    private int selected = -1;

    private WortStatistics statistics = new WortStatistics();

    /**
     * Initializes an empty word trainer.
     */
    public WortTrainer() {
        this.entries = new ArrayList<>();
    }

    /**
     * Initializes a word trainer with the given list of word entries.
     * @param items the list of word entries to be saved, must not be null or empty
     */
    public WortTrainer(List<WortEntry> items) {
        if (items.isEmpty()) throw new IllegalArgumentException("Die Liste ist leer!");
        this.entries = items;
    }

    /**
     * Returns the list of word entries.
     * @return the list of word entries
     */
    public List<WortEntry> getEntries() {
        return entries;
    }

    /**
     * Sets a new list of word entries. The other attributes are reset.
     * @param items the new list of word entries, must not be null
     */
    public void getEntries(List<WortEntry> items) {
        if (items == null) {
            throw new NullPointerException("Die Liste darf kein Nullobjekt sein!");
        }

        this.entries = items;
        this.selected = -1;
        this.statistics = new WortStatistics();
    }

    /**
     * Returns a random item.
     * @return a random item.
     */
    @JsonIgnore
    public WortEntry getRandomItem() {
        if (this.entries.isEmpty()) throw new IllegalArgumentException("Die Liste ist leer!");

        this.selected = new Random().nextInt(this.entries.size());
        return this.entries.get(this.selected);
    }

    /**
     * Returns the index of the selected item.
     * @return the index of the selected item
     */
    public int getSelected() {
        return selected;
    }

    /**
     * Returns the selected item.
     * @return the selected item
     */
    private WortEntry getSelectedItem() {
        return this.entries.get(this.selected);
    }

    /**
     * Sets the index of the selected item. Shall only be used for deserialization.
     * @param selected the index of the selected item
     */
    public void setSelected(int selected) {
        this.selected = selected;
    }

    /**
     * Returns the current statistics.
     * @return current statistics
     */
    public WortStatistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the statistics. The statistics must not be null. Shall only be used for deserialization.
     * @param statistik the statistics to be set
     */
    public void setStatistics(WortStatistics statistik) {
        if (statistik == null) {
            throw new NullPointerException("Die Statistik darf kein Nullobjekt sein!");
        }
        this.statistics = statistik;
    }


    /**
     * Checks the given word against the selected item.
     * @param word the word to be checked, must not be null and must be at least 2 characters long.
     * @return true if the word is equal to the selected item, false otherwise
     */
    public boolean check(String word, boolean ignoreCase) {
        if (word == null) {
            throw new NullPointerException("Das Wort darf kein Nullobjekt sein!");
        }

        if (word.trim().length() < 2) {
            throw new IllegalArgumentException("Das Wort muss mind. 2 Buchstaben lang sein!");
        }

        String correctWord = this.getSelectedItem().getWord();
        boolean equal = ignoreCase ? correctWord.equalsIgnoreCase(word) : correctWord.equals(word);
        this.statistics.addAttempt(equal);

        return equal;
    }

    /**
     * Returns the current statistics as a String.
     * @return current statistics as a String
     */
    public String showStatistics() {
        return this.statistics.toString();
    }

    /**
     * Resets the word trainer using an empty list of word entries.
     */
    public void reset() {
        this.entries = new ArrayList<>();
        this.selected = -1;
        this.statistics = new WortStatistics();
    }

    /**
     * Imports a word trainer from the given storage.
     * @param storage the storage to be used
     * @return the imported word trainer, or null if an error occurred
     */
    public static WortTrainer importFromStorage(GenericStorage storage) {
        return storage.loadData();
    }
}
