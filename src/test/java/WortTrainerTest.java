import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tgm.asuender.WortEntry;
import org.tgm.asuender.WortTrainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WortTrainerTest {
    List<WortEntry> entries;

    @BeforeEach
    void setup() throws MalformedURLException {
        entries = new LinkedList<>();
        entries.add(new WortEntry("Katze", new URL("https://upload.wikimedia.org/wikipedia/commons/3/3d/Katze_weiss.png")));
        entries.add(new WortEntry("Hund", new URL("https://www.pinclipart.com/picdir/middle/20-206356_wenn-hund-clipart.png")));
        entries.add(new WortEntry("Elefant", new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Serengeti_Elefantenherde1.jpg/1280px-Serengeti_Elefantenherde1.jpg")));
    }

    @Test
    @DisplayName("Test if `WortTrainer` saves the entries correctly and rejects null or empty lists")
    void testEntries() {
        WortTrainer trainer = new WortTrainer(entries);
        assertIterableEquals(trainer.getEntries(), entries);

        assertThrows(NullPointerException.class, () -> {
            new WortTrainer(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new WortTrainer(new LinkedList<>());
        });
    }

    @Test
    @DisplayName("Test if the `check` method works correctly")
    void testCheckMethod() {
        WortTrainer trainer = new WortTrainer(entries);
        WortEntry currentEntry = trainer.getRandomEntry();

        assertTrue(trainer.check(currentEntry.getWord(), false));
        assertFalse(trainer.check(currentEntry.getWord().toLowerCase(), false));
        assertTrue(trainer.check(currentEntry.getWord(), true));

        assertFalse(trainer.check("42", false));
    }

    @Test
    @DisplayName("Tests if `WortStatistics` calculates the result correctly")
    void testStatistics() {
        WortTrainer trainer = new WortTrainer(entries);

        WortEntry currentEntry;
        for (int i = 0; i < 5; ++i) {
            currentEntry = trainer.getRandomEntry();
            trainer.check(currentEntry.getWord(), true);
        }

        assertEquals(trainer.getStatistics().showResult(), 100.0);
    }
}
