package net.nanthrax.test.blueprint;

import java.util.Map;

public class MyBundle {

    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void init() {
        if (properties == null) {
            throw new IllegalStateException("Properties is null");
        }
        for (int i = 1; i <= 50; i++) {
            if (properties.get("TST" + i) == null) {
                throw new IllegalStateException("TST" + i + " is null");
            }
            if (properties.get("TST" + i).equals("")) {
                throw new IllegalStateException("TST" + i + " is empty");
            }
            if (!properties.get("TST" + i).equals("VL" + i)) {
                throw new IllegalStateException("TST" + i + "/VL" + i + " not match");
            }
        }
        for (int i = 1; i <= 50; i++) {
            System.out.println("= TST" + i + "=" + properties.get("TST" + i));
        }
        System.out.println("===> ALL OK");

    }
}
