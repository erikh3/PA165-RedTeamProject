package cz.fi.muni.pa165.teamred.service.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackages = "cz.fi.muni.pa165.teamred.service")
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        return new DozerBeanMapper();
    }
}
