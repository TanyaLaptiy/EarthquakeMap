package com.lineate.buscompany;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.lineate.buscompany")
@PropertySource("classpath:application.properties")

public class Config {

}
