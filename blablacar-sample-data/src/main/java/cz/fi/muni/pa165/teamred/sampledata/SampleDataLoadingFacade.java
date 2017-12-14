package cz.fi.muni.pa165.teamred.sampledata;

import java.io.IOException;

/**
 * Populates database with sample data.
 *
 * @author Erik Horváth
 */
public interface SampleDataLoadingFacade {

    void loadData() throws IOException;
}
