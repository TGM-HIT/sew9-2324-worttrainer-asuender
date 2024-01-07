import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tgm.asuender.WortEntry;
import org.tgm.asuender.WortTrainer;
import org.tgm.asuender.persistence.GenericStorage;
import org.tgm.asuender.persistence.XMLStorage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the {@link XMLStorage} class.
 * It uses the {@link WortTrainer} class to test if the storage classes work correctly.
 * @author Andreas Sünder
 * @version 01-07-2024
 */
public class PersistenceTest {
    static int SELECTED_ITEM = 2;

    List<WortEntry> entries;
    WortTrainer trainer;

    @BeforeEach
    void setup() throws MalformedURLException {
        entries = new LinkedList<>();
        entries.add(new WortEntry("Katze", new URL("https://upload.wikimedia.org/wikipedia/commons/3/3d/Katze_weiss.png")));
        entries.add(new WortEntry("Hund", new URL("https://www.pinclipart.com/picdir/middle/20-206356_wenn-hund-clipart.png")));
        entries.add(new WortEntry("Elefant", new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Serengeti_Elefantenherde1.jpg/1280px-Serengeti_Elefantenherde1.jpg")));

        trainer = new WortTrainer(entries);
        WortEntry currentEntry;
        for (int i = 0; i < 5; ++i) {
            currentEntry = trainer.getRandomEntry();
            trainer.check(currentEntry.getWord(), true);
        }
        trainer.setSelected(SELECTED_ITEM);
    }

    @Test
    @DisplayName("Saves an `WortTrainer` instance and checks if the loaded instance is equal to the saved one")
    void checkXMLPersistence() {
        GenericStorage storage = new XMLStorage("test.xml");
        storage.saveData(trainer);

        WortTrainer loadedTrainer = storage.loadData();
        assertIterableEquals(loadedTrainer.getEntries(), entries);
        assertEquals(loadedTrainer.getSelected(), SELECTED_ITEM);
        assertEquals(loadedTrainer.getStatistics().showResult(), 100.0);
    }
}
