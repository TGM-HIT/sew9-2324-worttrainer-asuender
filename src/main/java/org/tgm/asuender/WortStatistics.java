package org.tgm.asuender;

/**
 * Saves statistics of a trainer session and provides methods to add attempts and to show the result.
 * @author Andreas Sünder
 * @version 01-06-2024
 */
public class WortStatistics {
    private int numAttemptsTotal;
    private int numAttemptsCorrect;

    public WortStatistics() { }


    /**
     * Initializes empty statistics with the given total number of attempts.
     * @param numAttemptsTotal the total number of attempts, must be greater than 0
     */
    public WortStatistics(int numAttemptsTotal) {
        if (numAttemptsTotal < 1) {
            throw new IllegalArgumentException("Die Anzahl der Abfragen muss größer als 0 sein!");
        }

        this.numAttemptsTotal = numAttemptsTotal;
    }


    /**
     * Initializes statistics with the given total number of attempts and the number of correct attempts.
     * May be used to load the statistics from a previous session.
     * @param numAttemptsTotal the total number of attempts, must be greater than 0
     * @param numAttemptsCorrect the number of correct attempts, must be greater than or equal to 0
     */
    public WortStatistics(int numAttemptsTotal, int numAttemptsCorrect) {
        if (numAttemptsTotal < 1) {
            throw new IllegalArgumentException("Die Anzahl der Abfragen muss größer als 1 sein!");
        }
        if (numAttemptsCorrect < 0) {
            throw new IllegalArgumentException("Die Anzahl der richtigen Abfragen darf nicht negativ sein!");
        }

        this.numAttemptsTotal = numAttemptsTotal;
        this.numAttemptsCorrect = numAttemptsCorrect;
    }

    /**
     * Returns the total number of attempts.
     * @return the total number of attempts
     */
    public int getNumAttemptsAll() {
        return numAttemptsTotal;
    }

    /**
     * Updates the total number of attempts. Must be greater than 0.
     * @param numAttemptsTotal the total number of attempts, must be greater than 0
     */
    public void setNumAttemptsAll(int numAttemptsTotal) {
        if (numAttemptsTotal < 1) {
            throw new IllegalArgumentException("Die Anzahl der Abfragen muss größer als 1 sein!");
        }

        this.numAttemptsTotal = numAttemptsTotal;
    }

    /**
     * Returns the number of correct attempts.
     * @return the number of correct attempts
     */
    public int getNumAttemptsCorrect() {
        return numAttemptsCorrect;
    }

    /**
     * Sets the number of correct attempts. Must be greater than or equal to 0.
     * Shall only be used for deserialization.
     * @param numAttemptsCorrect the number of correct attempts, must be greater than or equal to 0
     */
    public void setNumAttemptsCorrect(int numAttemptsCorrect) {
        if (numAttemptsCorrect < 0) {
            throw new IllegalArgumentException("Die Anzahl der richtigen Abfragen darf nicht negativ sein!");
        }

        this.numAttemptsCorrect = numAttemptsCorrect;
    }

    /**
     * Adds an attempt to the statistics.
     * @param correct true if the attempt was correct, false otherwise
     */
    public void addAttempt(boolean correct) {
        ++this.numAttemptsTotal;
        if (correct) ++this.numAttemptsCorrect;
    }

    /**
     * Calculates the ratio of correct attempts to total attempts and resets the statistics.
     * @return the ratio of correct attempts to total attempts
     */
    public double showResult() {
        double result = numAttemptsCorrect *100.0 / numAttemptsTotal;

        this.numAttemptsTotal = 0;
        this.numAttemptsCorrect = 0;

        return result;
    }

    /**
     * Returns the current statistics as a String in the format "[correct] von [total] richtig.".
     * @return String representation of the current statistics
     */
    @Override
    public String toString() {
        return String.format("%d von %d richtig.", this.numAttemptsCorrect, this.numAttemptsTotal);
    }
}
