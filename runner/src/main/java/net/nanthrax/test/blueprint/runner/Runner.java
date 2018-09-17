package net.nanthrax.test.blueprint.runner;

import org.apache.karaf.features.ConfigInfo;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import java.net.URI;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class Runner {

    private final static URI REPO = URI.create("mvn:net.nanthrax.test/my.features/1.0-SNAPSHOT/xml");
    private final static String FEATURE = "test";
    private final static String FEATURE_VERSION = "1.0-SNAPSHOT";
    private final static String CONFIG_PID = "my.config";

    private FeaturesService featuresService;
    private ConfigurationAdmin configurationAdmin;

    public FeaturesService getFeaturesService() {
        return featuresService;
    }

    public void setFeaturesService(FeaturesService featuresService) {
        this.featuresService = featuresService;
    }

    public ConfigurationAdmin getConfigurationAdmin() {
        return configurationAdmin;
    }

    public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        this.configurationAdmin = configurationAdmin;
    }

    public void init() throws Exception {
        featuresService.addRepository(REPO, false);

        Feature feature = featuresService.getFeature(FEATURE, FEATURE_VERSION);
        Map<String, String> properties = new HashMap();

        for (ConfigInfo configInfo : feature.getConfigurations()) {
            for (Map.Entry entry : configInfo.getProperties().entrySet()) {
                properties.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }

        Configuration configuration = configurationAdmin.getConfiguration(CONFIG_PID, null);
        Dictionary<String, Object> dictionary = Optional.ofNullable(configuration.getProperties()).orElse(new Hashtable());
        for (Map.Entry entry : properties.entrySet()) {
            dictionary.put(entry.getKey().toString(), entry.getValue());
        }
        configuration.update(dictionary);

        featuresService.installFeature(FEATURE, FEATURE_VERSION);
    }

    public void destroy() throws Exception {
        featuresService.uninstallFeature(FEATURE, FEATURE_VERSION);

        Configuration configuration = configurationAdmin.getConfiguration(CONFIG_PID, null);
        configuration.delete();

        featuresService.removeRepository(REPO, false);
    }
}
