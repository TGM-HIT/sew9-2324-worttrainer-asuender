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
     * @param entries the list of word entries to be saved, must not be null or empty
     */
    public WortTrainer(List<WortEntry> entries) {
        if (entries == null) throw new NullPointerException("Die Liste darf kein Nullobjekt sein!");
        if (entries.isEmpty()) throw new IllegalArgumentException("Die Liste ist leer!");

        this.entries = entries;
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
     * @param entries the new list of word entries, must not be null
     */
    public void setEntries(List<WortEntry> entries) {
        if (entries == null) throw new NullPointerException("Die Liste darf kein Nullobjekt sein!");
        if (entries.isEmpty()) throw new IllegalArgumentException("Die Liste ist leer!");

        this.entries = entries;
        this.selected = -1;
        this.statistics = new WortStatistics();
    }

    /**
     * Returns a random entry.
     * @return a random entry.
     */
    @JsonIgnore
    public WortEntry getRandomEntry() {
        if (this.entries.isEmpty()) throw new IllegalArgumentException("Die Liste ist leer!");

        this.selected = new Random().nextInt(this.entries.size());
        return this.entries.get(this.selected);
    }

    /**
     * Returns the index of the selected entry.
     * @return the index of the selected entry
     */
    public int getSelected() {
        return selected;
    }

    /**
     * Returns the selected entry.
     * @return the selected entry
     */
    private WortEntry getSelectedEntry() {
        return this.entries.get(this.selected);
    }

    /**
     * Sets the index of the selected entry. Shall only be used for deserialization.
     * @param selected the index of the selected entry
     */
    public void setSelected(int selected) {
        if (selected >= this.entries.size()) {
            throw new IllegalArgumentException("Der Index ist zu groß!");
        }

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
     * Checks the given word against the selected entry.
     * @param word the word to be checked, must not be null and must be at least 2 characters long.
     * @return true if the word is equal to the selected entry, false otherwise
     */
    public boolean check(String word, boolean ignoreCase) {
        if (word == null) {
            throw new NullPointerException("Das Wort darf kein Nullobjekt sein!");
        }

        if (word.trim().length() < 2) {
            throw new IllegalArgumentException("Das Wort muss mind. 2 Buchstaben lang sein!");
        }

        String correctWord = this.getSelectedEntry().getWord();
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
