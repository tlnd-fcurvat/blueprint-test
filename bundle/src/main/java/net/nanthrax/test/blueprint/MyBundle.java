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
        if (properties.get("TST1") == null) {
            throw new IllegalStateException("TST1 is null");
        }
        if (properties.get("TST2") == null) {
            throw new IllegalStateException("TST2 is null");
        }
        if (!properties.get("TST1").equals("VL1")) {
            throw new IllegalStateException("TST1/VL1 not match");
        }
        if (!properties.get("TST2").equals("VL2")) {
            throw new IllegalStateException("TST2/VL2 not match");
        }
        System.out.println("ALL OK");
    }
}
