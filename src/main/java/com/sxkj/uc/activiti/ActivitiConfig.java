package com.sxkj.uc.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName ActivitiConfig
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/27 0027
 **/
@Configuration
public class ActivitiConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        return configuration;
    }

    @Bean
    public ProcessEngine processEngine() {
        return standaloneProcessEngineConfiguration().buildProcessEngine();
    }
}
