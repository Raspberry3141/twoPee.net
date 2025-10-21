package org.plugin.twopeeplugin.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.plugin.twopeeplugin.TwoPeePlugin;

import java.io.File;
import java.io.IOException;

public class tpLocationsYamlConfig {
    private static tpLocationsYamlConfig yamlconfig;
    private File file;
    private YamlConfiguration config;

    private tpLocationsYamlConfig() {
        setupFileConfig();
    }

    public static YamlConfiguration getConfig() {
        if (yamlconfig==null) {
            yamlconfig = new tpLocationsYamlConfig();
        }
        return yamlconfig.config;
    }

    public static tpLocationsYamlConfig getInstance() {
        if (yamlconfig==null) {
            yamlconfig = new tpLocationsYamlConfig();
        }
        return yamlconfig;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setupFileConfig() {
        file = new File(TwoPeePlugin.getInstance().getDataFolder(),"tpLocations.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
}
