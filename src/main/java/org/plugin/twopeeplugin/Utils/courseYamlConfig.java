package org.plugin.twopeeplugin.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.plugin.twopeeplugin.TwoPeePlugin;

import java.io.File;
import java.io.IOException;

public class courseYamlConfig {
    private static courseYamlConfig courseYaml;
    private File file;
    private YamlConfiguration config;
    private courseYamlConfig() {
        setupFileConfig();
    }

    public static YamlConfiguration getConfig() {
        if (courseYaml ==null) {
            courseYaml = new courseYamlConfig();
        }
        return courseYaml.config;
    }

    public static courseYamlConfig getInstance() {
        if (courseYaml ==null) {
            courseYaml = new courseYamlConfig();
        }
        return courseYaml;
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
        file = new File(TwoPeePlugin.getInstance().getDataFolder(),"course.yml");
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
