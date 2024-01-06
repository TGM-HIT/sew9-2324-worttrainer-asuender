package org.tgm.asuender.persistence;

import org.tgm.asuender.WortTrainer;

/**
 * This interface is used to save and load trainer data from any source.
 * The default implementation is {@link XMLStorage}.
 * @author Andreas Sünder
 * @version 01-06-2024
 */
public interface GenericStorage {
    boolean saveData(WortTrainer trainer);

    WortTrainer loadData();
}
