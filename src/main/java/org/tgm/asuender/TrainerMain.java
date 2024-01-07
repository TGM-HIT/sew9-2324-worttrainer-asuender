package org.tgm.asuender;

import org.tgm.asuender.persistence.XMLStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to show how {@link WortTrainer} and {@link WortEntry} can be sued
 * with a simple GUI to show the user a random image and ask for the corresponding word.
 * @author Andreas Sünder
 * @version 01-07-2024
 */
public class TrainerMain {
    public static void main(String[] args) throws MalformedURLException {
        XMLStorage storage = new XMLStorage();
        WortTrainer trainer = storage.loadData();

        if (trainer == null) {
            List<WortEntry> items = new LinkedList<>();
            items.add(new WortEntry("Katze", new URL("https://upload.wikimedia.org/wikipedia/commons/3/3d/Katze_weiss.png")));
            items.add(new WortEntry("Hund", new URL("https://www.pinclipart.com/picdir/middle/20-206356_wenn-hund-clipart.png")));
            items.add(new WortEntry("Elefant", new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Serengeti_Elefantenherde1.jpg/1280px-Serengeti_Elefantenherde1.jpg")));
            trainer = new WortTrainer(items);
        }

        boolean finished = false, inputCorrect = false;
        String input;
        Image image;
        int numTries = 0;
        do {
            WortEntry entry = trainer.getRandomItem();

            try {
                image = ImageIO.read(entry.getUrl());
                image = image.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);
            } catch(IOException ioe) {
                continue;
            }

            String message = String.format("Was siehst du auf dem Bild?\n%s", trainer.showStatistics());
            if (numTries > 0) {
                message += String.format("\nDein Letzter Versuch war %s", inputCorrect ? "richtig" : "falsch");
            }

            input = (String) JOptionPane.showInputDialog(null, message, "Wort-Trainer", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image), null, "");

            if (input == null || input.isEmpty()) { finished = true; }
            else {
                inputCorrect = trainer.check(input, false);
            }

            ++numTries;
        } while(!finished);

        storage.saveData(trainer);
    }
}
