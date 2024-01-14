package org.tgm.asuender.persistence;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.tgm.asuender.WortTrainer;

import java.io.File;
import java.io.IOException;

/**
 * This class is used to save and load trainer data from an XML file.
 * It implements {@link GenericStorage}, so that it can be used as a drop-in replacement for any other storage implementation
 * @author Andreas Sünder
 * @version 01-06-2024
 */
public class XMLStorage implements GenericStorage {
    private static final String DEFAULT_PATH = "data.xml";

    private String path;
    private XmlMapper mapper = new XmlMapper();

    /**
     * Initializes a new XML storage with the default path.
     */
    public XMLStorage() {
        this(DEFAULT_PATH);
    }

    /**
     * Initializes a new XML storage with the given path.
     * @param path the path to the XML file to be used
     */
    public XMLStorage(String path) {
        this.path = path;
    }

    /**
     * Saves the given trainer data to the XML file.
     * @param trainer the trainer data to be saved
     * @return true if the data was saved successfully, false otherwise
     */
    @Override
    public boolean saveData(WortTrainer trainer) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), trainer);
        } catch(IOException ioe) {
            return false;
        }

        return true;
    }

    /**
     * Loads the trainer data from the XML file.
     * @return the loaded trainer data, or null if an error occurred
     */
    @Override
    public WortTrainer loadData() {
        try {
            return mapper.readValue(new File(path), WortTrainer.class);
        } catch(IOException ioe) {
            return null;
        }
    }
}
