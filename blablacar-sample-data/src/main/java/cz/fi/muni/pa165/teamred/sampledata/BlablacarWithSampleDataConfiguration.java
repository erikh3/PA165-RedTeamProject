package cz.fi.muni.pa165.teamred.sampledata;

import cz.fi.muni.pa165.teamred.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Takes ServiceConfiguration and adds the SampleDataLoadingFacade bean
 *
 * @author Erik Horváth
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class BlablacarWithSampleDataConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(BlablacarWithSampleDataConfiguration.class);

    @Inject
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        logger.debug("sample-data dataLoading()");
        sampleDataLoadingFacade.loadData();
    }
}
