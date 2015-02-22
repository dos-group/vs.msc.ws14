package de.tuberlin.cit.sdn.opendaylight.client;

import de.tuberlin.cit.sdn.opendaylight.commons.OdlSettings;

import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected OdlSettings settings;

    public void init() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("test.properties"));
        settings = new OdlSettings(properties.getProperty("ip"),
                properties.getProperty("user"),
                properties.getProperty("password"),
                properties.getProperty("port"));
    }
}
