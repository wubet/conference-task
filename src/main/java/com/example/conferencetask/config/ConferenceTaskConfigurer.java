package com.example.conferencetask.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.context.annotation.Bean;

public class ConferenceTaskConfigurer extends DefaultTaskConfigurer {

  @Autowired
  private DataSource dataSource;

  @Bean
  public ConferenceTaskConfigurer getTaskConfigurer() {
    return new ConferenceTaskConfigurer(dataSource);
  }

  public ConferenceTaskConfigurer(DataSource dataSource){
    super(dataSource);
  }
}
