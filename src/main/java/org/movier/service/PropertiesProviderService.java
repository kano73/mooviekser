package org.movier.service;

import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class PropertiesProviderService {

    private final static Properties properties;

    static {
        properties = new Properties();
    }

    public Properties get() {
        return properties;
    }

}
