import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tgm.asuender.WortEntry;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the {@link WortEntry} class.
 * @author Andreas Sünder
 * @version 01-07-2024
 */
public class WortEntryTest {

    @Test
    @DisplayName("Test if `WordEntry` saves the word and URL correctly")
    void testValidConstructor() throws MalformedURLException {
        URL url = new URL("http://example.com");
        WortEntry entry = new WortEntry("word", url);

        assertEquals("word", entry.getWord());
        assertEquals(url, entry.getUrl());
    }

    @Test
    @DisplayName("Test if `WordEntry` throws an exception when the word is too short")
    void testConstructorWithShortWord() throws MalformedURLException {
        URL url = new URL("http://example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            new WortEntry("w", url);
        });
    }

    @Test
    @DisplayName("Test if `WordEntry` sets the word correctly")
    void testSetWord() {
        WortEntry entry = new WortEntry();
        entry.setWord("test");
        assertEquals("test", entry.getWord());
    }

    @Test
    @DisplayName("Test if `WordEntry` sets the URL correctly")
    void testSetUrl() throws Exception {
        WortEntry entry = new WortEntry();
        URL url = new URL("http://example.com");
        entry.setUrl(url);
        assertEquals(url, entry.getUrl());
    }

    @Test
    @DisplayName("Test if `WordEntry` returns the string representation in the format `word;url` correctly")
    void testToString() throws Exception {
        URL url = new URL("http://example.com");
        WortEntry entry = new WortEntry("word", url);
        assertEquals("word;http://example.com", entry.toString());
    }
}
