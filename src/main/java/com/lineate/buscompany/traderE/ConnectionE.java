package com.lineate.buscompany.traderE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class ConnectionE {

    private String USER;
    private String PASSWORD;
    private String URL;
    private String CLASSNAME;

    public void setConnection(){
        try (InputStream inputStream = new FileInputStream(new File("C:\\Users\\1\\eclipse-workspace\\earthquakes\\src\\main\\resources\\application.properties"))) {
            Properties properties = new Properties();
            properties.load(inputStream);

            USER = properties.getProperty("spring.datasource.username");
            PASSWORD = properties.getProperty("spring.datasource.password");
            URL = properties.getProperty("spring.datasource.url");
            CLASSNAME = properties.getProperty("spring.datasource.driver-class-name");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
